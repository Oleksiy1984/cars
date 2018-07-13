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



public class ManagerReturnCarAction implements Action {
	private static final Logger LOGGER = LoggerFactory.getLogger(ManagerReturnCarAction.class);

	/**
	 * This method privide the ability to return car.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LOGGER.info("Manager return car action");
		HttpSession session = request.getSession();
		String selectedId = request.getParameter("id");
		ServletContext context = session.getServletContext();
		DAOFactory dao = (DAOFactory) context.getAttribute("DAOManager");
		CarService carService = new CarService(dao);
		Integer row = carService.returnCar(Integer.parseInt(selectedId));
		if(row == 1) {
			session.setAttribute("SuccessReturn", "You have successfully returned car.");
		}
		else {
			session.setAttribute("SuccessReturn", "You attempt failed.");
		}
		List<Car> list = carService.getAllUnAvailableCars();
		
		session.setAttribute("unavailableCars", list);
		return "/frontController/returncarform";
	}

}
