package com.nekonekod.tagger.taggerserver.util;

import lombok.extern.log4j.Log4j2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

/**
 * @author duwenjun
 * @date 2018/1/2
 */
@Log4j2
public class DateUtil {

    /**
     * @param dateTimeFormat 2017-11-17T17:59:47.486Z
     * @return
     */
    public static Date parseDateTimeFormat(String dateTimeFormat) {
        if (Objects.isNull(dateTimeFormat)) return null;
        String d = dateTimeFormat.substring(0, dateTimeFormat.length() - 1);
        LocalDateTime dateTime = LocalDateTime.parse(d);
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * @param chineseFormat 2017年10月1日 00:51
     * @return
     */
    public static Date parseChineseFormat(String chineseFormat) {
        if (Objects.isNull(chineseFormat)) return null;
        String format = "yyyy年MM月dd日 hh:mm";
        try {
            return new SimpleDateFormat(format).parse(chineseFormat);
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(new Date().getTime());
    }

}
