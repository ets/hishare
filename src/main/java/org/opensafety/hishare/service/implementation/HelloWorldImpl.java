package org.opensafety.hishare.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.opensafety.hishare.service.interfaces.HelloWorld;

import org.opensafety.hishare.model.Parcel;
import org.opensafety.hishare.dao.interfaces.ParcelDao;

public class HelloWorldImpl implements HelloWorld
{
	private Log log = LogFactory.getLog(this.getClass());
	private ParcelDao parcelDao;
	
	public ParcelDao getParcelDao()
    {
    	return parcelDao;
    }

	public void setParcelDao(ParcelDao parcelDao)
    {
    	this.parcelDao = parcelDao;
    }

	public String greet(String name)
	{
		if(parcelDao == null)
		{
			System.out.println("Is Null");
		}
		else
		{
			System.out.println("Not null");
		}
		parcelDao.checkHPD();
		log.info("about to make a parcel");
		parcelDao.createParcel(new Parcel());
		log.info(name);
		return "Hello, "+name+"!";
	}
}