package com.mytest.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;

public class PathUtils {
    public static String pathPrefix;
    public static final String historyPathName = "/chat/";
    public static final String imagePathName = "/image/";
    public static final String voicePathName = "/voice/";
    public static final String filePathName = "/file/";
    public static final String videoPathName = "/video/";
    public static final String netdiskDownloadPathName = "/netdisk/";
    public static final String meetingPathName = "/meeting/";
    private static File storageDir = null;
    private static PathUtils instance = null;
    private File voicePath = null;
    private File imagePath = null;
    private File historyPath = null;
    private File videoPath = null;
    private File filePath;

    private PathUtils() {
    }

    public static PathUtils getInstance() {
        if (instance == null) {
            instance = new PathUtils();
        }

        return instance;
    }

    public void initDirs(String var1, String var2, Context var3) {
        String var4 = var3.getPackageName();
        pathPrefix = "/Android/data/" + var4 + "/";
        this.voicePath = generateVoicePath(var1, var2, var3);
        if (!this.voicePath.exists()) {
            this.voicePath.mkdirs();
        }

        this.imagePath = generateImagePath(var1, var2, var3);
        if (!this.imagePath.exists()) {
            this.imagePath.mkdirs();
        }

        this.historyPath = generateHistoryPath(var1, var2, var3);
        if (!this.historyPath.exists()) {
            this.historyPath.mkdirs();
        }

        this.videoPath = generateVideoPath(var1, var2, var3);
        if (!this.videoPath.exists()) {
            this.videoPath.mkdirs();
        }

        this.filePath = generateFiePath(var1, var2, var3);
        if (!this.filePath.exists()) {
            this.filePath.mkdirs();
        }

    }

    public File getImagePath() {
        return this.imagePath;
    }

    public File getVoicePath() {
        return this.voicePath;
    }

    public File getFilePath() {
        return this.filePath;
    }

    public File getVideoPath() {
        return this.videoPath;
    }

    public File getHistoryPath() {
        return this.historyPath;
    }

    private static File getStorageDir(Context var0) {
        if (storageDir == null) {
            File var1 = Environment.getExternalStorageDirectory();
            if (var1.exists()) {
                return var1;
            }

            storageDir = var0.getFilesDir();
        }

        return storageDir;
    }

    private static File generateImagePath(String var0, String var1, Context var2) {
        String var3 = null;
        if (var0 == null) {
            var3 = pathPrefix + var1 + "/image/";
        } else {
            var3 = pathPrefix + var0 + "/" + var1 + "/image/";
        }

        return new File(getStorageDir(var2), var3);
    }

    private static File generateVoicePath(String var0, String var1, Context var2) {
        String var3 = null;
        if (var0 == null) {
            var3 = pathPrefix + var1 + "/voice/";
        } else {
            var3 = pathPrefix + var0 + "/" + var1 + "/voice/";
        }

        return new File(getStorageDir(var2), var3);
    }

    private static File generateFiePath(String var0, String var1, Context var2) {
        String var3 = null;
        if (var0 == null) {
            var3 = pathPrefix + var1 + "/file/";
        } else {
            var3 = pathPrefix + var0 + "/" + var1 + "/file/";
        }

        return new File(getStorageDir(var2), var3);
    }

    private static File generateVideoPath(String var0, String var1, Context var2) {
        String var3 = null;
        if (var0 == null) {
            var3 = pathPrefix + var1 + "/video/";
        } else {
            var3 = pathPrefix + var0 + "/" + var1 + "/video/";
        }

        return new File(getStorageDir(var2), var3);
    }

    private static File generateHistoryPath(String var0, String var1, Context var2) {
        String var3 = null;
        if (var0 == null) {
            var3 = pathPrefix + var1 + "/chat/";
        } else {
            var3 = pathPrefix + var0 + "/" + var1 + "/chat/";
        }

        return new File(getStorageDir(var2), var3);
    }

    private static File generateMessagePath(String var0, String var1, Context var2) {
        File var3 = new File(generateHistoryPath(var0, var1, var2), var1 + File.separator + "Msg.db");
        return var3;
    }

    public static File getTempPath(File var0) {
        File var1 = new File(var0.getAbsoluteFile() + ".tmp");
        return var1;
    }

    public boolean saveProperty(String filename, String value) {
        if (TextUtils.isEmpty(filename)) {
            return false;
        }
        if (TextUtils.isEmpty(value)) {
            return false;
        }

        String filePath = getFilePath().getAbsolutePath() + "/" + filename;
        if (new File(filePath).exists()) {
            new File(filePath).delete();
        }

        RandomAccessFile mm = null;
        try {
            File file = new File(filePath);
            if (file.createNewFile()) {
                mm = new RandomAccessFile(file, "rw");
                mm.seek(0);
                mm.write(value.getBytes());
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (mm != null) {
                    mm.close();
                }
            } catch (IOException e) {
            }
        }
    }

    public String getProperty(String filename) {
        if (TextUtils.isEmpty(filename)) {
            return null;
        }
        String filePath = getFilePath().getAbsolutePath() + "/" + filename;
        if (!(new File(filePath).exists())) {
            return null;
        }

        String encoding = "UTF-8";
        File file = new File(filePath);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            InputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
