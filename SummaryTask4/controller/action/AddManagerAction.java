package ua.nure.orlovskyi.SummaryTask4.controller.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nure.orlovskyi.SummaryTask4.controller.Action;
import ua.nure.orlovskyi.SummaryTask4.dao.factory.DAOFactory;
import ua.nure.orlovskyi.SummaryTask4.model.Gender;
import ua.nure.orlovskyi.SummaryTask4.model.User;
import ua.nure.orlovskyi.SummaryTask4.service.UserService;

public class AddManagerAction implements Action {
	private static final Logger LOGGER = LoggerFactory.getLogger(AddManagerAction.class);

	/**
	 * This method provides ability to register manager to the rental system.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LOGGER.info("Add manager action");
		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();
		DAOFactory dao = (DAOFactory) context.getAttribute("DAOManager");
		UserService userService = new UserService(dao);
		User user = new User();
		user.setLogin(request.getParameter("login"));
		user.setPassword(request.getParameter("password"));
		user.setEmail(request.getParameter("email"));
		user.setGender(Gender.valueOf(request.getParameter("gender")));
		user.setMobile(request.getParameter("mobile"));
		user.setPassport(request.getParameter("passport"));
		user.setName(request.getParameter("name"));
		Integer row = userService.addManager(user);

		if (row == 1) {
			session.setAttribute("registerManagerSuccess", "You have successfully registered manager.");
		} else {
			session.setAttribute("registerManagerError", "Duplicates login or passport found.");
		}

		return "/frontController/users";
	}

}
