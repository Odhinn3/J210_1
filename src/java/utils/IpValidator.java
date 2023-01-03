/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.Stateless;

/**
 *
 * @author A.Konnov <github.com/Odhinn3>
 */
@Stateless
public class IpValidator {

    public boolean isValid(String ip){
        Pattern pattern = Pattern.compile("[0-9.]+");
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }
}
