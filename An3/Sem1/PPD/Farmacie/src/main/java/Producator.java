import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Producator extends Thread {

    Queue queue;
    String string;
    int start, end, consumatori, producatori, id;

    public Producator(Queue queue, String string, int start, int end, int consumatori, int producatori, int id) {
        this.queue = queue;
        this.string = string;
        this.start = start;
        this.end = end;
        this.consumatori = consumatori;
        this.producatori = producatori;
        this.id = id;
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

        String filename = string + ".txt";
        List<String> result = new ArrayList<>();
        try {
            //FileReader fileReader = new FileReader(filename);
            FileInputStream fileInputStream = new FileInputStream(filename);
            BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));
            String strLine;

            //System.out.println(filename);
            while ((strLine = br.readLine()) != null) {
                result.add(strLine.replace(" ", ""));
                //System.out.println(m);
                //queue.put(new Node(m));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (int i = start; i < end; i++) {
            //System.out.println(i);


            Random rand = new Random();
            int nr_medicamente = rand.nextInt(5) + 1;
            List<Medicament> list = new ArrayList<>();
            for (int j = 0; j < nr_medicamente; j++) {
                int rand_cod_medicament = rand.nextInt(10);
                int pret = Integer.parseInt(result.get(rand_cod_medicament).split(";")[1]);
                //String x = result.get(rand_cod_medicament - 1).split(" ");
                //System.out.println(result);
                list.add(new Medicament(rand_cod_medicament, pret));
            }
            Reteta reteta = new Reteta(i, nr_medicamente, list);

            queue.put(new Node(reteta));


        }

        //System.out.println("Gata" + start + " " + end);
        queue.setCount(queue.getCount() + 1);
        if (queue.getCount() == producatori) {
            //queue.finished = true;
            for (int i = 0; i < consumatori; i++) {
                queue.put(new Node(new Reteta(-1, -1, null)));
            }
        }

        //queue.finished = true;
    }
}
