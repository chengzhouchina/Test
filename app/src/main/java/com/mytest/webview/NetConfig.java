package com.mytest.webview;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class NetConfig {


    // 证书数据
    private static List<byte[]> CERTIFICATES_DATA = new ArrayList<>();


    /**
     * 添加https证书
     *
     * @param inputStream
     */
    public synchronized static void addCertificate(InputStream inputStream) {

        if (inputStream != null) {
            try {
                int ava = 0;// 数据当次可读长度
                int len = 0;// 数据总长度
                ArrayList<byte[]> data = new ArrayList<>();
                while ((ava = inputStream.available()) > 0) {
                    byte[] buffer = new byte[ava];
                    inputStream.read(buffer);
                    data.add(buffer);
                    len += ava;
                }

                byte[] buff = new byte[len];
                int dstPos = 0;
                for (byte[] bytes : data) {
                    int length = bytes.length;
                    System.arraycopy(bytes, 0, buff, dstPos, length);
                    dstPos += length;
                }
                CERTIFICATES_DATA.add(buff);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * https证书
     *
     * @return
     */
    public static List<byte[]> getCertificatesData() {
        return CERTIFICATES_DATA;
    }


    /**
     * 获取SslSocketFactory
     *
     * @param context 上下文
     * @return SSLSocketFactory
     */
//    public static SSLSocketFactory getSslSocketFactory(Context context) {
//        try {
//            // 服务器端需要验证的客户端证书
//            KeyStore keyStore = KeyStore.getInstance(KEY_STORE_TYPE_P12);
//            // 客户端信任的服务器端证书
//            KeyStore trustStore = KeyStore.getInstance(KEY_STORE_TYPE_BKS);
//
//            InputStream ksIn = context.getResources().getAssets().open(KEY_STORE_CLIENT_PATH);
//            InputStream tsIn = context.getResources().getAssets().open(KEY_STORE_TRUST_PATH);
//            try {
//                keyStore.load(ksIn, KEY_STORE_PASSWORD.toCharArray());
//                trustStore.load(tsIn, KEY_STORE_TRUST_PASSWORD.toCharArray());
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    ksIn.close();
//                } catch (Exception ignore) {
//                }
//                try {
//                    tsIn.close();
//                } catch (Exception ignore) {
//                }
//            }
//            return new SSLSocketFactory(keyStore, KEY_STORE_PASSWORD, trustStore);
//        } catch (KeyManagementException | UnrecoverableKeyException | KeyStoreException | FileNotFoundException | NoSuchAlgorithmException  e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
