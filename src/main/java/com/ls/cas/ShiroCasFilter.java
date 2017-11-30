package com.ls.cas;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.cas.CasFilter;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class ShiroCasFilter extends CasFilter {
	
	private static Logger logger = LoggerFactory.getLogger(ShiroCasFilter.class);
	
	private String casServerUrlPrefix;
	
	private String clientServerUrlPrefix;
	
	// the url where the application is redirected if the CAS service ticket validation failed (example : /mycontextpatch/cas_error.jsp)
    private String failureUrl;
	
	/**
     * If login has failed, redirect user to the CAS error page (no ticket or ticket validation failed) except if the user is already
     * authenticated, in which case redirect to the default success url.
     * 
     * @param token the token representing the current authentication
     * @param ae the current authentication exception
     * @param request the incoming request
     * @param response the outgoing response
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException ae, ServletRequest request,
                                     ServletResponse response) {
        // is user authenticated or in remember me mode ?
        Subject subject = getSubject(request, response);
        if (subject.isAuthenticated() || subject.isRemembered()) {
            try {
                issueSuccessRedirect(request, response);
            } catch (Exception e) {
                logger.error("Cannot redirect to the default success url", e);
            }
        } else {
//            WebUtils.issueRedirect(request, resp, failureUrl);
            try {
            	HttpServletResponse resp = (HttpServletResponse) response;
            	resp.setStatus(HttpStatus.FORBIDDEN.value());
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<html>");
				out.println("<body>"+ ae.getMessage() +" 或者重新<a href='"+ casServerUrlPrefix +"/logout?service="+ clientServerUrlPrefix +"/logout'>登录</a></body>");
				out.println("</html>");
				out.flush();
				out.close();
            } catch (IOException e) {
                logger.error("Cannot output error message: {}", failureUrl, e);
            }
        }
        return false;
    }
    
    public void setFailureUrl(String failureUrl) {
        this.failureUrl = failureUrl;
    }

	public void setCasServerUrlPrefix(String casServerUrlPrefix) {
		this.casServerUrlPrefix = casServerUrlPrefix;
	}

	public void setClientServerUrlPrefix(String clientServerUrlPrefix) {
		this.clientServerUrlPrefix = clientServerUrlPrefix;
	}

}
