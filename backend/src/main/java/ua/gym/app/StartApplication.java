package ua.gym.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan({"ua.gym"})
@EnableJpaRepositories({"ua.gym.domain"})
@EntityScan({"ua.gym.domain"})
@SpringBootApplication
public class StartApplication implements CommandLineRunner {
   public static void main(String[] args) {
      (new SpringApplicationBuilder(new Class[]{StartApplication.class})).web(WebApplicationType.SERVLET).run(args);
   }

   public void run(String... args) {
   }
}
