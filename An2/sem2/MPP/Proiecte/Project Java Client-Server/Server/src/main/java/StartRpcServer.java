

import app.persistance.repository.InscriereRepository;
import app.persistance.repository.ParticipantRepository;
import app.persistance.repository.ProbaRepository;
import app.persistance.repository.UtilizatorRepository;
import app.persistance.repository.databases.InscriereDBRepository;
import app.persistance.repository.databases.ParticipantDBRepository;
import app.persistance.repository.databases.ProbaDBRepository;
import app.persistance.repository.databases.UtilizatorDBRepository;
import app.server.Service;
import app.server.ServiceWithHibernate;
import hibernate.ManageUtilizator;
import services.IService;
import utils.AbstractServer;
import utils.ChatRpcConcurrentServer;
import utils.ServerException;

import java.io.IOException;
import java.util.Properties;

public class StartRpcServer {
    private static int defaultPort=55555;
    public static void main(String[] args) {
        // UserRepository userRepo=new UserRepositoryMock();
        Properties serverProps=new Properties();
        try {
            serverProps.load(StartRpcServer.class.getResourceAsStream("/chatserver.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatserver.properties "+e);
            return;
        }
        UtilizatorRepository utilizatorRepository = new UtilizatorDBRepository(serverProps);
        ManageUtilizator manageUtilizator = new ManageUtilizator();
        manageUtilizator.initialize();
        ParticipantRepository participantRepository = new ParticipantDBRepository(serverProps);
        ProbaRepository probaRepository = new ProbaDBRepository(serverProps);
        InscriereRepository inscriereRepository = new InscriereDBRepository(serverProps,probaRepository,participantRepository);

        Service service = new Service(inscriereRepository, participantRepository, probaRepository, utilizatorRepository);
        ServiceWithHibernate service2 = new ServiceWithHibernate(inscriereRepository, participantRepository, probaRepository, manageUtilizator);

        System.out.println(utilizatorRepository.findAll());

        int chatServerPort=defaultPort;
        try {
            chatServerPort = Integer.parseInt(serverProps.getProperty("chat.server.port"));
        }catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number"+nef.getMessage());
            System.err.println("Using default port "+defaultPort);
        }
        System.out.println("Starting server on port: "+chatServerPort);
        AbstractServer server = new ChatRpcConcurrentServer(chatServerPort, service2);
        try {
            server.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        }finally {
            try {
                server.stop();
            }catch(ServerException e){
                System.err.println("Error stopping server "+e.getMessage());
            }
        }
    }
}
