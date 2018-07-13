package ua.nure.orlovskyi.SummaryTask4.controller.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nure.orlovskyi.SummaryTask4.controller.Action;
import ua.nure.orlovskyi.SummaryTask4.dao.factory.DAOFactory;
import ua.nure.orlovskyi.SummaryTask4.model.Order;
import ua.nure.orlovskyi.SummaryTask4.service.OrderService;

public class OrderAction implements Action {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderAction.class);

	/**
	 * This method privide the ability to reserved car.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LOGGER.info("Order action");
		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();
		DAOFactory dao = (DAOFactory) context.getAttribute("DAOManager");
		Order order = (Order) session.getAttribute("order");
		OrderService orderService = new OrderService(dao);
		Integer result = orderService.makeOrder(order);
		if (result == 1) {
			session.removeAttribute("order");
			session.setAttribute("SuccessOrder", "You have successfully reserved car.");
			return "/frontController/search";
		}
		else {
			session.setAttribute("ErrorOrder", "Error occured. Please repeat your order. ");
			return "/frontController/search";
		}
	}

}
