package ua.nure.orlovskyi.SummaryTask4.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.nure.orlovskyi.SummaryTask4.controller.Action;
import ua.nure.orlovskyi.SummaryTask4.model.Brand;
import ua.nure.orlovskyi.SummaryTask4.model.CarType;
import ua.nure.orlovskyi.SummaryTask4.model.Transmission;

public class AddCarFormAction implements Action {
	private static final Logger LOGGER = LoggerFactory.getLogger(AddCarFormAction.class);

	/**
	 * This method provides ability to add car to the rental system.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LOGGER.info("Add car form action");
		HttpSession session = request.getSession();
		session.setAttribute("carType", CarType.values());
		session.setAttribute("brands", Brand.values());
		session.setAttribute("transmissions", Transmission.values());
		return "addcar";
	}

}
