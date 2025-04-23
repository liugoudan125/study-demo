package com.beiming.securitydemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * OAuth2Controller
 */
@RestController
@RequestMapping("oauth")
public class OAuth2Controller {


    private static final Logger log = LoggerFactory.getLogger(OAuth2Controller.class);

    @RequestMapping("callback/{registrationId}")
    public void callbackGitee(@PathVariable String registrationId, Principal principal) {
        log.info("授权码是: {}", principal);
    }
}
