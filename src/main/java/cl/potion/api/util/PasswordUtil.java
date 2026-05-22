package cl.potion.api.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.experimental.UtilityClass;

/**
 * Util used for password pourposes.
 *
 * @author AnemonaShin (Christian Ramirez) - cramireza1997@gmail.com
 * @version 1.0.0
 * @since 17-05-2026
 */
@UtilityClass
public class PasswordUtil {

  BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  /**
   * Uses BCrypt for password encoder to BD or other uses.
   * 
   * @param pswd Password to Encrypt.
   * @return Password Encrypted.
   */
  public String passwordEncrypt(String pswd) {
    return encoder.encode(pswd);
  }

  /**
   * Compare one Password using user and db password.
   * 
   * @param userPswd Password from the user.
   * @param dbPswd   Password in the DB.
   * @return Boolean with the response.
   */
  public Boolean passwordDecrypt(String userPswd, String dbPswd) {
    return encoder.matches(userPswd, dbPswd);
  }

}
