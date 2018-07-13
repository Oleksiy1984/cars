package ua.nure.orlovskyi.SummaryTask4.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.nure.orlovskyi.SummaryTask4.controller.Action;

public class Error404Action implements Action {
	private static final Logger LOGGER = LoggerFactory.getLogger(Error404Action.class);
	
	/**
	 * This method provides ability to handle 404 error code.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LOGGER.info("Error 404 action");
		return "error404";
	}

}
