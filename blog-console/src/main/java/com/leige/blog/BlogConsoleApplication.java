package com.leige.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication
@EnableTransactionManagement //如果mybatis中service实现类中加入事务注解，需要此处添加该注解
@MapperScan("com.leige.blog.mapper")//扫描的是mapper.xml中namespace指向值的包位置
public class BlogConsoleApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(BlogConsoleApplication.class, args);
	}

	@Bean
	public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
		ServletRegistrationBean reg = new ServletRegistrationBean(dispatcherServlet);
		reg.getUrlMappings().clear();
		reg.addUrlMappings("/");
		reg.addUrlMappings("*.shtml");
		reg.addUrlMappings("*.html");
		reg.addUrlMappings("*.css");
		reg.addUrlMappings("*.js");
		reg.addUrlMappings("*.png");
		reg.addUrlMappings("*.jpg");
		reg.addUrlMappings("*.gif");
		reg.addUrlMappings("*.ico");
		reg.addUrlMappings("*.ttf");
		reg.addUrlMappings("*.woff");
		reg.addUrlMappings("*.woff2");
		reg.addUrlMappings("*.swf");
		return reg;
	}

	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {

		return new EmbeddedServletContainerCustomizer() {
			@Override
			public void customize(ConfigurableEmbeddedServletContainer container) {
				ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/error.shtml");
				container.addErrorPages(error404Page);
				ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error.shtml");
				container.addErrorPages(error500Page);
			}
		};
	}
}
