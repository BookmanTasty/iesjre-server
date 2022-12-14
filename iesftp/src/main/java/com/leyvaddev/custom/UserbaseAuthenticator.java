package com.leyvaddev.custom;

import com.guichaguri.minimalftp.FTPConnection;
import com.guichaguri.minimalftp.api.IFileSystem;
import com.guichaguri.minimalftp.api.IUserAuthenticator;
import com.guichaguri.minimalftp.impl.NativeFileSystem;
import com.leyvaddev.dto.LoginResponse;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple user base which encodes passwords in MD5 (not really for security, it's just as an example)
 * @author Guilherme Chaguri
 */
public class UserbaseAuthenticator implements IUserAuthenticator {
    Logger logger = org.apache.logging.log4j.LogManager.getLogger(UserbaseAuthenticator.class);
    private final Map<String, byte[]> userbase = new HashMap<>();
    private final RestClient restClient = new RestClient();
    private byte[] toMD5(String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return md.digest(pass.getBytes("UTF-8"));
        } catch(Exception ex) {
            return pass.getBytes();
        }
    }
    public void registerUser(String username, String password) {
        userbase.put(username, toMD5(password));
    }

    @Override
    public boolean needsUsername(FTPConnection con) {
        return true;
    }

    @Override
    public boolean needsPassword(FTPConnection con, String username, InetAddress address) {
        return true;
    }

    @Override
    public IFileSystem authenticate(FTPConnection con, InetAddress address, String username, String password) throws AuthException {
        logger.info("Authenticating user " + username + " from " + address);
        // Check if the user exists
        LoginResponse loginResponse = restClient.loginService(username, password);
        // Check for a user with that username in the database
        if (loginResponse.getStatus().equals("200")) {
            // Check if the password is correct
            registerUser(username, password);
        }
        if(!userbase.containsKey(username)) {
            throw new AuthException();
        }
        // Gets the correct, original password
        byte[] originalPass = userbase.get(username);
        // Calculates the MD5 for the given password
        byte[] inputPass = toMD5(password);
        // Check for wrong password
        if(!Arrays.equals(originalPass, inputPass)) {
            throw new AuthException();
        }
        // Use the username as a directory for file storage
        File path = new File(System.getProperty("user.dir"), "~" + username);
        return new NativeFileSystem(path);
    }
}
