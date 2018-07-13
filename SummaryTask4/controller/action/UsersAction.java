package ua.nure.orlovskyi.SummaryTask4.controller.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nure.orlovskyi.SummaryTask4.controller.Action;
import ua.nure.orlovskyi.SummaryTask4.dao.factory.DAOFactory;
import ua.nure.orlovskyi.SummaryTask4.service.UserService;


public class UsersAction implements Action {
	private static final Logger LOGGER = LoggerFactory.getLogger(UsersAction.class);

	/**
	 * This method privides the ability to see users info.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LOGGER.info("Users action");
		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();
		DAOFactory dao = (DAOFactory) context.getAttribute("DAOManager");
		UserService userService = new UserService(dao);
		session.setAttribute("users", userService.getAllUsers());
		return "users";
	}

}
