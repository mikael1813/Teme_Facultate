package socialnetwork;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import socialnetwork.controller.StartController;
import socialnetwork.domain.*;
import socialnetwork.domain.validators.MesajValidator;
import socialnetwork.domain.validators.UtilizatorValidator;
import socialnetwork.repository.Repository;
import socialnetwork.repository.file.MesajFile;
import socialnetwork.repository.file.UtilizatorFile;
import socialnetwork.service.*;

import java.io.IOException;
import java.time.LocalDateTime;

public class MainApp extends Application {
    SuperService superService;


    public static void main(String[] args) {
        launch(args);
    }

    public void multipleWindows(Utilizator user)
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/Start.fxml"));

        AnchorPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setTitle("Message");
        stage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(root);
        stage.setScene(scene);


        StartController startController = loader.getController();
        startController.setUser(user);
        startController.setService(superService);

        stage.show();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        loadService();
//        initView(primaryStage);
//        primaryStage.setWidth(800);
//        primaryStage.show();

        superService.getUsers().forEach(x->{
            multipleWindows(x);
        });
    }

    private void initView(Stage primaryStage) throws IOException {
        FXMLLoader messageLoader = new FXMLLoader();
        messageLoader.setLocation(getClass().getResource("/views/Start.fxml"));
        AnchorPane messageTaskLayout = messageLoader.load();
        primaryStage.setScene(new Scene(messageTaskLayout));

        StartController startController = messageLoader.getController();
        startController.setService(superService);
    }

    private void loadService(){
        //String fileName=ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.users");
        String fileName = "data/Utilizatori.csv";
        Repository<Long, Utilizator> userFileRepository = new UtilizatorFile(fileName
                , new UtilizatorValidator());

        userFileRepository.findAll().forEach(System.out::println);

        String fileName3 = "data/Mesaje.csv";
        Repository<Long, Mesaj> mesajFileRepository = new MesajFile(fileName3
                , new MesajValidator());

        //mesajFileRepository.findAll().forEach(System.out::println);

        UtilizatorService serviceU = new UtilizatorService(userFileRepository);
        MesajService serviceM = new MesajService(mesajFileRepository);

        superService = new SuperService(serviceU, serviceM);

    }
}
