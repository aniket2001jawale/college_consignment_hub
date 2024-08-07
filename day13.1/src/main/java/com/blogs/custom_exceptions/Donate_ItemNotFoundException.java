package com.blogs.custom_exceptions;

@SuppressWarnings("serial")
public class Donate_ItemNotFoundException extends RuntimeException 
{
	public Donate_ItemNotFoundException(String mesg) 
	{
		super(mesg);
	}
}
