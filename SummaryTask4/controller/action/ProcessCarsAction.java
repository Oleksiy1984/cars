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

public class ProcessCarsAction implements Action {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessCarsAction.class);

	/**
	 * This method privide the ability to update car.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LOGGER.info("Process cars action");
		String update = request.getParameter("update");
		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();
		DAOFactory dao = (DAOFactory) context.getAttribute("DAOManager");
		CarService carService = new CarService(dao);

		if (update != null) {
			Integer row = carService.removeCar(Integer.parseInt(update));
			if (row == 1) {
				session.setAttribute("UpdateCar", "You have successfully deleted car.");
			}
			else {
				session.setAttribute("UpdateCar", "Attempt to delete car failed.");
			}
			return "/frontController/cars";
			
		}
		
		List<Car> vehiclesList = carService.getAllCars();
		session.setAttribute("vehiclesList", vehiclesList);
		return "cars";
	}

}
