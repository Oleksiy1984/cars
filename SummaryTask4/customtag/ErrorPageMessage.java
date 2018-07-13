package ua.nure.orlovskyi.SummaryTask4.customtag;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class ErrorPageMessage extends TagSupport{
	 @Override
	    public int doStartTag() throws JspException {

	        HttpSession session = pageContext.getSession();
	        //language of response depends on language of last page
	        String localeString = (String) session.getAttribute("locale");
	        String message;
	        String link;
	        if ("ru".equals(localeString)) {
	            message = "Ошибка";
	            link = "<a href='/final/frontController/login'>Вернуться</a>";
	        } else {
	            message = "Erorr";
	            link = "<a href='/final/frontController/login'>Login page</a>";
	        }
	        StringBuffer str = new StringBuffer(100);
	        //str.append(message).append("<br />");
	        str.append(link);
	        try {
	            JspWriter out = pageContext.getOut();
	            out.write(str.toString());
	        } catch (IOException e) {
	            throw new JspException(e.getMessage());
	        }
	        return SKIP_BODY;
	    }

}
