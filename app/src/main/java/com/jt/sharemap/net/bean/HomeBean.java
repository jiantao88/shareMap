package com.jt.sharemap.net.bean;

import java.util.List;

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
     * status : 0
     * result : [{"_id":"5adae59a3bf8c058ee292f67","coverImg":"http://okyb0e40i.bkt.clouddn.com/Fvx8spLXGWwvQJSZ4AFElgef2Pcc","creater":{"_id":"5ad405cbf35763038b757312","__v":0,"signature":"精选有趣、有用的地图集","sex":"female","avatar":"http://okyb0e40i.bkt.clouddn.com/Fg8nsEKje42ddsAbqWQ6WRkP-wft","name":"分享地图精选","followers":[],"userType":1},"title":"北京医保定点医院","description":"收集全部北京医保定点医院，方便大家快速定位自己身边最近的医保报销医院","__v":0,"addDate":"2018-04-21T07:17:46.552Z"},{"_id":"5adacbcbab4f8d14cec82456","coverImg":"http://okyb0e40i.bkt.clouddn.com/FhBlAjB16hK8GQOGMbqhDcImTQ_z","creater":{"_id":"5a95450e2071ec04f56daa3f","__v":0,"signature":"世界这么大，我想在家呆着","sex":"female","avatar":"http://okyb0e40i.bkt.clouddn.com/FhHLANyvDCJb4OTCvOVVylBZYWI8","name":"明明最可爱","followers":[],"userType":1},"title":"爱游","description":"我要在世界各地留下我的足记","__v":0,"addDate":"2018-04-21T05:27:39.640Z"},{"_id":"5a98aa002071ec04f56daa46","coverImg":"http://okyb0e40i.bkt.clouddn.com/Fq_0SLvFlaxYAQZqUXAbMg-TGjZS","creater":{"_id":"5a98a9822071ec04f56daa45","__v":0,"signature":"","sex":"male","avatar":null,"name":"jiantao ","followers":[],"userType":1},"title":"耳机","description":"耳机","__v":0,"addDate":"2018-03-02T01:33:52.418Z"},{"_id":"5a978d7fc4e4d504fb760864","coverImg":"http://okyb0e40i.bkt.clouddn.com/ltvg2H7Vywh-MO68RjSoXIaWixiG","creater":{"_id":"5a95450e2071ec04f56daa3f","__v":0,"signature":"世界这么大，我想在家呆着","sex":"female","avatar":"http://okyb0e40i.bkt.clouddn.com/FhHLANyvDCJb4OTCvOVVylBZYWI8","name":"明明最可爱","followers":[],"userType":1},"title":"明明吃喝集","description":"唯有美食与爱不可辜负\n","__v":0,"addDate":"2018-03-01T05:19:59.886Z"},{"_id":"5a9113462071ec04f56daa3c","coverImg":"http://okyb0e40i.bkt.clouddn.com/FiBXe_H1_BmIXr05H5c9Gtj7c6WJ","creater":{"_id":"5a9112b32071ec04f56daa3b","__v":0,"signature":"分享地图app作者","sex":"male","avatar":"http://okyb0e40i.bkt.clouddn.com/FoDILsRUHVFHkwBSxNyyma_1C79N","name":"剑波","followers":[],"userType":1},"title":"我的地图集","description":"我的地点收藏","__v":0,"addDate":"2018-02-24T07:24:54.286Z"}]
     */

    private int status;
    private List<ResultBean> result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
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
}
