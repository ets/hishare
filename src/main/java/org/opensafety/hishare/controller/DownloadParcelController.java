package org.opensafety.hishare.controller;

import org.opensafety.hishare.service.interfaces.http.DownloadParcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/DownloadParcel")
public class DownloadParcelController
{
	@Autowired
	DownloadParcel downloadParcel;
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView handleFormUpload(@RequestParam("username") String username,
	                                     @RequestParam("authenticationId") String authenticationId,
	                                     @RequestParam("parcelId") String parcelId,
	                                     @RequestParam("parcelPassword") String parcelPassword)
	{
		ModelAndView mav;
		
		mav = new ModelAndView("outputBytes");
		byte[] payload = downloadParcel.downloadParcel(username, authenticationId, parcelId,
		                                               parcelPassword);
		mav.addObject("bytes", payload);
		
		return mav;
	}
}