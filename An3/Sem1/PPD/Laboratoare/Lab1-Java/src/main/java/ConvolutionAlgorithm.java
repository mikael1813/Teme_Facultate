import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class ConvolutionAlgorithm {
    private List<List<Integer>> matrix = new ArrayList<>();
    private List<List<Integer>> finalMatrix = new ArrayList<>();
    private List<List<Integer>> kernel = new ArrayList<>();

    private ReentrantLock mutex = new ReentrantLock();

    public List<List<Integer>> getFinalMatrix() {
        return finalMatrix;
    }

    public void setFinalMatrix(List<List<Integer>> finalMatrix) {
        this.finalMatrix = finalMatrix;
    }

    private int nr;

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    private FileWorker fileWorker = new FileWorker(10, 10);

    public List<List<Integer>> getMatrix() {
        return matrix;
    }

    public void setMatrix(List<List<Integer>> matrix) {
        this.matrix = matrix;
    }

    public List<List<Integer>> getKernel() {
        return kernel;
    }

    public void setKernel(List<List<Integer>> kernel) {
        this.kernel = kernel;
    }

    public ConvolutionAlgorithm(String matrixName, String kernelName) {
        this.matrix = fileWorker.read(matrixName);
        this.kernel = fileWorker.read(kernelName);
        nr = kernel.size() / 2;
        for (int i = 0; i < this.matrix.size(); i++) {
            List<Integer> list = new ArrayList<>();
            for (int j = 0; j < this.matrix.get(0).size(); j++) {
                list.add(0);
            }
            finalMatrix.add(list);
        }

    }

    /*  N = row start
        M = row end
        X = column start
        Y = column end
     */
    public void run(int N, int M, int X, int Y) {
        for (int i = nr + N; i < M + nr; i++) {
            List<Integer> list = new ArrayList<>();
            for (int j = nr + X; j < Y + nr; j++) {
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

            //finalMatrix.add(list);
        }
    }

//    public static void main(String[] args) {
//        ConvolutionAlgorithm c = new ConvolutionAlgorithm("file1010.txt", "kernel3.txt");
//        MatrixWorker m = new MatrixWorker();
//        m.expand(c.matrix, c.nr);
//        System.out.println(c.matrix);
//        //List<List<Integer>> matrix = c.run(0, c.matrix.size(), 0, c.matrix.get(0).size());
//        //
//        ExecutorService executor = Executors.newFixedThreadPool(MYTHREADS);
//        int first = 0;
//        for (int i = 0; i < MYTHREADS; i++) {
//            int nr = (c.matrix.size() - 2 * c.nr) / MYTHREADS;
//            int k = 0;
//            if (i % 2 == 1) {
//                k = 1;
//            } else {
//                k = 0;
//            }
//            if (i == 0) first = 0;
//            Runnable worker = new MyThread("Thread"+String.valueOf(i),c, c.matrix, first, first + nr + k, 0, c.matrix.get(0).size());
//            first = first + nr + k;
//            executor.execute(worker);
//        }
//        executor.shutdown();
//        // Wait until all threads are finish
//        while (!executor.isTerminated()) {
//
//        }
//        System.out.println("\nFinished all threads");
//        //
//        System.out.println();
////        for (int i = 0; i < matrix.size(); i++) {
////            System.out.println(matrix.get(i));
////        }
//    }
}
