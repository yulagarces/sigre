/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.utils.emails;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import service.utils.security.utils.Constant;

/**
 *
 * @author danny
 */
public class EmailUtils {

    private EmailUtils() {
    }

    private static EmailUtils instance = new EmailUtils();

    public static EmailUtils getInstace() {
        return instance;
    }

    public boolean sendEmail(String receiver, String code, String msj) {

        try {
            Email email = new SimpleEmail();
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator(Constant.NAME_MAIL, Constant.getInstance().getPASSWORD_EMAIL()));
            email.setSSLOnConnect(true);
            email.setFrom(Constant.USERNAME_EMAIL);
            email.setSubject(msj);
            email.setMsg(msj.toUpperCase() + ": \n" + code);
            email.addTo(receiver);
            email.send();
            return true;
        } catch (EmailException ex) {
            ex.printStackTrace();
            return false;
        }

    }

}
