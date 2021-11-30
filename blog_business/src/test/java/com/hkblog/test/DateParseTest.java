package com.hkblog.test;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author : HK意境
 * @ClassName : DateParseTest
 * @date : 2021/11/30 15:35
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class DateParseTest {

    @Test
    public void datetimeFormatTest() throws ParseException {

        Date parse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2021-08-29 10:17:16");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parse) ;
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        System.out.println(year + " : " + month);
    }

}
