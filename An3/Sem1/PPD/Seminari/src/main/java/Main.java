import java.util.Random;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        int n = 10000000;
        int[] a = new int[n];
        int[] b = new int[n];
        int[] c = new int[n];
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            a[i] = r.nextInt(255);
            b[i] = r.nextInt(255);
        }
//        int a[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
//        int b[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
//        int[] c = new int[10];

//        for (int i = 0; i < a.length; i++) {
//            c[i] = a[i] + b[i];
//        }

        long startTime = System.nanoTime();
        int p = 32;
        Thread[] t = new Worker[p];
        for (int i = 0; i < t.length; i++) {
            t[i] = new Worker(i, p, a, b, c);
            t[i].start();
        }
        for (int i = 0; i < t.length; i++) {
            t[i].join();
        }
        long stopTime = System.nanoTime();

//        Thread t = new Worker();
//        t.start();
//        t.join();

        for (int i = 0; i < c.length; i++) {
            //System.out.println(c[i]);
        }
        System.out.println((double) (stopTime - startTime) / 1E6);
    }
}
