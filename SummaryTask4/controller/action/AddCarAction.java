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

public class AddCarAction implements Action {
	private static final Logger LOGGER = LoggerFactory.getLogger(AddCarAction.class);

	/**
	 * This method provides ability to add car to the rental system.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LOGGER.info("Add car action");
		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();
		DAOFactory dao = (DAOFactory) context.getAttribute("DAOManager");
		CarService carService = new CarService(dao);

		Car car = new Car();
		car.setBrand(Brand.valueOf(request.getParameter("brand")));
		car.setModel(request.getParameter("model"));
		car.setCarType(CarType.valueOf(request.getParameter("carType")));
		car.setColor(request.getParameter("color"));
		car.setTransmission(Transmission.valueOf(request.getParameter("transmission")));
		car.setPrice(Double.valueOf(request.getParameter("price")));

		Integer row = carService.addCar(car);
		if (row == 1) {
			session.setAttribute("addCarSuccess", "You have successfully added the car.");
		} else {
			session.setAttribute("addCarError", "The car have not been added.");
		}
		return "/frontController/cars";
	}

}
