package ua.nure.orlovskyi.SummaryTask4.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.nure.orlovskyi.SummaryTask4.controller.Action;
import ua.nure.orlovskyi.SummaryTask4.service.CookieService;


public class InvalidateSessionAndRemoveCookiesAction implements Action {
	private static final Logger LOGGER = LoggerFactory.getLogger(InvalidateSessionAndRemoveCookiesAction.class);

	/**
	 * This method invalidate session and remove cookies.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LOGGER.info("Invalidate session and remove cookies action");
		HttpSession session = request.getSession();
		session.invalidate();
		CookieService cmgr = new CookieService();
		cmgr.clearCredentials(request, response);
		return "/frontController/login";
	}

}
