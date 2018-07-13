package ua.nure.orlovskyi.SummaryTask4.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.nure.orlovskyi.SummaryTask4.controller.Action;

public class ReserveFormAction implements Action {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReserveFormAction.class);

	/**
	 * This method privides the ability to reserve car.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LOGGER.info("Reserve form action");
		return "reserveform";
	}

}
