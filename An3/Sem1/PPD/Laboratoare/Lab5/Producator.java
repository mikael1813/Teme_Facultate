import java.io.*;

public class Producator extends Thread {

    Queue queue;
    String string;
    int start, end, nr, producatori;

    public Producator(Queue queue, String string, int start, int end, int nr, int producatori) {
        this.queue = queue;
        this.string = string;
        this.start = start;
        this.end = end;
        this.nr = nr;
        this.producatori = producatori;
    }


    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            //System.out.println(i);
            String filename = string + i + ".txt";
            try {
                //FileReader fileReader = new FileReader(filename);
                FileInputStream fileInputStream = new FileInputStream(filename);
                BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));
                String strLine;
                //System.out.println(filename);
                while ((strLine = br.readLine()) != null) {
                    String[] result = strLine.split(",");
                    Monom m = new Monom(Integer.parseInt(result[0]), Integer.parseInt(result[1]));
                    //System.out.println(m);
                    queue.put(new Node(m));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //System.out.println("Gata" + start + " " + end);
        queue.setCount(queue.getCount() + 1);
        if (queue.getCount() == producatori) {
            queue.finished = true;
            for (int i = 0; i < nr; i++) {
                queue.put(new Node(new Monom(-1, -1)));
            }
        }

        //queue.finished = true;
    }
}
