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
import ua.nure.orlovskyi.SummaryTask4.model.Order;
import ua.nure.orlovskyi.SummaryTask4.service.CarService;

public class IndexAction implements Action {
	private static final Logger LOGGER = LoggerFactory.getLogger(IndexAction.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LOGGER.info("Index action");
		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();
		DAOFactory dao = (DAOFactory) context.getAttribute("DAOManager");
		Order order = (Order) session.getAttribute("order");
		if (order == null) {
			return "/frontController/search";
		}
		CarService carService = new CarService(dao);
		List<Car> cars = carService.getAllAvailableCarsAtDate(order.getPickUpDate(), order.getDropOffDate());
		session.setAttribute("cars", cars);
		session.setAttribute("allCars", cars);
		session.setAttribute("carType", CarType.values());
		session.setAttribute("brands", Brand.values());
		return "index";
	}

}
