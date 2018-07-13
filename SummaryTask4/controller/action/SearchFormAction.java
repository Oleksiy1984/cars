package ua.nure.orlovskyi.SummaryTask4.controller.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.nure.orlovskyi.SummaryTask4.controller.Action;


public class SearchFormAction implements Action {
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchFormAction.class);
	
	/**
	 * Search form.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LOGGER.info("Search form action");
		return "search";
	}

}
