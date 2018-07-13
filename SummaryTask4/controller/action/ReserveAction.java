package ua.nure.orlovskyi.SummaryTask4.controller.action;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nure.orlovskyi.SummaryTask4.controller.Action;
import ua.nure.orlovskyi.SummaryTask4.dao.factory.DAOFactory;
import ua.nure.orlovskyi.SummaryTask4.model.Car;
import ua.nure.orlovskyi.SummaryTask4.model.Order;
import ua.nure.orlovskyi.SummaryTask4.model.User;
import ua.nure.orlovskyi.SummaryTask4.service.CarService;


public class ReserveAction implements Action {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReserveAction.class);

	/**
	 * This method privides the ability to reserve car.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LOGGER.info("Reserve action");
		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();
		DAOFactory dao = (DAOFactory) context.getAttribute("DAOManager");
		Car car = null;

		int id = Integer.parseInt(request.getParameter("carId"));
		if (id > 0) {
			car = new CarService(dao).getCarById(id);
			session.setAttribute("car", car);

			Order order = (Order) session.getAttribute("order");
			DecimalFormat df = new DecimalFormat("#.00");
			DecimalFormatSymbols dfs = new DecimalFormatSymbols();
			dfs.setDecimalSeparator('.');
			df.setDecimalFormatSymbols(dfs);
			Double total = car.getPrice() * order.getNumberDaysRent();

			order.setEstimatedPrice(Double.valueOf(df.format(total)));
			order.setCar(car);
			
			User user = (User) session.getAttribute("authorized_user");
			order.setUser(user);
	}
		return "/frontController/reserveform";
	}
}
