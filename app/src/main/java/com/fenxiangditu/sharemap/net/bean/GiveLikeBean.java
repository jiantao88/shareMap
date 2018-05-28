package com.fenxiangditu.sharemap.net.bean;

/**
 * <pre>
 *     @author : zhangjiantao
 *     time   : 2018/04/23
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class GiveLikeBean {


    /**
     * status : 0
     * result : {"hasLiked":true,"like":{"__v":0,"targetId":"5adcb0a0145c155ff5560781","targetType":"location","creater":{"_id":"5a98a9822071ec04f56daa45","avatar":"http://okyb0e40i.bkt.clouddn.com/FkCTkc0WuYLAeJLo5-aoHejrel1h","name":"jt"},"_id":"5add4e450a96a45ffa72b420","addDate":"2018-04-23T03:08:53.003Z"}}
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
         * hasLiked : true
         * like : {"__v":0,"targetId":"5adcb0a0145c155ff5560781","targetType":"location","creater":{"_id":"5a98a9822071ec04f56daa45","avatar":"http://okyb0e40i.bkt.clouddn.com/FkCTkc0WuYLAeJLo5-aoHejrel1h","name":"jt"},"_id":"5add4e450a96a45ffa72b420","addDate":"2018-04-23T03:08:53.003Z"}
         */

        private boolean hasLiked;
        private LikeBean like;

        public boolean isHasLiked() {
            return hasLiked;
        }

        public void setHasLiked(boolean hasLiked) {
            this.hasLiked = hasLiked;
        }

        public LikeBean getLike() {
            return like;
        }

        public void setLike(LikeBean like) {
            this.like = like;
        }

        public static class LikeBean {
            /**
             * __v : 0
             * targetId : 5adcb0a0145c155ff5560781
             * targetType : location
             * creater : {"_id":"5a98a9822071ec04f56daa45","avatar":"http://okyb0e40i.bkt.clouddn.com/FkCTkc0WuYLAeJLo5-aoHejrel1h","name":"jt"}
             * _id : 5add4e450a96a45ffa72b420
             * addDate : 2018-04-23T03:08:53.003Z
             */

            private int __v;
            private String targetId;
            private String targetType;
            private CreaterBean creater;
            private String _id;
            private String addDate;

            public int get__v() {
                return __v;
            }

            public void set__v(int __v) {
                this.__v = __v;
            }

            public String getTargetId() {
                return targetId;
            }

            public void setTargetId(String targetId) {
                this.targetId = targetId;
            }

            public String getTargetType() {
                return targetType;
            }

            public void setTargetType(String targetType) {
                this.targetType = targetType;
            }

            public CreaterBean getCreater() {
                return creater;
            }

            public void setCreater(CreaterBean creater) {
                this.creater = creater;
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

            public static class CreaterBean {
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
}
