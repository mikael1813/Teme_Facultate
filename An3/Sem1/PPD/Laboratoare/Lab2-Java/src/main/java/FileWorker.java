import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FileWorker {
    int N, M;

    public FileWorker(int n, int m) {
        N = n;
        M = m;
    }

    public int getN() {
        return N;
    }

    public void setN(int n) {
        N = n;
    }

    public int getM() {
        return M;
    }

    public void setM(int m) {
        M = m;
    }

    public List<List<Integer>> read(String filename) {
        try {
            File myObj = new File(filename);
            List<List<Integer>> matrix = new ArrayList<>();
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                List<Integer> list = new ArrayList<>();
                List<String> strings = new ArrayList<>();
                strings = Arrays.asList(data.split(" "));
                for (int i = 0; i < strings.size(); i++) {
                    list.add(Integer.parseInt(strings.get(i)));
                }
                matrix.add(list);
            }
            myReader.close();
            return matrix;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void write(List<List<Integer>> matrix, String filename) {
        try {
            FileWriter myWriter = new FileWriter(filename);
            for (int i = 0; i < matrix.size(); i++) {
                for (int j = 0; j < matrix.get(0).size() - 1; j++) {
                    myWriter.write(String.valueOf(matrix.get(i).get(j)) + " ");
                }
                myWriter.write(String.valueOf(matrix.get(i).get(matrix.get(0).size() - 1)) + "\n");
            }
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generate() throws IOException {
        FileWriter myWriter = new FileWriter("file" + String.valueOf(N) + String.valueOf(M) + ".txt");
        List<List> matrix = new ArrayList<>();
        for (int i = 0; i < this.N; i++) {
            List<Integer> list = new ArrayList<>();
            for (int j = 0; j < this.M; j++) {
                list.add((int) (Math.random() * 255 + 1));
            }
            matrix.add(list);
        }
        System.out.println(matrix);
        for (int i = 0; i < this.N; i++) {
            for (int j = 0; j < this.M - 1; j++) {
                myWriter.write(String.valueOf(matrix.get(i).get(j)) + " ");
            }
            myWriter.write(String.valueOf(matrix.get(i).get(M - 1)) + "\n");
        }
        myWriter.close();
    }

    public static void main(String[] args) throws IOException {
        FileWorker g = new FileWorker(10000, 10);
        g.generate();
        //g.read("file1010.txt");
    }
}
