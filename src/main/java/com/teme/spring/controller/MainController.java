package com.teme.spring.controller;

import com.teme.spring.entities.AppUser;
import com.teme.spring.entities.GooglePojo;
import com.teme.spring.repository.UserRepository;
import com.teme.spring.service.EmailService;
import com.teme.spring.service.FacebookService;
import com.teme.spring.service.GoogleService;
import com.teme.spring.util.SocialUtil;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class MainController {

    private final GoogleService googleService;
    private final FacebookService facebookService;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Autowired
    public MainController(GoogleService googleService, FacebookService facebookService, UserRepository userRepository, EmailService emailService) {
        this.googleService = googleService;
        this.facebookService = facebookService;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    //    @RequestMapping("/home")
//    public String home(){
//        return "home";
//    }
    @RequestMapping("/login-google")
    public String requestGoogle(HttpServletRequest request, HttpServletResponse response) {
        String url = googleService.createGoogleAuthorizationURL();
        return "redirect:" + url;
    }

    @RequestMapping("/login-facebook")
    public String requestFacebook() {
        return "redirect:" + facebookService.createFacebookAuthorizationURL();
    }

    @RequestMapping("/google")
    public String getGoogleUser(@RequestParam(name = "code") String code, HttpServletRequest request) {
        googleService.createGoogleAccessToken(code);
        GooglePojo googlePojo = googleService.getGooglePojo();
        if (!userRepository.existsUserByIdUser(googlePojo.getId())) {
            AppUser appUser = new AppUser(ObjectId.get(), googlePojo.getId(), googlePojo.getEmail(), googlePojo.getName(), googlePojo.getPicture(), "ROLE_USER");
            userRepository.save(appUser);
            emailService.sendGreetingEmail(appUser.getName(), appUser.getEmail());
        }
        return loginSocial(request, googlePojo.getId(), googleService.buildAdmin(googlePojo), googleService.buildUser(googlePojo));
    }

    @RequestMapping("/facebook")
    public String getFacebookUser(@RequestParam("code") String code, HttpServletRequest request) {
        facebookService.createFacebookAccessToken(code);
        User user = facebookService.getUser();
        if (!userRepository.existsUserByIdUser(user.getId())) {
            AppUser appUser = new AppUser(ObjectId.get(), user.getId(), user.getEmail(), user.getName(), "http://graph.facebook.com/" + user.getId() + "/picture?type=square", "ROLE_USER");
            userRepository.save(appUser);
            emailService.sendGreetingEmail(appUser.getName(), appUser.getEmail());
        }
        return loginSocial(request, user.getId(), facebookService.buildAdmin(user), facebookService.buildUser(user));
    }

    private String loginSocial(HttpServletRequest request, String id, UserDetails admin, UserDetails user) {
        AppUser appUser = userRepository.findByIdUser(id);
        if (appUser.getRole().equals("ROLE_ADMIN")){
            new SocialUtil().login(admin, request);
        }
        else {
            new SocialUtil().login(user, request);
        }

        return "redirect:/home";
    }

}
