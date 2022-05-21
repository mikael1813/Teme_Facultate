package app.client;


import app.client.gui.ListeProbeController;
import app.client.gui.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import protobuffprotocol.ProtoChatProxy;
import services.IService;

import java.io.IOException;
import java.util.Properties;

public class StartProtobuffClient extends Application {
    private static int defaultChatPort=55555;
    private static String defaultServer="localhost";

    public static void main(String[] args) {

       launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Properties clientProps=new Properties();
        try {
            clientProps.load(StartRpcClientFX.class.getResourceAsStream("/chatclient.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatclient.properties "+e);
            return;
        }
        String serverIP=clientProps.getProperty("chat.server.host",defaultServer);
        int serverPort=defaultChatPort;
        try{
            serverPort=Integer.parseInt(clientProps.getProperty("chat.server.port"));
        }catch(NumberFormatException ex){
            System.err.println("Wrong port number "+ex.getMessage());
            System.out.println("Using default port: "+defaultChatPort);
        }
        System.out.println("Using server IP "+serverIP);
        System.out.println("Using server port "+serverPort);

        IService server=new ProtoChatProxy(serverIP, serverPort);

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/views/Login.fxml"));
        Parent root=loader.load();


        LoginController ctrl =
                loader.<LoginController>getController();
        ctrl.setService(server);




        FXMLLoader cloader = new FXMLLoader(
                getClass().getResource("/views/ListaProbe.fxml"));
        Parent croot=cloader.load();


        ListeProbeController chatCtrl =
                cloader.<ListeProbeController>getController();
        chatCtrl.setServer(server);

        ctrl.setListeProbeController(chatCtrl);
        ctrl.setParent(croot);

        primaryStage.setTitle("MPP chat");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
