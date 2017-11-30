package cn.ce.passport.common.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import cn.ce.passport.common.redis.RedisService;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class LoginFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    private RedisService codisClient;

    public void setCodisClient(RedisService codisClient) {
        this.codisClient = codisClient;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String ticket = null;
  
        Cookie[] cookies = httpServletRequest.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equalsIgnoreCase("COOKIE_PASSPORT_TICKET")) {
                    ticket = cookie.getValue();
                    break;
                }
            }
        }
        if (StringUtils.isEmpty(ticket)) {
            ticket = httpServletRequest.getParameter("token");
        }
//        if (!StringUtils.isEmpty(ticket)) {
//            String ticketKey = Constant.NEWSFEED_TICKET + ticket;
//            String uid = codisClient.get(ticketKey);
//            if (StringUtils.isEmpty(uid)) {
//                uid = passportService.validateTicket(ticket);
//                if (!StringUtils.isEmpty(uid)) {
//                    codisClient.set(ticketKey, uid, Constant.ticketExpire);
//                }
//            }
//            httpServletRequest.setAttribute(Constant.requestUid, uid);
//            if (!StringUtils.isEmpty(uid)) {
//                String uidKey = Constant.NEWSFEED_UID + uid;
//                String phone = codisClient.get(uidKey);
//                if (StringUtils.isEmpty(phone)) {
//                    phone = passportService.getUserinfoByUid(uid);
//                    if (!StringUtils.isEmpty(phone)) {
//                        codisClient.set(uidKey, phone, Constant.uidExpire);
//                    }
//                }
//                httpServletRequest.setAttribute(Constant.requestPhone, phone);
//            }
//        }
        chain.doFilter(httpServletRequest, httpServletResponse);
    }

    @Override
    public void destroy() {

    }
/*
    private boolean isToBeIgnore(HttpServletRequest request) {
        //获取当前路径
        *//*String uri = request.getRequestURI().split("[?]")[0];
        String contextPath = request.getContextPath();
        uri = uri.replaceFirst(contextPath, "");
        logger.info(request.getRequestURI());
        logger.info(request.getContextPath());
        logger.info(request.getServletPath());
        logger.info(uri);*//*
        String uri = request.getServletPath();
        //处理uri后面的"/"
        if (uri.endsWith("/") && uri.length() > 1) {
            uri = uri.substring(0, uri.length() - 1);
        }
        if (StringUtils.isEmpty(uri)) {
            return false;
        }
        String[] ignores = pathToBeIgnored.split(",");
        for (String ignoreRegex : ignores) {
            if (Pattern.compile(ignoreRegex).matcher(uri).matches()) {
                return true;
            }
        }
        return false;
    }*/
}
