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
import ua.nure.orlovskyi.SummaryTask4.service.OrderService;

public class ManagerProcessOrderAction implements Action {
	private static final Logger LOGGER = LoggerFactory.getLogger(ManagerProcessOrderAction.class);

	/**
	 * This method privide the ability to approved order.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LOGGER.info("Manager process order action");
		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();
		DAOFactory dao = (DAOFactory) context.getAttribute("DAOManager");
		String approved = (String) request.getParameter("approved");
		String orderId = (String) request.getParameter("update");
		
		if (approved != null) {
			OrderService orderService = new OrderService(dao);
			Integer row = orderService.setApproved(Integer.parseInt(orderId));
			if (row == 1) {
				session.setAttribute("UpdateOrder", "You have successfully approved order.");
			}
			else {
				session.setAttribute("UpdateOrder", "You attempt failed.");
			}
			List<Order> orders = orderService.getAllOrdersNotApproved();
			session.setAttribute("orders", orders);
			return "/frontController/orders";
		} 
		return "/frontController/orders";

	}

}
