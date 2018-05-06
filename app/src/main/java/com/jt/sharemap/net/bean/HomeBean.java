package com.jt.sharemap.net.bean;

/**
 * <pre>
 *     @author : zhangjiantao
 *     time   : 2018/04/23
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class HomeBean {

    /**
     * _id : 5adae59a3bf8c058ee292f67
     * coverImg : http://okyb0e40i.bkt.clouddn.com/Fvx8spLXGWwvQJSZ4AFElgef2Pcc
     * creater : {"_id":"5ad405cbf35763038b757312","__v":0,"signature":"精选有趣、有用的地图集","sex":"female","avatar":"http://okyb0e40i.bkt.clouddn.com/Fg8nsEKje42ddsAbqWQ6WRkP-wft","name":"分享地图精选","followers":[],"userType":1}
     * title : 北京医保定点医院
     * description : 收集全部北京医保定点医院，方便大家快速定位自己身边最近的医保报销医院
     * __v : 0
     * addDate : 2018-04-21T07:17:46.552Z
     */

    private String _id;
    private String coverImg;
    private CreaterBean creater;
    private String title;
    private String description;
    private int __v;
    private String addDate;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public CreaterBean getCreater() {
        return creater;
    }

    public void setCreater(CreaterBean creater) {
        this.creater = creater;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public static class CreaterBean {
        /**
         * _id : 5ad405cbf35763038b757312
         * __v : 0
         * signature : 精选有趣、有用的地图集
         * sex : female
         * avatar : http://okyb0e40i.bkt.clouddn.com/Fg8nsEKje42ddsAbqWQ6WRkP-wft
         * name : 分享地图精选
         * followers : []
         * userType : 1
         */

        private String _id;
        private int __v;
        private String signature;
        private String sex;
        private String avatar;
        private String name;
        private int userType;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
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

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }
    }
}
