package rest;

import domain.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import repos.RoundRepository;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

@CrossOrigin
@RestController
@RequestMapping("/app/propuneri")
public class PropuneriController {

    @Autowired
    private RoundRepository roundRepository;

    public PropuneriController(){
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

    @RequestMapping(value = "/{id}/{id2}", method = RequestMethod.GET)
    public String[] getById(@PathVariable String id,@PathVariable String id2) {

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
        s[0] = "Runda1: ";
        s[1] = "Runda2: ";
        s[2] = "Runda3: ";
        for (int i=0;i<3;i++){
            if(r[i]!= null){
                if(Integer.parseInt(id2) == 1){
                    s[i] = s[i] + r[i].getPropunere1() + " " + r[i].getPuncte1();
                }
                if(Integer.parseInt(id2) == 2){
                    s[i] = s[i] + r[i].getPropunere2() + " " + r[i].getPuncte2();
                }
                if(Integer.parseInt(id2) == 3){
                    s[i] = s[i] + r[i].getPropunere3() + " " + r[i].getPuncte3();
                }
            }
        }
        return s;
    }
}
