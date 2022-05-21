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

//    public static LinkedList sequentially(int length, String string) {
//        long start = System.currentTimeMillis();
//        LinkedList linkedList = new LinkedList();
//        for (int i = 0; i < length; i++) {
//            String filename = string + i + ".txt";
//            try {
//                //FileReader fileReader = new FileReader(filename);
//                FileInputStream fileInputStream = new FileInputStream(filename);
//                BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));
//                String strLine;
//                //System.out.println(filename);
//                while ((strLine = br.readLine()) != null) {
//                    String[] result = strLine.split(",");
//                    Monom m = new Monom(Integer.parseInt(result[0]), Integer.parseInt(result[1]));
//                    //System.out.println(m);
//                    linkedList.add(m);
//                }
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        long finish = System.currentTimeMillis();
//        long timeElapsed = finish - start;
//        System.out.println("Timp Secvential: " + timeElapsed);
//        //linkedList.show();
//        return linkedList;
//    }


    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
        int length = 100;
        int producatori = 4, consumatori = 4, casier = 1;
        new PrintWriter("facturi.txt").close();
        String filename = "medicamente";
        Queue queue = new Queue();
        Queue queue2 = new Queue();
        //LinkedList linkedListSequentially, linkedList = new LinkedList();

        //linkedListSequentially = sequentially(length, filename);
        //int nr = countMonomi(length, filename);
        //System.out.println(linkedListSequentially.equal(linkedListSequentially));

        int p = 9;
        long start1 = System.currentTimeMillis();
        Thread[] t = new Thread[p];
        //t[0] = new Producator(queue, "date", 10);
        //t[0].start();
        //int number = nr / (p - producatori);
        //int rest = nr % (p - producatori);
        int x;
        for (int i = producatori; i < p - 1; i++) {
            //x = number;
            //if (rest != 0) {
            //    x++;
            //    rest--;
            //}
            t[i] = new Consumator(queue, queue2, i - producatori, consumatori);
            t[i].start();
        }

        int number = length / producatori;
        int rest = length % producatori;
        int start, end;
        start = 0;
        end = 0;
        int rrest = consumatori % producatori;
        int ccat = consumatori / producatori;
        int nrrr;
        for (int i = 0; i < producatori; i++) {
            nrrr = ccat;
            if (rrest > 0) {
                nrrr++;
                rrest--;
            }
            x = number;
            if (rest > 0) {
                x++;
                rest--;
            }
            start = end;
            end = start + x;
            t[i] = new Producator(queue, filename, start, end, consumatori, producatori, i);
            t[i].start();
        }
        t[p - 1] = new Casier(queue2, 0, 1);
        t[p - 1].start();
        for (int i = 1; i < p; i++) {
            t[i].join();
        }
        long finish1 = System.currentTimeMillis();
        long timeElapsed1 = finish1 - start1;
        System.out.println("Timp: " + timeElapsed1);
        //linkedList.show();
        //System.out.println(linkedListSequentially.equal(linkedList));
    }
}
