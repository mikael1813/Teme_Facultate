package rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repos.RepositoryException;

import java.io.IOException;
import java.util.List;
import java.util.Properties;


/**
 * Created by grigo on 5/10/17.
 */
@CrossOrigin
@RestController
@RequestMapping("/app/probe")
public class ChatUserController {

    private static final String template = "Hello, %s!";

    @Autowired
    //private ProbaRepository probaRepository;

    public ChatUserController(){
        Properties serverProps=new Properties();
        try {
            serverProps.load(ChatUserController.class.getResourceAsStream("/database.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatserver.properties "+e);
            return;
        }
        //probaRepository = new ProbaDBRepository(serverProps);
    }

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format(template, name);
    }
//
//    @RequestMapping(method = RequestMethod.GET)
//    public Proba[] getAll() {
//        List<Proba> list = (List<Proba>) probaRepository.findAll();
//        Proba[] p = new Proba[list.size()];
//        for (int i = 0; i < list.size(); i++) {
//            p[i] = list.get(i);
//        }
//        return p;
//    }
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    public ResponseEntity<?> getById(@PathVariable String id) {
//
//        Proba proba = probaRepository.findOne(Integer.parseInt(id));
//        if (proba == null)
//            return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
//        else
//            return new ResponseEntity<Proba>(proba, HttpStatus.OK);
//    }
//
//    @RequestMapping(method = RequestMethod.POST)
//    public Proba create(@RequestBody Proba user) {
//        probaRepository.save(user);
//        return user;
//
//    }
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
//    public Proba update(@RequestBody Proba user) {
//        System.out.println("Updating proba ...");
//        probaRepository.update(user.getId(), user);
//        return user;
//
//    }
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//    public ResponseEntity<?> delete(@PathVariable String id) {
//        System.out.println("Deleting proba ... " + id);
//        try {
//            probaRepository.delete(Integer.parseInt(id));
//            return new ResponseEntity<Proba>(HttpStatus.OK);
//        } catch (RepositoryException ex) {
//            System.out.println("Ctrl Delete user exception");
//            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }


//    @RequestMapping("/{user}/name")
//    public String name(@PathVariable String user) {
//        User result = userRepository.findBy(user);
//        System.out.println("Result ..." + result);
//
//        return result.getName();
//    }


    @ExceptionHandler(RepositoryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String userError(RepositoryException e) {
        return e.getMessage();
    }
}
