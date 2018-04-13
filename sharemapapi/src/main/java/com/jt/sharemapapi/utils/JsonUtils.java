package com.jt.sharemapapi.utils;


import com.google.gson.Gson;

public class JsonUtils {

    public static <T> T parseObject(String jsonStr, Class<T> entityClass)
    {
        T ret  = null;
        try {
            Gson gson = new Gson();
            ret = gson.fromJson(jsonStr,entityClass);
        }catch (Exception e)
        {
            TraceLog.e("parseObject-something Exception with:" + e.toString());
        }

        return ret;
    }

    public static String toJSONString(Object obj) {
        String ret = null;

        try {
            Gson gson = new Gson();
            ret = gson.toJson(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }
}
