import java.io.*;
import java.util.Scanner;

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
        Scanner myObj = new Scanner(System.in);
        int producatori = 5, consumatori = 3, casier = 1, x = 4, y = 3;
        int p;
        p = myObj.nextInt();
        consumatori = myObj.nextInt();
        producatori = p - consumatori;
        new PrintWriter("rezultat.txt").close();
        String filename = "medicamente";
        Queue queue = new Queue();
        Dictionar dictionar = new Dictionar();
        //LinkedList linkedListSequentially, linkedList = new LinkedList();

        //linkedListSequentially = sequentially(length, filename);
        //int nr = countMonomi(length, filename);
        //System.out.println(linkedListSequentially.equal(linkedListSequentially));

        p = producatori + consumatori + 1;
        long start1 = System.currentTimeMillis();
        Thread[] t = new Thread[p];


        for (int i = producatori; i < p - 1; i++) {

            t[i] = new Consumator(queue, dictionar, i, consumatori, y);
            t[i].start();
        }


        for (int i = 0; i < producatori; i++) {

            t[i] = new Producator(queue, filename, consumatori, producatori, i, x, dictionar);
            t[i].start();
        }
        t[p - 1] = new Supervizor(queue, 0, 1, dictionar);
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
