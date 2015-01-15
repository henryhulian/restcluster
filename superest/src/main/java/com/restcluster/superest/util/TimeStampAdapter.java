package com.restcluster.superest.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.restcluster.superest.threadlocal.ThreadLocalHolder;

public class TimeStampAdapter extends XmlAdapter<String, Timestamp> {


    @Override
    public String marshal(Timestamp v) throws Exception {
    	SimpleDateFormat dateFormat = ThreadLocalHolder.getSimpleDateFormat();
        return dateFormat.format(v);
    }

    @Override
    public Timestamp unmarshal(String v) throws Exception {
    	SimpleDateFormat dateFormat = ThreadLocalHolder.getSimpleDateFormat();
       Date date = dateFormat.parse(v);
       return new Timestamp(date.getTime());
    }

}