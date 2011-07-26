package org.opensafety.hishare.implementation;

import org.opensafety.hishare.interfaces.HelloWorld;

public class HelloWorldImpl implements HelloWorld
{
	public String greet(String name)
	{
		System.out.println(name);
		return "Hello, "+name+"!";
	}
}