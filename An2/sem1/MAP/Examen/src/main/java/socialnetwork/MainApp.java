package socialnetwork;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import socialnetwork.controller.StartController;
import socialnetwork.domain.*;
import socialnetwork.domain.validators.HotelValidator;
import socialnetwork.domain.validators.LocationValidator;
import socialnetwork.domain.validators.SpecialValidator;
import socialnetwork.domain.validators.UtilizatorValidator;
import socialnetwork.repository.Repository;
import socialnetwork.repository.file.HotelFile;
import socialnetwork.repository.file.LocationFile;
import socialnetwork.repository.file.SpecialOfferFile;
import socialnetwork.repository.file.UtilizatorFile;
import socialnetwork.service.*;

import java.io.IOException;

public class MainApp extends Application {
    SuperService superService;


    public static void main(String[] args) {
        launch(args);
    }

    public void multipleWindows()
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/Start.fxml"));
       // loader.setRoot(this);
        AnchorPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        //stage.setTitle("Message");
        //stage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(root);
        stage.setScene(scene);


        StartController startController = loader.getController();
        //startController.setUser(user);
        startController.setService(superService);

        stage.show();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        loadService();
        //multipleWindows();
        initView(primaryStage);
        primaryStage.setWidth(800);
        primaryStage.show();


    }

    private void initView(Stage primaryStage) throws IOException {
        System.out.println("ceva");
        FXMLLoader messageLoader = new FXMLLoader();
        messageLoader.setLocation(getClass().getResource("/views/Start.fxml"));
        AnchorPane messageTaskLayout = (AnchorPane) messageLoader.load();
        primaryStage.setScene(new Scene(messageTaskLayout));
        System.out.println("ceva");

        StartController startController = messageLoader.getController();
        startController.setService(superService);
    }

    private void loadService(){
        //String fileName=ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.users");
        String fileName = "data/locatii.csv";
        Repository<Double, Location> locationFileRepository = new LocationFile(fileName
                , new LocationValidator());

        //userFileRepository.findAll().forEach(System.out::println);
        locationFileRepository.findAll().forEach(x->{
            System.out.println(x.getLocationName());
        });

        String fileName2 = "data/hoteluri.csv";
        Repository<Double, Hotel> hotelFileRepository = new HotelFile(fileName2
                , new HotelValidator());


        hotelFileRepository.findAll().forEach(x->{
            System.out.println(x.getHotelName());
        });

        String filename3 = "data/offers.csv";
        Repository<Double, SpecialOffer> specialOfferFileRepository = new SpecialOfferFile(filename3
                , new SpecialValidator());

        //UtilizatorService serviceU = new UtilizatorService(userFileRepository);
        LocationService locationService = new LocationService(locationFileRepository);
        HotelService hotelService = new HotelService(hotelFileRepository);
        SpecialOfferService specialOfferService = new SpecialOfferService(specialOfferFileRepository);


        superService = new SuperService(locationService,hotelService,specialOfferService);

    }
}
