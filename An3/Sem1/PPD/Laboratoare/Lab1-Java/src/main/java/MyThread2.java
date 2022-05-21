import java.util.List;
import java.util.concurrent.Callable;

public class MyThread2 implements Callable<List<List<Integer>>> {
    ConvolutionAlgorithm convolutionAlgorithm;
    List<List<Integer>> matrix;
    int N;
    int M;
    int X;
    int Y;
    String name;

    public MyThread2(String name, ConvolutionAlgorithm convolutionAlgorithm, List<List<Integer>> matrix, int n, int m, int x, int y) {
        this.name = name;
        this.convolutionAlgorithm = convolutionAlgorithm;
        this.matrix = matrix;
        N = n;
        M = m;
        X = x;
        Y = y;
    }


    @Override
    public List<List<Integer>> call() throws Exception {
        //List<List<Integer>> matrix = convolutionAlgorithm.run(N, M, X, Y);
        //System.out.println("Thread" + String.valueOf(N) + "," + String.valueOf(M));
        //FileWorker fileWorker = new FileWorker(0, 0);
        //fileWorker.write(matrix, this.name + ".txt");
        return matrix;
    }
}
