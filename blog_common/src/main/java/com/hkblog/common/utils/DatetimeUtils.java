package com.hkblog.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author : HK意境
 * @ClassName : DatetimeUtils
 * @date : 2021/11/30 15:43
 * @description : 日期时间，格式化，获取元数据信息
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class DatetimeUtils {


    // 获取年月日，时分秒，时区等
    public static int getDataFromDate(Date date, int type) throws ParseException {

        // 首先进行格式化
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = formatter.parse(formatter.format(date));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parse) ;

        return calendar.get(type);
    }

}
