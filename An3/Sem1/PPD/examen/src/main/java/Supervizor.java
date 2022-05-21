import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class Supervizor extends Thread {
    Queue queue;
    //LinkedList linkedList;
    int numar;
    int nr_threads;
    Dictionar dictionar;

    int count = 0;

    public Supervizor(Queue queue, int numar, int nr_threads, Dictionar dictionar) {
        this.queue = queue;
        //this.linkedList = linkedList;
        this.numar = numar;
        this.nr_threads = nr_threads;
        this.dictionar = dictionar;
    }

    @Override
    public void run() {

        while (true) {
            try {
                TimeUnit.MILLISECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(queue.getCount());
            if (queue.getCount() >= 8) {
                break;
            }

            //TreeMap<Integer, Valoare> sorted = new TreeMap<>();
            //sorted.putAll(dictionar.map);
            try {
                dictionar.write();
            } catch (IOException e) {
                e.printStackTrace();
            }

//            if (valoare != null) {
//                nr_factura += 1;
//                String contentToAppend = String.valueOf(nr_factura) + " " + String.valueOf(valoare.getPret()) + " " + String.valueOf(valoare.getId()) + "\n";
//                //System.out.println("Reteta nr " + reteta.getId() + " a fost platita si preluata");
//                try {
//                    Files.write(
//                            Paths.get("facturi.txt"),
//                            contentToAppend.getBytes(),
//                            StandardOpenOption.APPEND);
//                    System.out.println("Reteta nr " + valoare.getId() + " a fost platita si preluata");
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }

        }

    }
}
