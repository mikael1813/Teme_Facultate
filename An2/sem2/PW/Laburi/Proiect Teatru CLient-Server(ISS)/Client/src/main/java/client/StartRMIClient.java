package client;



import client.gui.ClientCtrl;
import client.gui.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import services.IService;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;


public class StartRMIClient extends Application {
    private static String defaultServer="localhost";
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{


        try {
            /*String name = "Chat";
            Registry registry = LocateRegistry.getRegistry("localhost");
            IChatServices server = (IChatServices) registry.lookup(name);*/

            ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-client.xml");
            IService server=(IService)factory.getBean("chatService");
            System.out.println("Obtained a reference to remote chat server");
            ClientCtrl ctrl=new ClientCtrl(server);


            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/views/Login.fxml"));
            Parent root=loader.load();


            LoginController loginCtrl =
                    loader.<LoginController>getController();
            loginCtrl.setService(ctrl);


            primaryStage.setTitle("MPP chat");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();

        } catch (Exception e) {
            System.err.println("Chat Initialization  exception:"+e);
            e.printStackTrace();
        }

    }



}
