package com.leyvadev.iesjrecontrol.logincontrol.services.imp;

import com.leyvadev.iesjrecontrol.logincontrol.dto.LoginResponse;
import com.leyvadev.iesjrecontrol.logincontrol.model.Radcheck;
import com.leyvadev.iesjrecontrol.logincontrol.repositories.RadcheckRepository;
import com.leyvadev.iesjrecontrol.logincontrol.services.LoginService;
import com.leyvadev.iesjrecontrol.logincontrol.utils.TokenUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class LoginServiceImpl implements LoginService {

    @Inject
    RadcheckRepository radcheckRepository;

    @Override
    public LoginResponse<String> login(String username, String password)  {
        List<Radcheck> radcheckList = radcheckRepository.find("username = ?1", username).list();
        Radcheck radcheck = radcheckList.stream().filter(r -> r.getAttribute().equals("Cleartext-Password")).findFirst().orElse(null);
        if (radcheck != null && radcheck.getValue().equals(password)) {
            String token;
            try {
                token  = TokenUtils.generateToken(username,36000L, "http://iesjre.com");
            } catch (Exception e) {
                return new LoginResponse<>("500", "Error", e.getLocalizedMessage());
            }
            return new LoginResponse<>("200", "Login correcto", token);
        } else {
            return new LoginResponse<>("403" , "Login incorrecto", null);
        }
    }
}

