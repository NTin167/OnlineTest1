package com.ptithcm.onlinetest.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Converter {
    public static String convertTimeStampToYYYYMMDDhhss(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);

        // Chuyển đổi thành LocalDateTime để định dạng ngày giờ
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, java.time.ZoneId.systemDefault());

        // Sử dụng java.time.format.DateTimeFormatter để định dạng
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return dateTime.format(formatter);
    }
}
