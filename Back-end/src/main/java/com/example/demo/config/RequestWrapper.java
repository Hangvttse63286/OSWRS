//package com.example.demo.config;
//
//import java.util.Collection;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.http.HttpHeaders;
//import org.springframework.security.web.firewall.FirewalledRequest;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
///**
// * Wrapper around HttpServletRequest that overwrites Set-Cookie response header and adds SameSite=None portion.
// */
//public class RequestWrapper extends FirewalledRequest {
//
//    /**
//     * Constructs a request object wrapping the given request.
//     *
//     * @param request The request to wrap
//     * @throws IllegalArgumentException if the request is null
//     */
//    public RequestWrapper(HttpServletRequest request) {
//        super(request);
//    }
//
//    /**
//     * Must be empty by default in Spring Boot. See FirewalledRequest.
//     */
//    @Override
//    public void reset() {
//    }
//
//    @Override
//    public HttpSession getSession(boolean create) {
//        HttpSession session = super.getSession(create);
//
//        if (create) {
//            ServletRequestAttributes ra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//            if (ra != null) {
//                overwriteSetCookie(ra.getResponse());
//            }
//        }
//
//        return session;
//    }
//
//    @Override
//    public String changeSessionId() {
//        String newSessionId = super.changeSessionId();
//        ServletRequestAttributes ra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        if (ra != null) {
//            overwriteSetCookie(ra.getResponse());
//        }
//        return newSessionId;
//    }
//
//    private void overwriteSetCookie(HttpServletResponse response) {
//        if (response != null) {
//            Collection<String> headers = response.getHeaders(HttpHeaders.SET_COOKIE);
//            boolean firstHeader = true;
//            for (String header : headers) { // there can be multiple Set-Cookie attributes
//                if (firstHeader) {
//                    response.setHeader(HttpHeaders.SET_COOKIE, String.format("%s; %s", header, "SameSite=Strict")); // set
//                    firstHeader = false;
//                    continue;
//                }
//                response.addHeader(HttpHeaders.SET_COOKIE, String.format("%s; %s", header, "SameSite=Strict")); // add
//            }
//        }
//    }
//}
