package io.baselogic.springsecurity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

/**
 * Main Spring boot Application class
 *
 * @author mickknutson
 * @since chapter01.00 Created
*/
@SpringBootApplication
@Slf4j
public class Application {

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }


    /**
     * This simple utility will print out to the console, the beans that have bee loaded into the current context
     * @param ctx Application Context
     * @return CommandLineRunner
     */
    @Profile("trace")
    @Bean
    @Autowired
    public CommandLineRunner viewBeansInContext(final ApplicationContext ctx) {
        return args -> {

            StringBuilder sb = new StringBuilder(1_000);
            sb.append("\n").append(LINE);
            sb.append("Let's inspect the beans provided by Spring Boot:");
            sb.append("\n").append(LINE);

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                sb.append(beanName);
            }

            sb.append("\n").append(LINE).append("\n");

            log.debug(sb.toString());
        };
    }

    public static final String LINE = "------------------------------------------------";


} // The End...
