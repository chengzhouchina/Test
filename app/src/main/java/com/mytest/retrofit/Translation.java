package com.mytest.retrofit;

import com.orhanobut.logger.Logger;

public class Translation {


    /**
     * status : 1
     * content : {"from":"en-EU","to":"zh-CN","vendor":"baidu","out":"你好世界<br/>","err_no":0}
     */

    private int status;
    private ContentBean content;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * from : en-EU
         * to : zh-CN
         * vendor : baidu
         * out : 你好世界<br/>
         * err_no : 0
         */

        private String from;
        private String to;
        private String vendor;
        private String out;
        private int err_no;

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getVendor() {
            return vendor;
        }

        public void setVendor(String vendor) {
            this.vendor = vendor;
        }

        public String getOut() {
            return out;
        }

        public void setOut(String out) {
            this.out = out;
        }

        public int getErr_no() {
            return err_no;
        }

        public void setErr_no(int err_no) {
            this.err_no = err_no;
        }
    }

    public void show(){
        Logger.d(status);
        Logger.d(content.from);
        Logger.d(content.to);
        Logger.d(content.vendor);
        Logger.d(content.out);
        Logger.d(content.err_no);
    }
}
