//package start;
//
//
//import client.ProbaClient;
//
//import domain.Card;
//import org.springframework.web.client.RestClientException;
//import org.springframework.web.client.RestTemplate;
//import rest.ServiceException;
//
//
///**
// * Created by grigo on 5/11/17.
// */
//public class StartRestClient {
//    private final static ProbaClient usersClient = new ProbaClient();
//
//    public static void main(String[] args) {
//        RestTemplate restTemplate = new RestTemplate();
//        Card probaT = new Card("Red",100);
//        try {
//            //  User result= restTemplate.postForObject("http://localhost:8080/chat/users",userT, User.class);
//
//            //  System.out.println("Result received "+result);
//      /*  System.out.println("Updating  user ..."+userT);
//        userT.setName("New name 2");
//        restTemplate.put("http://localhost:8080/chat/users/test124", userT);
//
//*/
//            // System.out.println(restTemplate.postForObject("http://localhost:8080/chat/users",userT, User.class));
//            //System.out.println( restTemplate.postForObject("http://localhost:8080/chat/users",userT, User.class));
//
//            //show(() -> System.out.println(usersClient.create(probaT)));
//            show(() -> {
//                Card[] res = usersClient.getAll();
//                for (Card p : res) {
//                    System.out.println(p.getId() + ": " + p.getCuloare() + "," + p.getNr());
//                }
//            });
//            show(()-> usersClient.delete(probaT.getId().toString()));
//            show(() -> {
//                Card[] res = usersClient.getAll();
//                for (Card p : res) {
//                    System.out.println(p.getId() + ": " + p.getCuloare() + "," + p.getNr());
//                }
//            });
//        } catch (RestClientException ex) {
//            System.out.println("Exception ... " + ex.getMessage());
//        }
//
//    }
//
//
//    private static void show(Runnable task) {
//        try {
//            task.run();
//        } catch (ServiceException e) {
//            //  LOG.error("Service exception", e);
//            System.out.println("Service exception" + e);
//        }
//    }
//}
