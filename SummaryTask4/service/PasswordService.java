package ua.nure.orlovskyi.SummaryTask4.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PasswordService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PasswordService.class);

	 /**
     * Returns hashed password. Uses SHA-256 algorithm
     * @param password
     * @return
     */
    public String getHashedPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte byteData[] = md.digest();
            StringBuffer sb = new StringBuffer();
            for (byte aByteData : byteData) {
                sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
        	LOGGER.error(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Cannot hash password");
        }
    }

}
