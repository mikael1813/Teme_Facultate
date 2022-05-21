package start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import rest.ChatUserController;
import rest.PropuneriController;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by grigo on 5/10/17.
 */
@ComponentScan("rest")
@ComponentScan("repos")
@SpringBootApplication
public class StartRestServices {
    public static void main(String[] args) {

        SpringApplication.run(StartRestServices.class, args);
    }
    @Primary
    @Bean(name="properties")
    public Properties getBdProperties(){
        Properties serverProps=new Properties();
        try {
            serverProps.load(ChatUserController.class.getResourceAsStream("/database.properties"));
            //serverProps.load(PropuneriController.class.getResourceAsStream("/database.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatserver.properties "+e);
            return null;
        }
        return serverProps;
    }
}

