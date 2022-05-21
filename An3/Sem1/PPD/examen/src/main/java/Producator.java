import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Producator extends Thread {

    Queue queue;
    String string;
    int consumatori, producatori, id;
    int nr_obiecte;
    Dictionar dictionar;

    public Producator(Queue queue, String string, int consumatori, int producatori, int id, int nr_obiecte, Dictionar dictionar) {
        this.queue = queue;
        this.string = string;

        this.consumatori = consumatori;
        this.producatori = producatori;
        this.id = id;
        this.nr_obiecte = nr_obiecte;
        this.dictionar = dictionar;
    }


    public int getIdd() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        //System.out.println("Prrrroducator   " + id);

        List<Node> list = new ArrayList<>();

        for (int i = 0; i < 50; i++) {

            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int j = 0; j < nr_obiecte; j++) {
                Random rand = new Random();
                int id_obiect = rand.nextInt(1000);
                int pret_obiect = rand.nextInt(2000);
                list.add(new Node(new Obiect(id_obiect, pret_obiect)));
            }

            queue.put(list, nr_obiecte);
            dictionar.add(id, new Valoare(Operatie.depunere, queue.get_nr(), LocalDateTime.now()));
        }


        System.out.println("Done");
        //System.out.println("Gata" + start + " " + end);
        queue.setCount(queue.getCount() + 1);

//        if (queue.getCount() == producatori) {
//            //queue.finished = true;
//            for (int i = 0; i < consumatori; i++) {
//                queue.put(new Node(new Valoare(-1, -1, null)));
//            }
//        }

        //queue.finished = true;
    }
}
