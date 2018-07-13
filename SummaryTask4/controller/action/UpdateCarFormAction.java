package ua.nure.orlovskyi.SummaryTask4.controller.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nure.orlovskyi.SummaryTask4.controller.Action;
import ua.nure.orlovskyi.SummaryTask4.dao.factory.DAOFactory;
import ua.nure.orlovskyi.SummaryTask4.model.Brand;
import ua.nure.orlovskyi.SummaryTask4.model.Car;
import ua.nure.orlovskyi.SummaryTask4.model.CarType;
import ua.nure.orlovskyi.SummaryTask4.model.Transmission;
import ua.nure.orlovskyi.SummaryTask4.service.CarService;

public class UpdateCarFormAction implements Action {
	private static final Logger LOGGER = LoggerFactory.getLogger(UpdateCarFormAction.class);

	/**
	 * This method privides the ability to update car.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LOGGER.info("Update car form action");
		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();
		DAOFactory dao = (DAOFactory) context.getAttribute("DAOManager");
		CarService carService = new CarService(dao);
		int id = Integer.parseInt(request.getParameter("update"));
		Car car = carService.getCarById(id);
		session.setAttribute("selectedCarType", car.getCarType().name());
		session.setAttribute("selectedBrand", car.getBrand().name());
		session.setAttribute("selectedTransmission", car.getTransmission().name());
		session.setAttribute("carType", CarType.values());
		session.setAttribute("brands", Brand.values());
		session.setAttribute("transmissions", Transmission.values());
		session.setAttribute("car", car);
		return "/frontController/updatecarform";

	}

}
