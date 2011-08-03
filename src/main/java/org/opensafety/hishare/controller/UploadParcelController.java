package org.opensafety.hishare.controller;

import java.io.IOException;

import org.opensafety.hishare.service.interfaces.UploadParcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/UploadParcel")
public class UploadParcelController
{
	@Autowired
	UploadParcel uploadParcel;
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView
	        handleFormUpload(@RequestParam("username") String username,
	                         @RequestParam("authenticationId") String authenticationId,
	                         @RequestParam("parcelName") String parcelName,
	                         @RequestParam("daysToLive") Integer daysToLive,
	                         @RequestParam("file") MultipartFile file) throws IOException
	{
		byte[] payload = null;
		
		if(!file.isEmpty())
		{
			payload = file.getBytes();
		}
		
		ModelAndView mav = new ModelAndView("UploadParcel");
		String[] accessInfo = uploadParcel.uploadParcel(username, authenticationId, parcelName,
		                                                daysToLive, payload);
		mav.addObject("parcelId", accessInfo[0]);
		mav.addObject("parcelPassword", accessInfo[1]);
		
		return mav;
	}
}