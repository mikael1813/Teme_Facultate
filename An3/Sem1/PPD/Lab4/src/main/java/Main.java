import java.io.*;

public class Main {

    public static int countMonomi(int length, String string) {
        int count = 0;
        for (int i = 0; i < length; i++) {
            String filename = string + i + ".txt";
            try {
                //FileReader fileReader = new FileReader(filename);
                FileInputStream fileInputStream = new FileInputStream(filename);
                BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));
                String strLine;
                //System.out.println(filename);
                while ((strLine = br.readLine()) != null) {
                    count++;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return count;
    }

    public static LinkedList sequentially(int length, String string) {
        long start = System.currentTimeMillis();
        LinkedList linkedList = new LinkedList();
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
                    linkedList.add(m);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("Timp Secvential: " + timeElapsed);
        //linkedList.show();
        return linkedList;
    }


    public static void main(String[] args) throws InterruptedException {
        int length = 10;
        String filename = "date";
        Queue queue = new Queue();
        LinkedList linkedListSequentially, linkedList = new LinkedList();

        linkedListSequentially = sequentially(length, filename);
        int nr = countMonomi(length, filename);
        //System.out.println(linkedListSequentially.equal(linkedListSequentially));

        int p = 8;
        long start1 = System.currentTimeMillis();
        Thread[] t = new Thread[p];
        //t[0] = new Producator(queue, "date", 10);
        //t[0].start();
        int number = nr / (p - 1);
        int rest = nr % (p - 1);
        int x;
        for (int i = 1; i < p; i++) {
            x = number;
            if (rest != 0) {
                x++;
                rest--;
            }
            t[i] = new Consumator(queue, linkedList, i, x);
            t[i].start();
        }
        t[0] = new Producator(queue, filename, length);
        t[0].start();
        for (int i = 1; i < p; i++) {
            t[i].join();
        }
        long finish1 = System.currentTimeMillis();
        long timeElapsed1 = finish1 - start1;
        System.out.println("Timp: " + timeElapsed1);
        //linkedList.show();
        System.out.println(linkedListSequentially.equal(linkedList));
    }
}
