package com.fenxiangditu.sharemap.net.bean;

/**
 * <pre>
 *     @author : zhangjiantao
 *     time   : 2018/04/23
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class CommentBean {


    /**
     * status : 0
     * result : {"__v":0,"fromUser":{"_id":"5a98a9822071ec04f56daa45","avatar":"http://okyb0e40i.bkt.clouddn.com/FkCTkc0WuYLAeJLo5-aoHejrel1h","name":"jt"},"content":"退款吧","pageId":"5adcb0a0145c155ff5560781","pageType":"map","_id":"5add4f530a96a45ffa72b421","addDate":"2018-04-23T03:13:23.561Z"}
     */

    private int status;
    private ResultBean result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * __v : 0
         * fromUser : {"_id":"5a98a9822071ec04f56daa45","avatar":"http://okyb0e40i.bkt.clouddn.com/FkCTkc0WuYLAeJLo5-aoHejrel1h","name":"jt"}
         * content : 退款吧
         * pageId : 5adcb0a0145c155ff5560781
         * pageType : map
         * _id : 5add4f530a96a45ffa72b421
         * addDate : 2018-04-23T03:13:23.561Z
         */

        private int __v;
        private FromUserBean fromUser;
        private String content;
        private String pageId;
        private String pageType;
        private String _id;
        private String addDate;

        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;
        }

        public FromUserBean getFromUser() {
            return fromUser;
        }

        public void setFromUser(FromUserBean fromUser) {
            this.fromUser = fromUser;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPageId() {
            return pageId;
        }

        public void setPageId(String pageId) {
            this.pageId = pageId;
        }

        public String getPageType() {
            return pageType;
        }

        public void setPageType(String pageType) {
            this.pageType = pageType;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getAddDate() {
            return addDate;
        }

        public void setAddDate(String addDate) {
            this.addDate = addDate;
        }

        public static class FromUserBean {
            /**
             * _id : 5a98a9822071ec04f56daa45
             * avatar : http://okyb0e40i.bkt.clouddn.com/FkCTkc0WuYLAeJLo5-aoHejrel1h
             * name : jt
             */

            private String _id;
            private String avatar;
            private String name;

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
