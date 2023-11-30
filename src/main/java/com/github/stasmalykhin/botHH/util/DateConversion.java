package com.github.stasmalykhin.botHH.util;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Log4j
public class DateConversion {

    public String fromDateToString(String pattern, Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);

    }

    public Date fromStringToDate(String pattern, String publishedAt) {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern(pattern);
        Date published = null;
        try {
            published = format.parse(publishedAt);
        } catch (ParseException e) {
            log.error(e);
        }
        return published;
    }
}
