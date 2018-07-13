package ua.nure.orlovskyi.SummaryTask4.controller.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.nure.orlovskyi.SummaryTask4.controller.Action;

public class ManagerRejectOrderFormAction implements Action {

	private static final Logger LOGGER = LoggerFactory.getLogger(ManagerProcessOrderAction.class);

	/**
	 * This method privide the ability to reject order.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LOGGER.info("Manager reject order form action");
		HttpSession session = request.getSession();
		String rejectId = (String) request.getParameter("update");
		session.setAttribute("rejectId", rejectId);
		return "rejectorderform";
	}

}
