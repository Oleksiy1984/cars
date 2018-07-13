package ua.nure.orlovskyi.SummaryTask4.controller.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nure.orlovskyi.SummaryTask4.controller.Action;
import ua.nure.orlovskyi.SummaryTask4.dao.factory.DAOFactory;
import ua.nure.orlovskyi.SummaryTask4.model.Car;
import ua.nure.orlovskyi.SummaryTask4.service.CarService;

public class UploadAction implements Action {

	private static final Logger LOGGER = LoggerFactory.getLogger(UploadAction.class);

	/**
	 * This method privides the ability to upload car photo.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		LOGGER.info("Upload action");
		String description = request.getParameter("description"); // Retrieves <input type="text" name="description">
		Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
		String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.

		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();
		DAOFactory dao = (DAOFactory) context.getAttribute("DAOManager");
		final String path = context.getRealPath("/images");
		if (!description.equals("")) {
			fileName = description;
		}
		String url = "images/" + fileName;
		try (OutputStream outStream = new FileOutputStream(new File(path + File.separator + fileName));
				InputStream filecontent = filePart.getInputStream()) {
			int read = 0;
			final byte[] bytes = new byte[1024];

			while ((read = filecontent.read(bytes)) != -1) {
				outStream.write(bytes, 0, read);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			session.setAttribute("updateCarError", "Attempt to upload image failed.");
			return "/frontController/cars";
		}
		LOGGER.info("File uploaded successfully");

		String id = request.getParameter("id");

		CarService carService = new CarService(dao);
		Car car = carService.getCarById(Integer.parseInt(id));
		car.setPhoto(url);
		Integer row = carService.updateCarPhoto(car);
		if (row == 1) {
			session.setAttribute("UpdateCar", "You have successfully uploaded image.");
		} else {
			session.setAttribute("updateCarError", "Attempt to upload image failed.");
		}
		return "/frontController/cars";
	}

}
