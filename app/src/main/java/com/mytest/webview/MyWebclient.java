package com.mytest.webview;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.http.SslError;
import android.os.Build;
import android.webkit.ClientCertRequest;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mytest.BaseApplication;
import com.orhanobut.logger.Logger;

import org.apache.commons.compress.utils.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertPathValidatorException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.FormBody;
import okio.BufferedSink;
import okio.Okio;

public class MyWebclient extends WebViewClient {

    //private LoadedListener listener;
    private SSLContext sslContext;
    private int url_type;
    private Context context;
    private X509Certificate[] certificatesChain;
    private PrivateKey clientCertPrivateKey;

    private static final String KEY_STORE_CLIENT_PATH = "client.p12";//客户端要给服务器端认证的证书
    private static final String KEY_STORE_PASSWORD = "bbz2018";// 客户端证书密码

    public SSLContext getSslContext() {
        return sslContext;
    }

    public MyWebclient(Context context) throws IOException {
        //this.listener = listener;
        this.context = context;
        // 添加cer证书
        List<InputStream> certificates = new ArrayList<>();
        List<byte[]> certs_data = NetConfig.getCertificatesData();

        // 将字节数组转为数组输入流
        if (certs_data != null && !certs_data.isEmpty()) {
            for (byte[] bytes : certs_data) {
                certificates.add(new ByteArrayInputStream(bytes));
            }
        }
        prepareSslPinning(certificates);
        initPrivateKeyAndX509Certificate(BaseApplication.getContext());


    }

