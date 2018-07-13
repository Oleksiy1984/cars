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
import ua.nure.orlovskyi.SummaryTask4.service.ClientService;

public class RegisterAction implements Action {
	private static final Logger LOGGER = LoggerFactory.getLogger(RegisterAction.class);

	/**
	 * This method register user.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LOGGER.info("Register action");
		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();
		DAOFactory dao = (DAOFactory) context.getAttribute("DAOManager");
		ClientService clientService = new ClientService(dao);
		User user = new User();
		user.setLogin(request.getParameter("login"));
		user.setPassword(request.getParameter("password"));
		user.setEmail(request.getParameter("email"));
		user.setGender(Gender.valueOf(request.getParameter("gender")));
		user.setMobile(request.getParameter("mobile"));
		user.setPassport(request.getParameter("passport"));
		user.setName(request.getParameter("name"));

		if(!userIsValid(user)) {
			session.setAttribute("registerError", "User is not valid.");
			return "register";
		}

		Integer result = clientService.addClient(user);
		if (result == 1) {
			session.setAttribute("SuccessMessage", "You have been successfully registered.");
			return "/frontController/login";
		} else {
			session.setAttribute("registerError", "Duplicates login or passport found.");
			return "/frontController/register";
		}
	}
	
	private Boolean userIsValid(User user) {
		if (user.getLogin() == null || user.getLogin().isEmpty()) {
			return false;
		}
		if (user.getPassword() == null || user.getPassword().isEmpty()) {
			return false;
		}
		if (user.getName() == null || user.getName().isEmpty()) {
			return false;
		}
		if (user.getPassport() == null || user.getPassport().isEmpty()) {
			return false;
		}
		if (user.getEmail() == null || user.getEmail().isEmpty()) {
			return false;
		}
		if (user.getMobile() == null || user.getMobile().isEmpty()) {
			return false;
		}
		if (user.getGender() == null || user.getGender().name().isEmpty()) {
			return false;
		}
		return true;
	}


}
