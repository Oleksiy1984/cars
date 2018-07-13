package ua.nure.orlovskyi.SummaryTask4.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This servlet implements front controller pattern.
 * Controller consist of only single servlet. It delegates
 * logic implementation to action classes. In general, servlet just
 * receives request and sends forward or redirect.
 */
@WebServlet("/frontController/*")
@MultipartConfig
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(FrontController.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FrontController() {
		super();
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Action action = AbstractActionFactory.getInstance().getAction(request);
			if (action == null) {
				request.getRequestDispatcher("/WEB-INF/jsp/error404.jsp").forward(request, response);
				return;
			}
			String view = action.execute(request, response);

			if (view.equals(request.getPathInfo().substring(1))) {
				request.getRequestDispatcher("/WEB-INF/jsp/" + view + ".jsp").forward(request, response);
			} else {
				response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + view));
			}

		} catch (Exception e) {
			LOGGER.error("Executing action failed.", e);
		}
	}
}
