package ua.nure.orlovskyi.SummaryTask4.controller.action;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nure.orlovskyi.SummaryTask4.controller.Action;
import ua.nure.orlovskyi.SummaryTask4.dao.factory.DAOFactory;
import ua.nure.orlovskyi.SummaryTask4.model.Car;
import ua.nure.orlovskyi.SummaryTask4.model.Role;
import ua.nure.orlovskyi.SummaryTask4.model.User;
import ua.nure.orlovskyi.SummaryTask4.service.CarService;
import ua.nure.orlovskyi.SummaryTask4.service.ClientService;
import ua.nure.orlovskyi.SummaryTask4.service.CookieService;

public class LoginAction implements Action {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginAction.class);

	/**
	 * This method puts into session all necessary data about logged user.
	 */
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LOGGER.info("Login action");
		User user = null;
		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();
		DAOFactory dao = (DAOFactory) context.getAttribute("DAOManager");
		ClientService clientService = new ClientService(dao);
		String uid = request.getParameter("uid");
		String pwd = request.getParameter("pwd");

		if (validateParams(uid, pwd)) {
			user = clientService.getUserByCredential(uid, pwd);
		}
		else {
			return invalidCreadential(request, response);
		}

		
		if (user == null) {
			return invalidCreadential(request, response);
		}

		session.setAttribute("authorized_user", user);
		session.setMaxInactiveInterval(1800);

		// add cookie if user wants to
		if (request.getParameter("rememberMe") != null) {
			String rememberMe = request.getParameter("rememberMe");
			if (rememberMe.equalsIgnoreCase("ON")) {
				CookieService cmgr = new CookieService();
				cmgr.addCredentials(response, request, user);
			}
		}

		if (user.getRole() == Role.Admin) {
			List<Car> vehiclesList = new CarService(dao).getAllCars();
			session.setAttribute("vehiclesList", vehiclesList);
			return "/frontController/cars";
		}
		if (user.getRole() == Role.Manager) {
			return "/frontController/orders";
		}

		return "/frontController/search";

	}
	
	public boolean validateParams(String uid, String pwd) {
		if (uid == null)
			return false;
		if (pwd == null)
			return false;
		if (uid.length() == 0 || pwd.length() == 0) {
			// can't log in
			return false;
		}
		return true;
	}


	private String invalidCreadential(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setAttribute("errorMessage", "Invalid username or password. Please try again.");
		return "/frontController/login";

	}
}
