package com.blogs.custom_exceptions;

@SuppressWarnings("serial")
public class CartNotFoundException extends RuntimeException 
{
	public CartNotFoundException(String mesg) 
	{
		super(mesg);
	}
}
