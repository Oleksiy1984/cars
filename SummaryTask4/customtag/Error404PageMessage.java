package ua.nure.orlovskyi.SummaryTask4.customtag;

import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Error404PageMessage extends TagSupport {
	
	
	private static final long serialVersionUID = 1L;

	@Override
	public int doStartTag() throws JspException {
		HttpSession session = pageContext.getSession();
		String message;
		if(session.getAttribute("locale") ==null) {
			message = "404. Page not found.";
		}
		else {
			if(session.getAttribute("locale").equals("ru")) {
				message = "404. Страница не найдена.";
			}
			else {
				message = "404. Page not found.";
			}
		}
		

		StringBuffer str = new StringBuffer(100);
		str.append(message).append("<br />");
		try {
			JspWriter out = pageContext.getOut();
			out.write(str.toString());
		} catch (IOException e) {
			throw new JspException(e.getMessage());
		}
		return SKIP_BODY;
	}
}
