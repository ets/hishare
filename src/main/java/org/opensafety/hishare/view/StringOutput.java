package org.opensafety.hishare.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

public class StringOutput extends AbstractView
{

	@Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception
    {
		String output = (String) model.get("string");
		response.getOutputStream().print(output);
		response.getOutputStream().flush();
    }
	
}
