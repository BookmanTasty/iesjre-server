package com.leyvadev.iesjrecontrol.logincontrol.services;

import com.leyvadev.iesjrecontrol.logincontrol.dto.LoginResponse;

import java.util.Map;

public interface LoginService {
    public LoginResponse<String> login(String username, String password) throws Exception;
}
