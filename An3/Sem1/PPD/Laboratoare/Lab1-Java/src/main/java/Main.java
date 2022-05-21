import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    private static final int MYTHREADS = 8;
    private static CyclicBarrier cyclicBarrier;

    private void executeSequentially(ConvolutionAlgorithm c) throws InterruptedException {
        c.run(0, c.getMatrix().size() - c.getNr() * 2, 0, c.getMatrix().get(0).size() - c.getNr() * 2);
//        for (int i = 0; i < c.getFinalMatrix().size(); i++) {
//            System.out.println(c.getFinalMatrix().get(i));
//        }
    }

    private void executeConcurentByRows(ConvolutionAlgorithm c) throws Exception {
        // thread - ex general
        //ExecutorService executor = Executors.newFixedThreadPool(MYTHREADS);
        int nr = (c.getMatrix().size() - 2 * c.getNr()) / MYTHREADS;

        Thread[] t = new Thread[MYTHREADS];
        int start = 0, end = start + nr, rest = (c.getMatrix().size() - 2 * c.getNr()) % MYTHREADS;
        for (int i = 0; i < MYTHREADS; i++) {
            if (rest > 0) {
                end++;
                rest--;
            }
            t[i] = new MyThread(c, c.getMatrix(), start, end, 0, c.getMatrix().get(0).size() - 2 * c.getNr());
            t[i].start();
            start = end;
            end = end + nr;
        }
        for (int i = 0; i < MYTHREADS; i++) {
            t[i].join();
        }

        //System.out.println("\nFinished all threads");

//        for (int i = 0; i < c.getFinalMatrix().size(); i++) {
//            System.out.println(c.getFinalMatrix().get(i));
//        }
    }

    private void executeConcurentByColumns(ConvolutionAlgorithm c) throws ExecutionException, InterruptedException {
        // thread - ex general
        //ExecutorService executor = Executors.newFixedThreadPool(MYTHREADS);
        int nr = (c.getMatrix().get(0).size() - 2 * c.getNr()) / MYTHREADS;

        Thread[] t = new Thread[MYTHREADS];
        int start = 0, end = start + nr, rest = (c.getMatrix().get(0).size() - 2 * c.getNr()) % MYTHREADS;
        for (int i = 0; i < MYTHREADS; i++) {
            //Callable worker = new MyThread2("Thread" + String.valueOf(i), c, c.getMatrix(), 0, c.getMatrix().size() - 2 * c.getNr(), i * nr, nr * (i + 1));
            //Future<List<List<Integer>>> future = executor.submit(worker);

            if (rest > 0) {
                end++;
                rest--;
            }
            t[i] = new MyThread(c, c.getMatrix(), 0, c.getMatrix().size() - 2 * c.getNr(), start, end);
            t[i].start();
            start = end;
            end = end + nr;
        }
        for (int i = 0; i < MYTHREADS; i++) {
            t[i].join();
        }

        //System.out.println("\nFinished all threads");


//        for (int i = 0; i < c.getFinalMatrix().size(); i++) {
//            System.out.println(c.getFinalMatrix().get(i));
//        }
    }

    public static void main(String[] args) throws Exception {
        //long start1 = System.currentTimeMillis();
        //int p = 3;
        //p = Integer.parseInt(args[0]);
        cyclicBarrier = new CyclicBarrier(MYTHREADS);
        ConvolutionAlgorithm c = new ConvolutionAlgorithm("file1010.txt", "kernel3.txt");
        MatrixWorker m = new MatrixWorker();
        m.expand(c.getMatrix(), c.getNr());

        //sequentially - ex general
//        List<List<Integer>> matrix = c.run(0, c.getMatrix().size() - c.getNr*2, 0, c.getMatrix().get(0).size()-c.getNr*2);
//        for (int i = 0; i < matrix.size(); i++) {
//            System.out.println(matrix.get(i));
//        }


        Main main = new Main();
        long start1 = System.currentTimeMillis();
//        if (c.getMatrix().size() >= c.getMatrix().get(0).size()) {
//            main.executeConcurentByRows(c);
//        } else {
//            main.executeConcurentByColumns(c);
//        }
        long end1 = System.currentTimeMillis();
        main.executeSequentially(c);
        //main.executeConcurentByRows(c);
        //main.executeConcurentByColumns(c);

        //long end1 = System.currentTimeMillis();
        System.out.println("Elapsed Time in nano seconds: " + (end1 - start1));
        FileWorker fileWorker = new FileWorker(1000, 1000);
        fileWorker.write(c.getFinalMatrix(), "test.txt");

        List<List<Integer>> list = fileWorker.read("test.txt");
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(0).size(); j++) {
                int x = list.get(i).get(j);
                int y = c.getFinalMatrix().get(i).get(j);
                if (x != y) {
                    System.out.println("NU " + String.valueOf(i) + " " + String.valueOf(j));
                }
            }
        }

        //System.out.println((double)(endTime - startTime)/1E6);

    }


}
