package org.opensafety.hishare.view;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

public class ByteOutput extends AbstractView
{

	@Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception
    {
		byte[] data = (byte[]) model.get("bytes");
		
		response.setContentType("application/octet-stream");
		response.getOutputStream().write(data);
		response.getOutputStream().flush();
    }
	
}
