
package utils;

import javax.ejb.Stateless;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author A.Konnov <github.com/Odhinn3>
 */
@Stateless
public class UsernameValidator {
    
    public boolean isValid(String clientname){
        Pattern pattern = Pattern.compile("[а-яА-Я-,. ]+");
        Matcher matcher = pattern.matcher(clientname);
        return matcher.matches();
    }
}
