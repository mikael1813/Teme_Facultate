//package rest;
//
//import com.example.mdbspringboot.domain.Penguin;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import SQL.Repository;
//
//import java.util.List;
//
//
//
//public class Controller {
//
//    //@Autowired
//    private Repository repo;
//
//    public Controller() {
//        System.out.println("dada");
//        System.out.println("nono");
//    }
//
//    @RequestMapping(method = RequestMethod.GET)
//    public Penguin[] getUsers() {
//        List<Penguin> list = (List<Penguin>) repo.getUsers();
//        Penguin[] u = new Penguin[list.size()];
//        for (int i = 0; i < list.size(); i++) {
//            u[i] = list.get(i);
//        }
////        LocalDate date = LocalDate.now();
////        User u = new domain.User("usernameee", "password", "Ion", "Ion", date, "077777777");
//        return u;
//    }
//}
