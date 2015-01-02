package com.superest.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class TimeStampAdapter extends XmlAdapter<String, Timestamp> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public String marshal(Timestamp v) throws Exception {
        return dateFormat.format(v);
    }

    @Override
    public Timestamp unmarshal(String v) throws Exception {
       Date date = dateFormat.parse(v);
       return new Timestamp(date.getTime());
    }

}