package com.fenxiangditu.sharemap.event;

/**
 * <pre>
 *     @author : zhangjiantao
 *     time   : 2018/04/18
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class Event {
    public enum Type{
        ITEM,LIST
    }

    public Type mType;
    public Object mObject;

    public Event(Type type, Object object) {
        mType = type;
        mObject = object;
    }
}
