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
import ua.nure.orlovskyi.SummaryTask4.model.Brand;
import ua.nure.orlovskyi.SummaryTask4.model.Car;
import ua.nure.orlovskyi.SummaryTask4.model.CarType;
import ua.nure.orlovskyi.SummaryTask4.model.Transmission;
import ua.nure.orlovskyi.SummaryTask4.service.CarService;

public class UpdateCarAction implements Action {
	private static final Logger LOGGER = LoggerFactory.getLogger(UpdateCarAction.class);

	/**
	 * This method privides the ability to update car.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LOGGER.info("Update car action");
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
		car.setId(Integer.parseInt(request.getParameter("id")));
		
		Integer row = carService.updateCar(car);
		if(row == 1) {
			session.setAttribute("updateCarSuccess", "You have successfully updated car.");
		}
		else {
			session.setAttribute("updateCarError", "Attempt to update car failed.");
		}
		List<Car> vehiclesList = carService.getAllCars();
		session.setAttribute("vehiclesList", vehiclesList);
		return "/frontController/cars";
	}

}
