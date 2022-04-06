package com.example.demo.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.firewall.FirewalledRequest;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Component;

@Component
public class MyHttpFirewall implements HttpFirewall {

    @Override
    public FirewalledRequest getFirewalledRequest(HttpServletRequest request) throws RequestRejectedException {
        return new RequestWrapper(request);
    }

    @Override
    public HttpServletResponse getFirewalledResponse(HttpServletResponse response) {
        return new ResponseWrapper(response);
    }

}
