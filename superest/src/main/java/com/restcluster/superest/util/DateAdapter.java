package com.restcluster.superest.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.restcluster.superest.threadlocal.ThreadLocalHolder;

public class DateAdapter extends XmlAdapter<String, Date> {

    @Override
    public String marshal(Date v) throws Exception {
    	SimpleDateFormat dateFormat = ThreadLocalHolder.getSimpleDateFormat();
        return dateFormat.format(v);
    }

    @Override
    public Date unmarshal(String v) throws Exception {
    	SimpleDateFormat dateFormat = ThreadLocalHolder.getSimpleDateFormat();
        return dateFormat.parse(v);
    }

}