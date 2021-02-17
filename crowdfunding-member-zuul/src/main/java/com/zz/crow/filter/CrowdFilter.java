package com.zz.crow.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.zz.crow.constant.AccessPassResource;
import com.zz.crow.constant.CrowdConstant;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class CrowdFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();

        String servletPath = request.getServletPath();

        if(AccessPassResource.PASS_RESOURCE.contains(servletPath)){
            return false;
        }

        try {
            return !AccessPassResource.judgeServletPathIsStaticResource(servletPath);
        }catch (Exception e){
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("should filter");
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        HttpSession session = request.getSession();
        Object attribute = session.getAttribute(CrowdConstant.MEMBER_SESSION_NAME);
        if(attribute == null){
            HttpServletResponse response = context.getResponse();
            session.setAttribute("message", CrowdConstant.ACCESS_FORBIDDEN_NOT_LOGIN);
            try {
                response.sendRedirect("/auth/member/login/page");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
