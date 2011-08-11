package org.opensafety.hishare.controller;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opensafety.hishare.service.interfaces.http.UploadParcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import sun.tools.tree.ThisExpression;

@Controller
@RequestMapping("/UploadParcel")
public class UploadParcelController
{
	@Autowired
	UploadParcel uploadParcel;
	
	Log log = LogFactory.getLog(this.getClass());
	
	@RequestMapping(value="/file", method = RequestMethod.POST)
	public ModelAndView
	        handleFormUpload(@RequestParam("username") String username,
	                         @RequestParam("authenticationId") String authenticationId,
	                         @RequestParam("parcelName") String parcelName,
	                         @RequestParam("daysToLive") Integer daysToLive,
	                         @RequestParam("file") MultipartFile file) throws IOException
	{
		ModelAndView mav;
		
		byte[] payload = null;
		
		if(!file.isEmpty())
		{
			payload = file.getBytes();
		}
		
		mav = new ModelAndView("outputString");
		String[] accessInfo = uploadParcel.uploadParcel(username, authenticationId, parcelName,
		                                                daysToLive, payload);
		String output = accessInfo[0]+":"+accessInfo[1];
		mav.addObject("string", output);
		
		return mav;
	}
	
	@RequestMapping(value="/raw", method = RequestMethod.POST)
	public ModelAndView
	        handleFormUpload(@RequestParam("username") String username,
	                         @RequestParam("authenticationId") String authenticationId,
	                         @RequestParam("parcelName") String parcelName,
	                         @RequestParam("daysToLive") Integer daysToLive,
	                         @RequestParam("data") String payload) throws IOException
	{
		ModelAndView mav;
		
		mav = new ModelAndView("outputString");
		String[] accessInfo = uploadParcel.uploadParcel(username, authenticationId, parcelName,
		                                                daysToLive, payload.getBytes());
		String output = accessInfo[0]+":"+accessInfo[1];
		mav.addObject("string", output);
		
		return mav;
	}
}