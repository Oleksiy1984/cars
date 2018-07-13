package ua.nure.orlovskyi.SummaryTask4.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.nure.orlovskyi.SummaryTask4.controller.Action;

public class LogoutAction implements Action {
	private static final Logger LOGGER = LoggerFactory.getLogger(LogoutAction.class);

	/**
	 * This method provides user logout.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LOGGER.info("Logout action");
		HttpSession s = request.getSession();
		s.invalidate();
		return "/frontController/login";
	}

}
