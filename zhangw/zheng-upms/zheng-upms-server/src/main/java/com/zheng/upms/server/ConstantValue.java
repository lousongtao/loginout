package com.zheng.upms.server;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ConstantValue {


    public static final DateFormat DFYMDHMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final DateFormat DFHMS = new SimpleDateFormat("HH:mm:ss");
    public static final DateFormat DFYMD = new SimpleDateFormat("yyyy-MM-dd");
    public static final DateFormat DFWEEK = new SimpleDateFormat("EEE");
    public static final DateFormat DFYMDHMS_2 = new SimpleDateFormat("yyyyMMddHHmmss");

    public static final String DATE_PATTERN_YMD = "yyyy-MM-dd";
    public static final String DATE_PATTERN_YMDHMS = "yyyy-MM-dd HH:mm:ss";

    public static final String FORMAT_DOUBLE = "%.2f";

    public static final int SIGNTYPE_IN = 0;
    public static final int SIGNTYPE_OUT = 1;
    public static final int SIGNVIA_BROWSER = 0;
    public static final int SIGNVIA_APP = 1;
}
