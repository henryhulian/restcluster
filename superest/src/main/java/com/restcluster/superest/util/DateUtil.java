package com.restcluster.superest.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateUtil {

	public static Date pasreString( String dateString ){
		try {
			 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return dateFormat.parse(dateString);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}
