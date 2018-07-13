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

public class ManagerRejectOrderAction implements Action {
	private static final Logger LOGGER = LoggerFactory.getLogger(ManagerRejectOrderAction.class);

	/**
	 * This method privide the ability to reject order.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LOGGER.info("Manager reject order action");
		HttpSession session = request.getSession();
		String rejectId = (String) session.getAttribute("rejectId");
		String comment = request.getParameter("comment");
		ServletContext context = session.getServletContext();
		DAOFactory dao = (DAOFactory) context.getAttribute("DAOManager");
		OrderService orderService = new OrderService(dao);
		Integer row = orderService.rejectOrder(Integer.parseInt(rejectId), comment);
		if (row == 1) {
			session.setAttribute("UpdateOrder", "You have successfully reject order.");
		}
		List<Order> orders = orderService.getAllOrdersNotApproved();
		session.setAttribute("orders", orders);
		return "/frontController/orders";
	}

}
