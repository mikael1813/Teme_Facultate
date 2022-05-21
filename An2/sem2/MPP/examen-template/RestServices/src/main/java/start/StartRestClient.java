package start;


import client.ProbaClient;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import rest.ServiceException;


/**
 * Created by grigo on 5/11/17.
 */
public class StartRestClient {
    private final static ProbaClient usersClient = new ProbaClient();

    public static void main(String[] args) {
//        RestTemplate restTemplate = new RestTemplate();
//        Proba probaT = new Proba(3,100, Stil.FLUTURE);
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
//                Proba[] res = usersClient.getAll();
//                for (Proba p : res) {
//                    System.out.println(p.getId() + ": " + p.getDistanta() + "," + p.getStil().toString());
//                }
//            });
//            show(()-> usersClient.delete(probaT.getId().toString()));
//            show(() -> {
//                Proba[] res = usersClient.getAll();
//                for (Proba p : res) {
//                    System.out.println(p.getId() + ": " + p.getDistanta() + "," + p.getStil().toString());
//                }
            //});
//        } catch (RestClientException ex) {
//            System.out.println("Exception ... " + ex.getMessage());
//        }
//
//    }


//    private static void show(Runnable task) {
//        try {
//            task.run();
//        } catch (ServiceException e) {
//            //  LOG.error("Service exception", e);
//            System.out.println("Service exception" + e);
//        }
//    }
}
