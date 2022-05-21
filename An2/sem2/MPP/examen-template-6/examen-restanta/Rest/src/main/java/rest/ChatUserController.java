package rest;



import domain.Round;
import domain.Utilizator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import repos.RepositoryException;
import repos.RoundRepository;
import repos.UtilizatorRepository;

import java.io.IOException;
import java.util.List;
import java.util.Properties;


/**
 * Created by grigo on 5/10/17.
 */
@CrossOrigin
@RestController
@RequestMapping("/app/modele")
public class ChatUserController {

    private static final String template = "Hello, %s!";

    @Autowired
    private RoundRepository roundRepository;

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

    @RequestMapping(method = RequestMethod.GET)
    public Utilizator[] getAll() {
//        List<Utilizator> list = (List<Utilizator>) roundRepository.findAll();
//        Utilizator[] p = new Utilizator[list.size()];
//        for (int i = 0; i < list.size(); i++) {
//            p[i] = list.get(i);
//        }
//        return p;
        return null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String[] getById(@PathVariable String id) {

        List<Round> list = (List<Round>) roundRepository.findAll();
        Round[] r = new Round[3];
        int k=0;
        for (int i=0;i<list.size();i++){
            if(list.get(i).getGame() == Integer.parseInt(id)){
                r[k]=list.get(i);
                k++;
            }
        }
        String[] s = new String[3];
        s[0] = "Jucatorul1: ";
        s[1] = "Jucatorul2: ";
        s[2] = "Jucatorul3: ";
        for (int i=0;i<3;i++){
            if(r[i]!= null){
                s[0] = s[0] + r[i].getModel1() + " ";
                s[1] = s[1] + r[i].getModel2()+ " ";
                s[2] = s[2] + r[i].getModel3()+ " ";
            }
        }
        return s;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Utilizator create(@RequestBody Utilizator user) {
        //utilizatorRepository.save(user);
        return user;

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Utilizator update(@RequestBody Utilizator user) {
        System.out.println("Updating proba ...");
        //cardRepositoy.update(user.getId(), user);
        return user;

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable String id) {
        System.out.println("Deleting proba ... " + id);
        try {
           // utilizatorRepository.delete(Integer.parseInt(id));
            return new ResponseEntity<Utilizator>(HttpStatus.OK);
        } catch (RepositoryException ex) {
            System.out.println("Ctrl Delete user exception");
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


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
