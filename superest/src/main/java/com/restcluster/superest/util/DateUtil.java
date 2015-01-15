package com.restcluster.superest.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.restcluster.superest.threadlocal.ThreadLocalHolder;


public class DateUtil {

	public static Date pasreString( String dateString ){
		try {
			 SimpleDateFormat dateFormat = ThreadLocalHolder.getSimpleDateFormat();
			return dateFormat.parse(dateString);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}
