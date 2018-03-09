package com.ls.shiro.case1;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;

public class Helloworld {
	
	public static void main(String[] args) {
		//1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager  
	    Factory<org.apache.shiro.mgt.SecurityManager> factory =  
	            new IniSecurityManagerFactory("classpath:com/ls/shiro/case1/shiro.ini");  
	    //2、得到SecurityManager实例 并绑定给SecurityUtils  
	    org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();  
	    SecurityUtils.setSecurityManager(securityManager);  
	    
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("root", "secret");
		
		try {  
	        //4、登录，即身份验证  
	        subject.login(token);  
	    } catch (AuthenticationException e) {  
	        //5、身份验证失败  
	    }  
	  
	    Assert.assertEquals(true, subject.isAuthenticated()); //断言用户已经登录  
	  
	    System.out.println(subject.getPrincipal());
	    
	    //6、退出  
	    subject.logout();  
	    
	    System.out.println(subject.getPrincipal());
	    
	}

}
