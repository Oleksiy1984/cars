package ua.nure.orlovskyi.SummaryTask4.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ua.nure.orlovskyi.SummaryTask4.model.User;

public class CookieService {
	private final String credentials_uid = "credentials_uid";
	private final String credentials_pwd = "credentials_pwd";
	private final int cookieLife = 1800; //30 minutes
	
	public boolean addCredentials(HttpServletResponse res, HttpServletRequest req, User user)
	{
		//we also want to store the information in a cookie
		//for reuse later:
		try
		{
			Cookie uidCook = new Cookie(credentials_uid, user.getLogin());
			uidCook.setMaxAge(cookieLife);
			uidCook.setPath("/");
			res.addCookie(uidCook);
			
			Cookie pwdCook = new Cookie(credentials_pwd, user.getPassword());
			pwdCook.setMaxAge(cookieLife);
			pwdCook.setPath("/");
			res.addCookie(pwdCook);
		}
		catch(Exception ex)
		{
			return false;
		}
		return true;
	}
	
	/**
	 * Clear the credential cookies
	 * @param req the HttpServletRequest
	 * @param res the HttpServletResponse
	 * @return true on success else false
	 */
	public boolean clearCredentials(HttpServletRequest req, HttpServletResponse res)
	{
		try
		{
			Cookie[] MyCookies = req.getCookies();
			if (MyCookies != null && MyCookies.length > 0)
			{
				for (Cookie c : MyCookies)
				{
					if (c.getName().toLowerCase().contains("credentials"))
					{
						//expire the cookie
						c.setPath("/");
						c.setMaxAge(0);
						res.addCookie(c);
					}
				}
			}
		}
		catch (Exception ex)
		{
			return false;
		}
		return true;
	}
}
