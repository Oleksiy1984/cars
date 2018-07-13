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
import ua.nure.orlovskyi.SummaryTask4.model.Accident;
import ua.nure.orlovskyi.SummaryTask4.model.Order;
import ua.nure.orlovskyi.SummaryTask4.service.AccidentService;
import ua.nure.orlovskyi.SummaryTask4.service.OrderService;

public class RegisterAccidentAction implements Action {
	private static final Logger LOGGER = LoggerFactory.getLogger(RegisterAccidentAction.class);

	/**
	 * This method privides the ability to register accident.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LOGGER.info("Register accident action");
		String orderId = request.getParameter("orderId");
		String loss = request.getParameter("loss");
		String comment = request.getParameter("comment");

		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();
		DAOFactory dao = (DAOFactory) context.getAttribute("DAOManager");
		OrderService orderService = new OrderService(dao);
		Order order = orderService.getOrderById(Integer.parseInt(orderId));

		Accident accident = new Accident();
		accident.setComment(comment);
		accident.setLoss(Double.parseDouble(loss));
		accident.setOrder(order);

		AccidentService accidentService = new AccidentService(dao);
		Integer row = accidentService.addAccident(accident);
		if (row == 1) {
			session.setAttribute("Accident", "Accident successfully added.");
		} else {
			session.setAttribute("Accident", "You attempt failed.");
		}
		List<Order> list = orderService.getCurrentOrders();
		session.setAttribute("currentOrders", list);
		return "/frontController/accidentform";
	}

}
