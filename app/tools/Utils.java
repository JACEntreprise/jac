package tools;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Created by brick on 03/11/2016.
 */
public class Utils {
    private String salt;
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Utils() {
        this.salt = BCrypt.gensalt();
    }

    public String hash(String value) {
        return BCrypt.hashpw(value, salt);
    }
}
