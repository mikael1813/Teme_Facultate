//package client;
//
//
//import domain.Card;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.client.ResourceAccessException;
//import org.springframework.web.client.RestTemplate;
//import rest.ServiceException;
//
//import java.util.concurrent.Callable;
//
///**
// * Created by grigo on 5/11/17.
// */
//public class ProbaClient {
//    public static final String URL = "http://localhost:8080/app/probe";
//
//    private RestTemplate restTemplate = new RestTemplate();
//
//    private <T> T execute(Callable<T> callable) {
//        try {
//            return callable.call();
//        } catch (ResourceAccessException | HttpClientErrorException e) { // server down, resource exception
//            throw new ServiceException(e);
//        } catch (Exception e) {
//            throw new ServiceException(e);
//        }
//    }
//
//    public Card[] getAll() {
//        return execute(() -> restTemplate.getForObject(URL, Card[].class));
//    }
//
//    public Card getById(String id) {
//        return execute(() -> restTemplate.getForObject(String.format("%s/%s", URL, id), Card.class));
//    }
//
//    public Card create(Card proba) {
//        return execute(() -> restTemplate.postForObject(URL, proba, Card.class));
//    }
//
//    public void update(Card proba) {
//        execute(() -> {
//            restTemplate.put(String.format("%s/%s", URL, proba.getId()), proba);
//            return null;
//        });
//    }
//
//    public void delete(String id) {
//        execute(() -> {
//            restTemplate.delete(String.format("%s/%s", URL, id));
//            return null;
//        });
//    }
//
//}
