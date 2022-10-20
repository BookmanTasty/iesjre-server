package com.leyvaddev.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leyvaddev.dto.LoginResponse;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class RestClient {

    private final Logger logger = org.apache.logging.log4j.LogManager.getLogger(RestClient.class);

    public LoginResponse loginService(String username, String password) {
        try {
            String urlString = "http://localhost:8080/login?username=" + username + "&password=" + password;
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                LoginResponse loginResponseError = new LoginResponse();
                loginResponseError.setStatus(connection.getResponseCode()+"");
                loginResponseError.setMessage(connection.getResponseMessage());
                return loginResponseError;
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
            String output;
            output = br.readLine();
            ObjectMapper objectMapper = new ObjectMapper();
            connection.disconnect();
            return objectMapper.readValue(output, LoginResponse.class);
        } catch (MalformedURLException e) {
            logger.error(e.getMessage());
            LoginResponse loginResponseError = new LoginResponse();
            loginResponseError.setStatus("500");
            loginResponseError.setMessage(e.getMessage());
            return loginResponseError;
        } catch (IOException e) {
            LoginResponse loginResponseError = new LoginResponse();
            loginResponseError.setStatus("500");
            loginResponseError.setMessage(e.getMessage());
            return loginResponseError;
        }catch (Exception e) {
            LoginResponse loginResponseError = new LoginResponse();
            loginResponseError.setStatus("500");
            loginResponseError.setMessage(e.getMessage());
            return loginResponseError;
        }
    }
}


