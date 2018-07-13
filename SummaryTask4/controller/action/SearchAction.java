package ua.nure.orlovskyi.SummaryTask4.controller.action;


import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.orlovskyi.SummaryTask4.controller.Action;
import ua.nure.orlovskyi.SummaryTask4.model.Order;
import ua.nure.orlovskyi.SummaryTask4.service.DateService;

public class SearchAction implements Action {

	/**
	 * Register order.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		String pickUpDate = request.getParameter("pickUpDate");
		String returnDate = request.getParameter("returnDate");
		String pickLocation = request.getParameter("pick");
		String dropLocation = request.getParameter("drop");
		Boolean isDriver =  request.getParameter("driver")!=null;
		Integer result = (int) DateService.getDateDiff(pickUpDate, returnDate, TimeUnit.DAYS);
		Order order = new Order();
		order.setIsDriver(isDriver);
		order.setPickUpLocation(pickLocation);
		order.setDropOffLocation(dropLocation);
		order.setDropOffDate(DateService.convert(returnDate));
		order.setPickUpDate(DateService.convert(pickUpDate));
		order.setNumberDaysRent(result);
		
		session.setAttribute("order", order);
		session.removeAttribute("sort");

		return "/frontController/index";
	}

}
