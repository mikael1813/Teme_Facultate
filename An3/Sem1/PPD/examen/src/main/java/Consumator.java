import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Consumator extends Thread {
    Queue queue;
    Dictionar dictionar;
    //LinkedList linkedList;
    int numar;
    int nr_threads;
    int nr_obiecte;

    int count = 0;

    public Consumator(Queue queue, Dictionar dictionar, int numar, int nr_threads, int nr_obiecte) {
        this.queue = queue;
        this.dictionar = dictionar;
        //this.linkedList = linkedList;
        this.numar = numar;
        this.nr_threads = nr_threads;
        this.nr_obiecte = nr_obiecte;
    }

    @Override
    public void run() {

        while (true) {

            try {
                TimeUnit.MILLISECONDS.sleep(8);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<Obiect> list;


            if (queue.getCount() >= 5 && queue.get_nr() < nr_obiecte) {
                break;
            }
            System.out.println(queue.getCount());


            list = queue.get(nr_obiecte);
            dictionar.add(numar, new Valoare(Operatie.preluare, queue.get_nr(), LocalDateTime.now()));
            System.out.println();

        }

        queue.setCount(queue.getCount() + 1);
//        if (queue.getCount() == nr_threads) {
//            queue.put(new Node(new Valoare(-1, -1, null)));
//        }

    }
}
