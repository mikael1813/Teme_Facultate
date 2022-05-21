import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Casier extends Thread {
    Queue queue;
    //LinkedList linkedList;
    int numar;
    int nr_threads;

    int count = 0;
    int nr_factura = 0;

    public Casier(Queue queue, int numar, int nr_threads) {
        this.queue = queue;
        //this.linkedList = linkedList;
        this.numar = numar;
        this.nr_threads = nr_threads;
    }

    @Override
    public void run() {

        while (true) {

            Reteta reteta;

            reteta = queue.get();

            //System.out.println("acum");

            if (reteta.getId() == -1) {
                break;
            }

            if (reteta != null) {
                nr_factura += 1;
                String contentToAppend = String.valueOf(nr_factura) + " " + String.valueOf(reteta.getPret()) + " " + String.valueOf(reteta.getId()) + "\n";
                //System.out.println("Reteta nr " + reteta.getId() + " a fost platita si preluata");
                try {
                    Files.write(
                            Paths.get("facturi.txt"),
                            contentToAppend.getBytes(),
                            StandardOpenOption.APPEND);
                    System.out.println("Reteta nr " + reteta.getId() + " a fost platita si preluata");

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }

    }
}
