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


public class UsersProcessAction implements Action {
	private static final Logger LOGGER = LoggerFactory.getLogger(UsersProcessAction.class);

	/**
	 * This method privides the ability to block or unblock users.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LOGGER.info("Users process action");
		String block = request.getParameter("block");
		String unblock = request.getParameter("unblock");
		String id = request.getParameter("update");
		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();
		DAOFactory dao = (DAOFactory) context.getAttribute("DAOManager");
		UserService userService = new UserService(dao);

		if (block != null) {
			Integer row = userService.blockUser(Integer.parseInt(id));
			if (row == 1) {
				session.setAttribute("UpdateUsers", "You have successfully blocked user.");
			}
			
		} else if (unblock != null) {
			Integer row = userService.unBlockUser(Integer.parseInt(id));
			if (row == 1) {
				session.setAttribute("UpdateUsers", "You have successfully unblocked user.");
			}
		}
		session.setAttribute("users", userService.getAllUsers());
		return "/frontController/users";
	}

}
