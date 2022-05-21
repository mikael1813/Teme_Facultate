import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;

public class Main {
    private static final int MYTHREADS = 2;
    private static CyclicBarrier cyclicBarrier;


    private List<List<Integer>> executeSequentially(List<List<Integer>> matrix, List<List<Integer>> kernel, int nr) throws InterruptedException {
        List<List<Integer>> finalMatrix = new ArrayList<>();
        for (int i = 0; i < matrix.size(); i++) {
            List<Integer> list = new ArrayList<>();
            for (int j = 0; j < matrix.get(0).size(); j++) {
                list.add(0);
            }
            finalMatrix.add(list);
        }
        for (int i = nr + 0; i < matrix.size() - 2 * nr + nr; i++) {
            List<Integer> list = new ArrayList<>();
            for (int j = nr + 0; j < matrix.get(0).size() - 2 * nr + nr; j++) {
                int sum = 0;
                for (int x = i - nr; x < i + nr + 1; x++) {
                    for (int y = j - nr; y < j + nr + 1; y++) {
                        sum = sum + matrix.get(x).get(y) * kernel.get(x - (i - nr)).get(y - (j - nr));
                    }
                }
                //mutex.lock();
                finalMatrix.get(i - nr).set(j - nr, sum);
                //mutex.unlock();
                //list.add(sum);
            }


        }
        return finalMatrix;
    }

    private void executeConcurentByRows(List<List<Integer>> matrix, List<List<Integer>> kernel, int nr) throws Exception {
        // thread - ex general
        //ExecutorService executor = Executors.newFixedThreadPool(MYTHREADS);
        int number = (matrix.size() - 2 * nr) / MYTHREADS;

        Thread[] t = new Thread[MYTHREADS];
        int start = 0, end = start + number, rest = (matrix.size() - 2 * nr) % MYTHREADS;
        for (int i = 0; i < MYTHREADS; i++) {
            if (rest > 0) {
                end++;
                rest--;
            }
            t[i] = new MyThread(matrix, kernel, start, end, 0, matrix.get(0).size() - 2 * nr, MYTHREADS);
            t[i].start();
            start = end;
            end = end + number;
        }

        for (int i = 0; i < MYTHREADS; i++) {
            t[i].join();
        }

        for (int i = 0; i < nr; i++) {
            matrix.remove(0);
            matrix.remove(matrix.size() - 1);
        }
    }

    private void executeConcurentByColumns(List<List<Integer>> matrix, List<List<Integer>> kernel, int nr) throws ExecutionException, InterruptedException {
        // thread - ex general
        //ExecutorService executor = Executors.newFixedThreadPool(MYTHREADS);
        int number = (matrix.get(0).size() - 2 * nr) / MYTHREADS;

        Thread[] t = new Thread[MYTHREADS];
        int start = 0, end = start + number, rest = (matrix.get(0).size() - 2 * nr) % MYTHREADS;
        for (int i = 0; i < MYTHREADS; i++) {
            //Callable worker = new MyThread2("Thread" + String.valueOf(i), c, c.getMatrix(), 0, c.getMatrix().size() - 2 * c.getNr(), i * nr, nr * (i + 1));
            //Future<List<List<Integer>>> future = executor.submit(worker);

            if (rest > 0) {
                end++;
                rest--;
            }
            t[i] = new MyThread(matrix, kernel, 0, matrix.size() - 2 * nr, start, end, MYTHREADS);
            t[i].start();
            start = end;
            end = end + number;
        }
        for (int i = 0; i < MYTHREADS; i++) {
            t[i].join();
        }
        for (int i = 0; i < nr; i++) {
            matrix.remove(0);
            matrix.remove(matrix.size() - 1);
        }
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < nr; j++) {
                matrix.get(i).remove(0);
                matrix.get(i).remove(matrix.get(i).size() - 1);
            }
        }

    }

    public static void main(String[] args) throws Exception {
        //int p = 3;
        //p = Integer.parseInt(args[0]);
        cyclicBarrier = new CyclicBarrier(MYTHREADS);

        FileWorker fileWorker = new FileWorker(1000, 1000);

        List<List<Integer>> matrix, kernel;
        matrix = fileWorker.read("file1010.txt");
        kernel = fileWorker.read("kernel3.txt");
        int nr = kernel.size() / 2;

        MatrixWorker m = new MatrixWorker();
        m.expand(matrix, nr);

        Main main = new Main();
        //sout sequentially time
//        long start2 = System.currentTimeMillis();
//        main.executeSequentially(matrix, kernel, nr);
//        long end2 = System.currentTimeMillis();
//        System.out.println((double) (end2 - start2));
        fileWorker.write(main.executeSequentially(matrix, kernel, nr), "output.txt");
        long start1 = System.currentTimeMillis();
        if (matrix.size() >= matrix.get(0).size())
            main.executeConcurentByRows(matrix, kernel, nr);
        else
            main.executeConcurentByColumns(matrix, kernel, nr);
        long end1 = System.currentTimeMillis();
        fileWorker.write(matrix, "rezultat.txt");
//        for (int i = 0; i < matrix.size(); i++) {
//            System.out.println(matrix.get(i));
//        }

        List<List<Integer>> verifyMatrix = fileWorker.read("output.txt");
        boolean ok = false;
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(0).size(); j++) {
                if (!matrix.get(i).get(j).equals(verifyMatrix.get(i).get(j))) {
                    ok = true;
                }
            }
        }
        if (ok) {
            System.out.println("Incorect");
        } else {
            //System.out.println("Elapsed Time in nano seconds: " );
            System.out.println((double) (end1 - start1));
        }
    }
}
