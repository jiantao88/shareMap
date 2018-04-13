package com.jt.sharemapapi.utils;


import android.util.Log;

import java.util.Locale;

public class TraceLog {

    private static boolean bFlag = true;

    private static class LogLine {
        private String tag;
        private String text;

        public LogLine(String tag, String text) {
            this.tag = tag;
            this.text = text;
        }
    }

    public static void setFlag(boolean flag)
    {
        bFlag = flag;
    }

    private static LogLine concatLogLine(String extraMessage) {
        StackTraceElement ste = new Throwable().getStackTrace()[2];
        String shortClassName = ste.getClassName().substring(
                ste.getClassName().lastIndexOf(".") + 1);
        String logText = String.format(Locale.US, "%s: %s [at (%s:%d)]",
                ste.getMethodName(), extraMessage, ste.getFileName(),
                ste.getLineNumber());
        return new LogLine(shortClassName, logText);
    }

    public static void v(String msg) {
        if(!bFlag)
        {
            return;
        }
        LogLine line = concatLogLine(msg);
        Log.v(line.tag, line.text);
    }

    public static void v() {
        if(!bFlag)
        {
            return;
        }
        LogLine line = concatLogLine("");
        Log.v(line.tag, line.text);
    }

    public static void v(String format, Object... args) {
        if(!bFlag)
        {
            return;
        }
        LogLine line = concatLogLine(String.format(format, args));
        Log.v(line.tag, line.text);
    }

    public static void d(String msg) {
        if(!bFlag)
        {
            return;
        }
        LogLine line = concatLogLine(msg);
        Log.d(line.tag, line.text);
    }

    public static void d() {
        if(!bFlag)
        {
            return;
        }
        LogLine line = concatLogLine("");
        Log.d(line.tag, line.text);
    }

    public static void d(String format, Object... args) {
        if(!bFlag)
        {
            return;
        }
        LogLine line = concatLogLine(String.format(format, args));
        Log.d(line.tag, line.text);
    }

    public static void i(String msg) {
        if(!bFlag)
        {
            return;
        }
        LogLine line = concatLogLine(msg);
        Log.i(line.tag, line.text);
    }

    public static void i() {
        if(!bFlag)
        {
            return;
        }
        LogLine line = concatLogLine("");
        Log.i(line.tag, line.text);
    }

    public static void i(String format, Object... args) {
        if(!bFlag)
        {
            return;
        }
        LogLine line = concatLogLine(String.format(format, args));
        Log.i(line.tag, line.text);
    }

    public static void w(String msg) {
        if(!bFlag)
        {
            return;
        }
        LogLine line = concatLogLine(msg);
        Log.w(line.tag, line.text);
    }

    public static void w() {
        LogLine line = concatLogLine("");
        Log.w(line.tag, line.text);
    }

    public static void w(String format, Object... args) {
        LogLine line = concatLogLine(String.format(format, args));
        Log.w(line.tag, line.text);
    }

    public static void e(String msg) {
        LogLine line = concatLogLine(msg);
        Log.e(line.tag, line.text);
    }

    public static void e() {
        LogLine line = concatLogLine("");
        Log.e(line.tag, line.text);
    }

    public static void e(String format, Object... args) {
        LogLine line = concatLogLine(String.format(format, args));
        Log.e(line.tag, line.text);
    }

    public static void wtf(String msg) {
        LogLine line = concatLogLine(msg);
        Log.wtf(line.tag, line.text);
    }

    public static void wtf() {
        LogLine line = concatLogLine("");
        Log.wtf(line.tag, line.text);
    }

    public static void wtf(String format, Object... args) {
        LogLine line = concatLogLine(String.format(format, args));
        Log.wtf(line.tag, line.text);
    }
}
