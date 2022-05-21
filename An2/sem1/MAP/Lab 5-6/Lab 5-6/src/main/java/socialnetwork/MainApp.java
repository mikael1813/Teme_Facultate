package socialnetwork;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import socialnetwork.controller.StartController;
import socialnetwork.domain.*;
import socialnetwork.domain.validators.FriendRequestValidator;
import socialnetwork.domain.validators.MessageValidator;
import socialnetwork.domain.validators.PrietenieValidator;
import socialnetwork.domain.validators.UtilizatorValidator;
import socialnetwork.repository.Repository;
import socialnetwork.repository.file.FriendRequestFile;
import socialnetwork.repository.file.MessageFile;
import socialnetwork.repository.file.PrietenieFile;
import socialnetwork.repository.file.UtilizatorFile;
import socialnetwork.service.*;

import java.io.IOException;
import java.time.LocalDateTime;

public class MainApp extends Application {
    SuperService superService;


    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        loadService();

        initView(primaryStage);
        primaryStage.setWidth(800);
        primaryStage.show();
    }

    private void initView(Stage primaryStage) throws IOException {
        FXMLLoader messageLoader = new FXMLLoader();
        messageLoader.setLocation(getClass().getResource("/views/Start.fxml"));
        AnchorPane messageTaskLayout = messageLoader.load();
        primaryStage.setScene(new Scene(messageTaskLayout));

        StartController startController = messageLoader.getController();
        startController.setService(superService);
        //StartController startController = new StartController(superService);
    }

    private void loadService(){
        //String fileName=ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.users");
        String fileName = "data/users.csv";
        Repository<Long, Utilizator> userFileRepository = new UtilizatorFile(fileName
                , new UtilizatorValidator());

        //userFileRepository.findAll().forEach(System.out::println);

        String fileName2 = "data/friendships.csv";
        Repository<Tuple<Long, Long>, Prietenie> friendsFileRepository = new PrietenieFile(fileName2,
                new PrietenieValidator());
        //friendsFileRepository.findAll().forEach(System.out::println);

        String fileName3 = "data/messages.csv";
        Repository<Long, MessageFromFile> messageFileRepository = new MessageFile(fileName3
                , new MessageValidator());

        String fileName4 = "data/friendrequests.csv";
        Repository<Tuple<Tuple<Long, Long>, LocalDateTime>, FriendRequest> friendRequestRepository = new FriendRequestFile(fileName4
                , new FriendRequestValidator());

        UtilizatorService serviceU = new UtilizatorService(userFileRepository);
        PrietenieService serviceP = new PrietenieService((friendsFileRepository));
        MessageService serviceM = new MessageService(messageFileRepository);
        FriendRequestService serviceF = new FriendRequestService(friendRequestRepository);

        superService = new SuperService(serviceU, serviceP, serviceM, serviceF);

    }
}
