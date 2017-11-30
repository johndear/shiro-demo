package com.ls.cas;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.cas.CasFilter;
import org.apache.shiro.cas.CasSubjectFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.DelegatingFilterProxy;

@Configuration
public class ShiroCasConfiguration {
	
	@Bean(name="myRealm")
	@Order(1)
	public AuthorizingRealm getRealm(){
		ShiroCasRealm dm = new ShiroCasRealm();
		dm.setCasServerUrlPrefix("https://ssouat.oppein.com/cas");
		dm.setCasService("http://ls.oppein.com:8080/shiro-cas");
		return dm;
	}
	
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
		filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
		// 该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
		filterRegistration.addInitParameter("targetFilterLifecycle", "true");
		filterRegistration.setEnabled(true);
		filterRegistration.addUrlPatterns("/*");
		return filterRegistration;
	}

	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}
	
	@Bean(name="myDefaultSecurityManager")
	@Order(2)
	public DefaultSecurityManager getDefaultSecurityManager(@Qualifier("myRealm") AuthorizingRealm myRealm){
		DefaultSecurityManager dm = new DefaultWebSecurityManager();
		dm.setRealm(myRealm);
		dm.setSubjectFactory(new CasSubjectFactory());
		return dm;
	}
	
//	@Bean
//	public MethodInvokingFactoryBean getMethodInvokingFactoryBean(@Qualifier("myDefaultSecurityManager") DefaultSecurityManager myDefaultSecurityManager){
//		MethodInvokingFactoryBean mifb = new MethodInvokingFactoryBean();
//		mifb.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
//		mifb.setArguments(new Object[]{myDefaultSecurityManager});
//		return mifb;
//	}
	
	@Bean(name = "casFilter")
	@Order(4)
	public CasFilter getCasFilter() {
		ShiroCasFilter casFilter = new ShiroCasFilter();
		casFilter.setName("casFilter");
		casFilter.setEnabled(true);
		casFilter.setFailureUrl("aa");
		casFilter.setCasServerUrlPrefix("bb");
		casFilter.setClientServerUrlPrefix("cc");
		return casFilter;
	}
	
	@Bean
	public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(
			@Qualifier("myDefaultSecurityManager") DefaultWebSecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}
	
	@Bean(name = "shiroFilter")
	@Order(5)
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("myDefaultSecurityManager") DefaultSecurityManager myDefaultSecurityManager){
		String ssoServerUrl = "https://ssouat.oppein.com/cas";
//		ssoServerUrl = "http://opssotest.oppein.com/sso";
		
		ShiroFilterFactoryBean sffb = new ShiroFilterFactoryBean();
		sffb.setSecurityManager(myDefaultSecurityManager);
		sffb.setLoginUrl(ssoServerUrl + "/login?service=http://ls.oppein.com:8080/shiro-cas");
		sffb.setSuccessUrl("success.jsp");
		sffb.setUnauthorizedUrl("error.jsp");
		
		Map<String,String> filterChainDefinitionMap = new HashMap<String,String>();
		filterChainDefinitionMap.put("/shiro-cas", "casFilter");
		filterChainDefinitionMap.put("/admin/**", "authc");
		filterChainDefinitionMap.put("/**", "authc");
		sffb.setFilterChainDefinitionMap(filterChainDefinitionMap);
		
		return sffb;
	}
	
	
	
	
	
	

}
