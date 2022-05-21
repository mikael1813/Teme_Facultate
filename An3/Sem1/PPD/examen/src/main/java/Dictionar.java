import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

public class Dictionar {
    Map<Integer, Valoare> map = new HashMap<>();

    public synchronized void add(int id, Valoare valoare) {
        map.put(id, valoare);
    }

    public synchronized void write() throws IOException {
        String contentToAppend = "";
        for (Map.Entry<Integer, Valoare> entry : map.entrySet()) {
//            System.out.println("Key = " + entry.getKey() +
//                    ", Value = " + entry.getValue());
//            System.out.println("Verificat");

            contentToAppend += "Key = " + entry.getKey() + ", Value = " + entry.getValue() + "\n";

        }
        contentToAppend += "Verificat\n";
        Files.write(
                Paths.get("rezultat.txt"),
                contentToAppend.getBytes(),
                StandardOpenOption.APPEND);


    }
}
