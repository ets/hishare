package org.opensafety.hishare.dao.implementation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opensafety.hishare.dao.interfaces.PayloadDao;

public class FilePayloadDao implements PayloadDao
{
	private Log log;
	
	public FilePayloadDao()
	{
		log = LogFactory.getLog(this.getClass());
	}
	
	public boolean savePayload(String location, byte[] payload)
    {
		log.info("SAVING PAYLOAD: "+new String(payload)+" AT LOCATION: "+location);
		
		String specificLocation = "Parcels/"+location+".parcel";
		
		FileOutputStream fileOut = null;
        try
        {
	        fileOut = new FileOutputStream(specificLocation);
	        fileOut.write(payload);
	        fileOut.close();
        }
		catch(FileNotFoundException e)
        {
	        e.printStackTrace();
	        return false;
        }
        catch(IOException e)
        {
	        e.printStackTrace();
	        return false;
        }
		
	    return true;
    }

	public byte[] retrievePayload(String location)
	{
		log.info("RETRIEVING PAYLOAD: "+location);
		
		String specificLocation = "Parcels/"+location+".parcel";
		
		FileInputStream fileIn = null;
		byte[] payload;
		
		try
		{
			File file = new File(specificLocation);
			fileIn = new FileInputStream(specificLocation);
			payload = new byte[(int) file.length()];
			fileIn.read(payload);
			fileIn.close();
        }
		catch(FileNotFoundException e)
        {
	        e.printStackTrace();
	        return null;
        }
        catch(IOException e)
        {
	        e.printStackTrace();
	        return null;
        }
		
		return payload;
    }
	
}
