package by.khrapovitsky.security;

import by.khrapovitsky.model.User;
import by.khrapovitsky.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

public class CustomSuccessHandler implements AuthenticationSuccessHandler,LogoutSuccessHandler {

    @Autowired
    UserService usersService;

    @Override
    public void onAuthenticationSuccess(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        User user = usersService.read(authentication.getName());
        user.setDateOfLastUse(new Timestamp(new java.util.Date().getTime()));
        usersService.update(user);
        httpServletResponse.sendRedirect("home");
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        User user = usersService.read(authentication.getName());
        user.setDateOfLastUse(new Timestamp(new java.util.Date().getTime()));
        usersService.update(user);
        httpServletResponse.sendRedirect("signin?logout");
    }
}
