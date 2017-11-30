package com.ls.simple;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

@Controller
public class LoginController {

	private static final transient Logger log = LoggerFactory
			.getLogger(LoginController.class);

	@RequestMapping(value = "/login")
	public String login() {
		Subject currentUser = SecurityUtils.getSubject();

		if (!currentUser.isAuthenticated()) {
			UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa1111");
			token.setRememberMe(true);
			try {
				currentUser.login(token);
				return "success";
			} catch (UnknownAccountException uae) {
				log.error("There is no user with username of " + token.getPrincipal());
			} catch (IncorrectCredentialsException ice) {
				log.error("Password for account " + token.getPrincipal() + " was incorrect!");
			} catch (LockedAccountException lae) {
				log.error("The account for username " + token.getPrincipal() + " is locked. Please contact your administrator to unlock it.");
			}
			// ... catch more exceptions here (maybe custom ones specific to
			// your application?
			catch (AuthenticationException ae) {
				// unexpected condition? error?
			}
		}
		return "failure";

	}

	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request, HttpSession session) {
		SecurityUtils.getSubject().logout();
		try {
			System.out.println("---------------- session id 1 ----------------" + request.getSession().getId());
		} catch (final Exception e) {
			System.out.println("123 Error invalidating session." + e.getMessage());
		}
		System.out.println("---------------- session id 3 ----------------" + session.getId());

		try {
			session.invalidate();
		} catch (final Exception e) {
			log.debug("Error invalidating session.", e);
		}
		return "redirect:http://ssouat.oppein.com/cas/logout?service=http://localhost:8080/admin/test";

	}
}
