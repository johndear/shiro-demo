package com.ls.simple;
//package com.ls.spring;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.shiro.mgt.DefaultSecurityManager;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.realm.text.IniRealm;
//import org.apache.shiro.spring.LifecycleBeanPostProcessor;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//
//@Configuration
//public class ShiroConfiguration {
//	
//	@Bean(name="myRealm")
//	@Order(1)
//	public AuthorizingRealm getRealm(){
//		IniRealm dm = new IniRealm("classpath:shiro.ini");
//		return dm;
//	}
//	
//	@Bean(name="myDefaultSecurityManager")
//	@Order(2)
//	public DefaultSecurityManager getDefaultSecurityManager(@Qualifier("myRealm") AuthorizingRealm myRealm){
//		DefaultSecurityManager dm = new DefaultWebSecurityManager();
//		dm.setRealm(myRealm);
//		return dm;
//	}
//	
//	@Bean
//	@Order(3)
//	public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor(){
//		LifecycleBeanPostProcessor lbp = new LifecycleBeanPostProcessor();
//		return lbp;
//	}
//	
////	@Bean
////	public MethodInvokingFactoryBean getMethodInvokingFactoryBean(@Qualifier("myDefaultSecurityManager") DefaultSecurityManager myDefaultSecurityManager){
////		MethodInvokingFactoryBean mifb = new MethodInvokingFactoryBean();
////		mifb.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
////		mifb.setArguments(new Object[]{myDefaultSecurityManager});
////		return mifb;
////	}
//	
//	@Bean
//	@Order(4)
//	public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("myDefaultSecurityManager") DefaultSecurityManager myDefaultSecurityManager){
//		ShiroFilterFactoryBean sffb = new ShiroFilterFactoryBean();
//		sffb.setSecurityManager(myDefaultSecurityManager);
//		sffb.setLoginUrl("login.jsp");
//		sffb.setSuccessUrl("success.jsp");
//		sffb.setUnauthorizedUrl("error.jsp");
//		Map<String,String> patterns = new HashMap<String,String>();
//		patterns.put("/admin/**", "authc");
//		sffb.setFilterChainDefinitionMap(patterns);
//		return sffb;
//	}
//	
//	
//
//}
