package com.zilker.CustomTag;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Calendar;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class CustomTag extends SimpleTagSupport{
String message;
	
	
	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public void doTag() throws JspException, IOException {
		StringWriter stringWriter = new StringWriter();
		JspWriter out = getJspContext().getOut();
		out.print(Calendar.getInstance().getTime());
		out.print(message);
		getJspBody().invoke(stringWriter);
		getJspContext().getOut().println(stringWriter.toString());
	}
}
