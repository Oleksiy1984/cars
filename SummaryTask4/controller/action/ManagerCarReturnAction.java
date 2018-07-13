package ua.nure.orlovskyi.SummaryTask4.controller.action;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nure.orlovskyi.SummaryTask4.controller.Action;
import ua.nure.orlovskyi.SummaryTask4.dao.factory.DAOFactory;
import ua.nure.orlovskyi.SummaryTask4.model.Car;
import ua.nure.orlovskyi.SummaryTask4.service.CarService;

public class ManagerCarReturnAction implements Action {
	private static final Logger LOGGER = LoggerFactory.getLogger(ManagerCarReturnAction.class);

	/**
	 * This method provides ability to return car from rental.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LOGGER.info("Manager car return action");
		HttpSession session = request.getSession();
		List<Car> list = null;
		ServletContext context = session.getServletContext();
		DAOFactory dao = (DAOFactory) context.getAttribute("DAOManager");
		CarService carService = new CarService(dao);
		list = carService.getAllUnAvailableCars();
		session.setAttribute("unavailableCars", list);
		return "returncarform";
	}

}
