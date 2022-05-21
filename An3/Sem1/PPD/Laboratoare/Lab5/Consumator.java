public class Consumator extends Thread {
    Queue queue;
    LinkedList linkedList;
    int numar;
    int nr_threads;

    int count = 0;

    public Consumator(Queue queue, LinkedList linkedList, int numar, int nr_threads) {
        this.queue = queue;
        this.linkedList = linkedList;
        this.numar = numar;
        this.nr_threads = nr_threads;
    }

    @Override
    public void run() {
//        Calendar cal = Calendar.getInstance();
//        long startTime = cal.getTimeInMillis();
//        long currentTime = startTime;
//        while (currentTime < startTime + 2000) {

//            currentTime = cal.getTimeInMillis();
//        int x = 480 / nr_threads;
//        for (int i = 0; i < x; i++) {
        //while (!queue.finished || queue.head != null) {
        //while (!queue.isDone()) {
//        count++;
//        if (count == 1000) {
//            System.out.println();
//        }
        //System.out.println("infinit");
        //for (int i = 0; i < nr_threads; i++) {
        while (true) {
            //System.out.println("Am intrat");
            Monom monom;

            monom = queue.get();
            //System.out.println(monom + " " + this.getId());
            if (monom.getExponent() == -1) {
                break;
            }
//            if (queue.finished == true) {
//                break;
//            }
            if (monom != null)
                linkedList.add(monom);

            //System.out.println("thread " + numar + " monom= " + monom);

        }
        //System.out.println("Am iesit");
    }
}
