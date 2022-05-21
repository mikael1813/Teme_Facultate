import java.io.*;

public class Producator extends Thread {

    Queue queue;
    String string;
    int length;

    public Producator(Queue queue, String string, int length) {
        this.queue = queue;
        this.string = string;
        this.length = length;
    }


    @Override
    public void run() {
        for (int i = 0; i < length; i++) {
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
        queue.finished=true;
    }
}
