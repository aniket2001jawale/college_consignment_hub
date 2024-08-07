package com.blogs.custom_exceptions;

@SuppressWarnings("serial")
public class WatchlistNotFoundException extends RuntimeException 
{
	public WatchlistNotFoundException(String mesg) 
	{
		super(mesg);
	}
}
