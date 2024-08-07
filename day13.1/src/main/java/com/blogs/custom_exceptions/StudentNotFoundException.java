package com.blogs.custom_exceptions;

@SuppressWarnings("serial")
public class StudentNotFoundException extends RuntimeException 
{
	public StudentNotFoundException(String mesg) 
	{
		super(mesg);
	}
}
