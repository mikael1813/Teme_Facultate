import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


public class Generator {
    public static void write(String s, int limit, int maxGrad) throws IOException {

        Random rand = new Random();
        for (int j = 0; j < 10; j++) {
            FileWriter fileWriter = new FileWriter(s+j+".txt");
            int limitRand = rand.nextInt(limit);
            for (int i = 0; i < limitRand; i++) {
                int coef = rand.nextInt(1000)+1, e = rand.nextInt(maxGrad);
                fileWriter.write(coef + "," + e + "\n");
            }
            fileWriter.close();
        }
    }


    public static void main(String[] args) throws IOException {
        write("date1",500,10000);
    }
}
