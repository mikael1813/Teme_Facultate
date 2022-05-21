import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cont {
    Lock lockRON = new ReentrantLock();
    Lock lockEUR = new ReentrantLock();
////
    Object lR = new Object();
    Object lE = new Object();
    private int soldRON = 0;
    private int soldEUR = 0;

    Cont(int sold) {
        this.soldRON = sold;
    }

    //synchronized void depuneLEI(){ // SOLUTIA 1, simpla dar
    void depuneLEI() {
//        synchronized(lR) { // SOLUTIA 2, MAI GRANULARA; SOLUTIA 3
//        synchronized (lR) {
//            for (int i = 0; i < 1000; i++) {
////            sold+=1;
//                soldRON++;
//            }
//        }
//        }
        lockRON.lock(); // SOLUTIA 4; din Java 1.5
           // lockEUR.lock();
            for (int i = 0; i < 1000; i++) {
                soldRON+=1;
            }
        lockRON.unlock();
//        lockRON.lock(); // SOLUTIA 4; din Java 1.5
//            lockEUR.lock();
//            for (int i = 0; i < 1000; i++) {
//                soldRON+=soldEUR/5.0;
//                //soldEUR-=
//            }
//            lockEUR.unlock();
//        lockRON.unlock();
    }

    synchronized void retrageLEI() {
//        lockEUR.lock();
//            lockRON.lock();
        //synchronized (this) {
        for (int i = 0; i < 1000; i++) {
            soldRON -= 1;
        }
        //}
    }

    public int getSold() {
        return soldRON;
    }
}