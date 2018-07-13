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
import ua.nure.orlovskyi.SummaryTask4.model.Order;
import ua.nure.orlovskyi.SummaryTask4.model.User;
import ua.nure.orlovskyi.SummaryTask4.service.OrderService;

public class BookingHistoryAction implements Action {
	private static final Logger LOGGER = LoggerFactory.getLogger(BookingHistoryAction.class);

	/**
	 * This method provides ability to see booking hystoty in the rental system.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LOGGER.info("Bookin history action");
		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();
		DAOFactory dao = (DAOFactory) context.getAttribute("DAOManager");
		OrderService orderService = new OrderService(dao);
		User user = (User) session.getAttribute("authorized_user");
		List<Order> orders = orderService.getOrdersByClientId(user.getId());
		LOGGER.info(orders.get(1).getDamage().toString());
		session.setAttribute("orders", orders);
		return "history";
	}

}
