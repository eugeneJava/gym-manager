package ua.gym.app;

import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
   private static final String dateFormat = "yyyy-MM-dd";
   private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

   public void addResourceHandlers(final ResourceHandlerRegistry registry) {
      registry.addResourceHandler(new String[]{"/clients/**", "/**"}).addResourceLocations(new String[]{"classpath:/resources/clients/"});
   }

   @Bean
   public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer() {
      return (factory) -> {
         factory.setContextPath("/gym-manager");
      };
   }
}
