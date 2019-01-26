package com.teme.spring.controller;

import com.teme.spring.entities.AppUser;
import com.teme.spring.repository.UserRepository;
import com.teme.spring.service.FacebookService;
import com.teme.spring.service.GoogleService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private final UserRepository userRepository;
    private final GoogleService googleService;
    private final FacebookService facebookService;

    @Autowired
    public RestController(UserRepository userRepository, GoogleService googleService, FacebookService facebookService) {
        this.userRepository = userRepository;
        this.googleService = googleService;
        this.facebookService = facebookService;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<AppUser> findAllUser() {
        return this.userRepository.findAll();
    }

    @RequestMapping(value = "/insertUser", method = RequestMethod.POST)
    public AppUser insertUser(@Valid @RequestBody AppUser appUser) {
        appUser.setId(ObjectId.get());
        userRepository.save(appUser);
        return appUser;
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable ObjectId id) {
        userRepository.delete(userRepository.findById(id));
    }

    @RequestMapping(value = "/users/id/{id}", method = RequestMethod.GET)
    public AppUser findUserById(@PathVariable("id") ObjectId id) {
        return this.userRepository.findById(id);
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String error() {
        return "403";
    }

    @RequestMapping(value = "/logoutSuccess", method = RequestMethod.GET)
    public String logout() {
        return "Logged out";
    }
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public AppUser home(HttpServletRequest request){
        String userId = request.getUserPrincipal().getName();
        return  userRepository.findByIdUser(userId);
    }
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(){
        return "you are admin";
    }
}
