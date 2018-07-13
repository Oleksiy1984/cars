package ua.nure.orlovskyi.SummaryTask4.controller.action;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.nure.orlovskyi.SummaryTask4.controller.Action;

public class ChangeLocaleAction implements Action {
	private static final Logger LOGGER = LoggerFactory.getLogger(ChangeLocaleAction.class);

	/**
	 * This method provides ability to change locale in current session in the rental system.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LOGGER.info("Change locale action");
		HttpSession session = request.getSession();
		String language = request.getParameter("lang");
		String path = request.getParameter("path");
		Locale locale = new Locale(language);
		if (language.equals("ru") || language.equals("en")) {
			session.setAttribute("locale", locale.toString());
			Config.set(request.getSession(), Config.FMT_LOCALE, locale);
		}
		String name = path.substring(path.lastIndexOf(request.getContextPath()) + request.getContextPath().length());
		LOGGER.info("Current locale - "  + session.getAttribute("locale").toString());
		return name;
	}

}
