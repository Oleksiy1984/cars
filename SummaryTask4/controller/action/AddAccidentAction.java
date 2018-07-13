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

public class AddAccidentAction implements Action {
	private static final Logger LOGGER = LoggerFactory.getLogger(AddAccidentAction.class);
	/**
	 * This method provides ability to register accident.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LOGGER.info("Add accident action");
		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();
		DAOFactory dao = (DAOFactory) context.getAttribute("DAOManager");
		OrderService orderService = new OrderService(dao);
		List<Order> list = orderService.getCurrentOrders();
		session.setAttribute("currentOrders", list);
		return "accidentform";
	}

}
