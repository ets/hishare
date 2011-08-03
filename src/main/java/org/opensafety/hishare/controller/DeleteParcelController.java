package org.opensafety.hishare.controller;

import java.io.IOException;

import org.opensafety.hishare.service.interfaces.DeleteParcel;
import org.opensafety.hishare.service.interfaces.DownloadParcel;
import org.opensafety.hishare.service.interfaces.UploadParcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/DeleteParcel")
public class DeleteParcelController
{
	@Autowired
	DeleteParcel deleteParcel;
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView
	        handleFormUpload(@RequestParam("username") String username,
	                         @RequestParam("authenticationId") String authenticationId,
	                         @RequestParam("parcelId") String parcelId,
	                         @RequestParam("parcelPassword") String parcelPassword)
	{
		ModelAndView mav = new ModelAndView("DeleteParcel");
		String result = deleteParcel.deleteParcel(username, authenticationId, parcelId, parcelPassword);
		mav.addObject("result", result);
		
		return mav;
	}
}