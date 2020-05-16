//package service.utils.security.ejb;
//
//import java.util.ArrayList;
//import javax.ejb.Local;
//import javax.security.auth.login.LoginException;
//import model.Modulo;
//
///**
// *
// * @author danny
// */
//@Local
//public interface IAuthBeanLocal {
//
//    /**
//     * Make the logIn operation with DataBase information
//     *
//     * @param user logged user
//     * @param password user encrypted password
//     * @return
//     * @throws LoginException
//     */
//    boolean login(String user, String password) throws LoginException;
//
//    /**
//     * Make the logOut operation
//     */
//    void logout();
//
//    /**
//     * obtains the current authenticated user
//     *
//     * @param user
//     * @return
//     */
//    String getAuthenticatedUser(String user);
//
//    /**
//     * Get user modules
//     *
//     * @param user
//     * @return
//     */
//    ArrayList<Modulo> getUserModulos(String user);
//
//    /**
//     * Evaluates if the given password has not been used by the current user
//     *
//     * @param user
//     * @param password
//     * @return
//     */
//    boolean isValidPassword(String user, String password);
//
//    /**
//     * Makes the changr password operation
//     *
//     * @param user
//     * @param password
//     * @return
//     */
//    boolean changeUserPassword(String user, String password);
//
//    /**
//     * Evaluates if the given password has a valid length for the current user
//     * restriction
//     *
//     * @param password
//     * @return
//     * @throws LoginException
//     */
//    boolean isValidPasswordLength(String password) throws LoginException;
//
//}
