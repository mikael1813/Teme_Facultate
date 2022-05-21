import java.util.Random;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Random random = new Random();
        for(int i=0;i<100;i++){

            int x = random.nextInt(4);
            System.out.println(x);
        }
        int x,y,i;
        boolean ok =true;

        Scanner keyboard = new Scanner(System.in);
        System.out.println("Introdu primul numar");
        x = keyboard.nextInt();
        System.out.println("Introdu al doilea numar");
        y = keyboard.nextInt();

        for(i=2;i<=x;i++){
            if(x%i==0 && y%i==0){
                ok=false;
            }
        }

        if(ok){
            System.out.println("Numerele sunt prime intre ele");
        }
        else {
            System.out.println("Numerele nu sunt prime intre ele");
        }
    }


}
