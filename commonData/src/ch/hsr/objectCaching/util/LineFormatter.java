package ch.hsr.objectCaching.util;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LineFormatter extends Formatter{
	
	private Date date;
	private String lineSeperator;
	
	public LineFormatter(){
		date = new Date();
		lineSeperator = System.getProperty("line.separator");
	}
	

	@Override
	public String format(LogRecord record) {
		StringBuilder buffer = new StringBuilder();
		date.setTime(record.getMillis());
		
		buffer.append(date.toString().substring(4,19) + " ");
		buffer.append(fill(record.getLevel().toString(), 8) + " ");
		buffer.append(fill(record.getSourceClassName() + ":" + record.getSourceMethodName(), 80) + " --> ");
		buffer.append(record.getMessage() + lineSeperator);
		return buffer.toString();
	}


	private String fill(String string, int i) {
		StringBuilder buffer = new StringBuilder(string);
		while(buffer.length() < i){
			buffer.append(" ");
		}
		return buffer.toString();
	}

}