    private void initPrivateKeyAndX509Certificate(Context context) {
        Logger.d("initPrivateKeyAndX509Certificate");
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            InputStream ksIn = context.getResources().getAssets().open(KEY_STORE_CLIENT_PATH);
            keyStore.load(ksIn, KEY_STORE_PASSWORD.toCharArray());
            Enumeration<?> localEnumeration;
            localEnumeration = keyStore.aliases();
            while (localEnumeration.hasMoreElements()) {
                String str3 = (String) localEnumeration.nextElement();
                clientCertPrivateKey = (PrivateKey) keyStore.getKey(str3, KEY_STORE_PASSWORD.toCharArray());
                if (clientCertPrivateKey == null) {
                    continue;
                } else {
                    Certificate[] arrayOfCertificate = keyStore.getCertificateChain(str3);
                    certificatesChain = new X509Certificate[arrayOfCertificate.length];
                    for (int j = 0; j < certificatesChain.length; j++) {
                        certificatesChain[j] = ((X509Certificate) arrayOfCertificate[j]);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
        Logger.d("onReceivedClientCertRequest");
        if ((null != clientCertPrivateKey) && ((null != certificatesChain) && (certificatesChain.length != 0))) {
            Logger.d("if");
            request.proceed(clientCertPrivateKey, certificatesChain);
        } else {
            Logger.d("else");
            request.cancel();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        Logger.d("onReceivedSslError");
        handler.proceed();
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Logger.d(url_type + "a");
        view.loadUrl(url);
        if (url_type == 2) {
            return false;
        }
        return true;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return this.shouldOverrideUrlLoading(view, request.getUrl().toString());
    }

    public MyWebclient(int url_type) throws IOException {
        //this.listener = listener;
        this.url_type = url_type;
        // 添加cer证书
        List<InputStream> certificates = new ArrayList<>();

        List<byte[]> certs_data = NetConfig.getCertificatesData();

        // 将字节数组转为数组输入流

        if (certs_data != null && !certs_data.isEmpty()) {

            for (byte[] bytes : certs_data) {

                certificates.add(new ByteArrayInputStream(bytes));

            }

        }

        prepareSslPinning(certificates);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(final WebView view, String url) {
        Logger.d("shouldInterceptRequest");
        try {
            URL murl = null;
            if (isPOST()) {
                murl = new URL(url);
            } else {
                return null;
            }
            URLConnection rulConnection = murl.openConnection();
            HttpURLConnection conn = (HttpURLConnection) rulConnection;
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("contentType", "utf-8");
            conn.setRequestMethod(isPOST() ? "POST" : "GET");
            // Write body
            if (isPOST()) {
                OutputStream os = conn.getOutputStream();

                if (mNextAjaxRequestContents != null) {
                    writeBody(os);
                } else {
                    writeForm((BufferedSink) Okio.sink(os));
                }
                os.close();
            }

            // Read input
            String charset = conn.getContentEncoding() != null ? conn.getContentEncoding() : Charset.defaultCharset().displayName();
            String mime = conn.getContentType();
            byte[] pageContents = IOUtils.toByteArray(conn.getInputStream());

            // Perform JS injection
            if (mime.equals("text/html")) {
                pageContents = PostInterceptJavascriptInterface
                        .enableIntercept(context, pageContents)
                        .getBytes(charset);
            }

            // Convert the contents and return
            InputStream isContents = new ByteArrayInputStream(pageContents);
            mNextAjaxRequestContents = null;
            return new WebResourceResponse(mime, charset,
                    isContents);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;        // Let Android try handling things itself
        } catch (Exception e) {
            e.printStackTrace();
            return null;        // Let Android try handling things itself
        }
    }

    private boolean isPOST() {
        return (mNextAjaxRequestContents != null && mNextAjaxRequestContents.method.equals("POST"));
    }

    private void writeBody(OutputStream out) {
        try {
//            Log.d("777773", mNextAjaxRequestContents.body);
            out.write(mNextAjaxRequestContents.body.getBytes("UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void writeForm(BufferedSink out) {
        try {
            JSONArray jsonPars = new JSONArray(mNextFormRequestContents.json);

            // We assume to be dealing with a very simple form here, so no file uploads or anything
            // are possible for reasons of clarity
//            FormEncoding.Builder m = new FormEncoding.Builder();
            FormBody.Builder m = new FormBody.Builder();
            for (int i = 0; i < jsonPars.length(); i++) {
                JSONObject jsonPar = jsonPars.getJSONObject(i);
                m.add(jsonPar.getString("name"), jsonPar.getString("value"));
                // jsonPar.getString("type");
            }
//            m.build().writeBodyTo(out);
            m.build().writeTo(out);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private WebResourceResponse processRequest(String webUrl) {
        try {
            // Setup connection
            URL url = new URL(webUrl);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            // Set SSL Socket Factory for this request
            urlConnection.setSSLSocketFactory(sslContext.getSocketFactory());
            //很重要，验证证书
            urlConnection.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            // Get content, contentType and encoding
            InputStream is = urlConnection.getInputStream();
            String contentType = urlConnection.getContentType();
            String encoding = urlConnection.getContentEncoding();
            // If got a contentType header
            if (contentType != null) {
                String mimeType = contentType;
                // Parse mime type from contenttype string
                if (contentType.contains(";")) {
                    mimeType = contentType.split(";")[0].trim();
                }
                return new WebResourceResponse(mimeType, encoding, is);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (isCause(CertPathValidatorException.class, e)) {
            }
        }
        // Return empty response for this request
        return new WebResourceResponse(null, null, null);
    }

    private void prepareSslPinning(List<InputStream> certificates) throws IOException {
        try {
            KeyManagerFactory keyManagerFactory = KeyManagerFactory
                    .getInstance("X509");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("X509");
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            KeyStore keyStore2 = KeyStore.getInstance(KeyStore.getDefaultType());
            //读取证书
            InputStream ksIn = context.getResources().getAssets().open("client.p12");
            //加载证书
            keyStore2.load(null);
            keyStore.load(ksIn, "bbz2018".toCharArray());
            ksIn.close();
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            try {
                for (int i = 0, size = certificates.size(); i < size; ) {
                    InputStream certificate = certificates.get(i);
                    String certificateAlias = Integer.toString(i++);
                    keyStore2.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
                    if (certificate != null)
                        certificate.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            sslContext = SSLContext.getInstance("TLS");
            keyManagerFactory.init(keyStore, "bbz2018".toCharArray());
            trustManagerFactory.init(keyStore2);
            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static TrustManager[] prepareTrustManager(InputStream... certificates) {
        if (certificates == null || certificates.length <= 0) return null;
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
                try {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e) {
                }
            }
            TrustManagerFactory trustManagerFactory = null;
            trustManagerFactory = TrustManagerFactory.
                    getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            return trustManagers;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static KeyManager[] prepareKeyManager(InputStream bksFile, String password) {
        try {
            if (bksFile == null || password == null) return null;

            KeyStore clientKeyStore = KeyStore.getInstance("PKCS12");
            clientKeyStore.load(bksFile, password.toCharArray());
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(clientKeyStore, password.toCharArray());
            return keyManagerFactory.getKeyManagers();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static X509TrustManager chooseTrustManager(TrustManager[] trustManagers) {
        for (TrustManager trustManager : trustManagers) {
            if (trustManager instanceof X509TrustManager) {
                return (X509TrustManager) trustManager;
            }
        }
        return null;
    }


    private static class MyTrustManager implements X509TrustManager {
        private X509TrustManager defaultTrustManager;
        private X509TrustManager localTrustManager;

        public MyTrustManager(X509TrustManager localTrustManager) throws NoSuchAlgorithmException, KeyStoreException {
            TrustManagerFactory var4 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            var4.init((KeyStore) null);
            defaultTrustManager = chooseTrustManager(var4.getTrustManagers());
            this.localTrustManager = localTrustManager;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            try {
                defaultTrustManager.checkServerTrusted(chain, authType);
            } catch (CertificateException ce) {
                localTrustManager.checkServerTrusted(chain, authType);
            }
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    public static boolean isCause(
            Class<? extends Throwable> expected,
            Throwable exc
    ) {
        return expected.isInstance(exc) || (
                exc != null && isCause(expected, exc.getCause())
        );
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
    }




    private PostInterceptJavascriptInterface.FormRequestContents mNextFormRequestContents = null;

    public void nextMessageIsFormRequest(PostInterceptJavascriptInterface.FormRequestContents formRequestContents) {
        mNextFormRequestContents = formRequestContents;
    }

    private PostInterceptJavascriptInterface.AjaxRequestContents mNextAjaxRequestContents = null;

    public void nextMessageIsAjaxRequest(PostInterceptJavascriptInterface.AjaxRequestContents ajaxRequestContents) {
        mNextAjaxRequestContents = ajaxRequestContents;
    }

}
