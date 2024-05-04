package ua.gym.app;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
   private static final String dateFormat = "yyyy-MM-dd";
   private static final String dateTimeFormat = "yyyy-MM-dd HH:mm";

  /* public void addResourceHandlers(final ResourceHandlerRegistry registry) {
      registry.addResourceHandler(new String[]{"/frontend/**", "/**"})
              .addResourceLocations(new String[]{"classpath:/resources/frontend/"});


   }*/


   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry) {

      registry.addResourceHandler(new String[]{"/frontend/**", "/**"})
              .addResourceLocations("classpath:/resources/frontend/")
              .resourceChain(true)
              .addResolver(new PathResourceResolver() {
                 @Override
                 protected Resource getResource(String resourcePath, Resource location) throws IOException {
                    Resource requestedResource = location.createRelative(resourcePath);
                    return requestedResource.exists() && requestedResource.isReadable() ? requestedResource
                            : new ClassPathResource("/resources/frontend/index.html");
                 }
              });
   }

   @Bean
   public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer() {
      return (factory) -> {
         factory.setContextPath("/gym-manager");
      };
   }

   @Bean
   public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
      return builder -> {
        /* builder.simpleDateFormat(dateTimeFormat);
         builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)));
         builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)));*/

         //builder.simpleDateFormat(DateTimeFormatter.ISO_DATE_TIME.toString());
         builder.serializers(new LocalDateSerializer(DateTimeFormatter.ISO_DATE));
         builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME));
      };
   }
}
