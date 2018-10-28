package com.cloudproject.clouddrive;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class MvcConfig {

	  @Autowired
	  ApplicationContext applicationContext;
	  
	  
	@Bean
  public ClassLoaderTemplateResolver templateResover() {
      ClassLoaderTemplateResolver configurer = new ClassLoaderTemplateResolver();
      configurer.setPrefix("/");
      configurer.setSuffix(".html");
      configurer.setTemplateMode(TemplateMode.HTML);
      configurer.setCharacterEncoding("UTF-8");
//      configurer.setOrder(0);  // this is important. This way spring //boot will listen to both places 0 and 1
      configurer.setCheckExistence(true);
      
      return configurer;
  }
  @Bean
  @Description("Thymeleaf template engine with Spring integration")
  public SpringTemplateEngine templateEngine() {
      
      SpringTemplateEngine templateEngine = new SpringTemplateEngine();
      templateEngine.setTemplateResolver(templateResover());

      return templateEngine;
  }

  @Bean
  @Description("Thymeleaf view resolver")
  public ViewResolver viewResolver() {

      ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
      
      viewResolver.setTemplateEngine(templateEngine());
      viewResolver.setCharacterEncoding("UTF-8");

      return viewResolver;
  }    

  public void addViewControllers(ViewControllerRegistry registry) {
      registry.addViewController("/").setViewName("index");
  }
}

