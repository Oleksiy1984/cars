package ua.nure.orlovskyi.SummaryTask4.customtag;

import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import ua.nure.orlovskyi.SummaryTask4.model.User;

/**
 * prints hello to user
 *
 */
public class HelloTag extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();
        StringBuffer str = new StringBuffer(100);
        User user = (User) session.getAttribute("authorized_user");
        
        String localeString = (String) session.getAttribute("locale");
        if ("ru".equals(localeString)) {
            str.append("Здравствуй,  ").append(user.getName()).append("!<br />");
            str.append("Вы вошли как ").append(user.getRole()).append(".<br />");
            str.append("<b>Ваш id ").append(user.getId()).append(".</b><br />");
        } else {
            str.append("Hello, ").append(user.getName()).append("!<br />");
            str.append("You are loggon as ").append(user.getRole()).append(".<br />");
            str.append("<b>Your id is ").append(user.getId()).append(".</b><br />");
        }
        try {
            JspWriter out = pageContext.getOut();
            out.write(str.toString());
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }
}

