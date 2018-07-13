package ua.nure.orlovskyi.SummaryTask4.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.orlovskyi.SummaryTask4.controller.Action;

public class RegisterFormAction implements Action {

	/**
	 * This method provides ability to register user.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {	
		return "register";
	}

}
