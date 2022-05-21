public class Consumator extends Thread {
    Queue queueReteta;
    Queue queueCasier;
    //LinkedList linkedList;
    int numar;
    int nr_threads;

    int count = 0;

    public Consumator(Queue queue, Queue queue1, int numar, int nr_threads) {
        this.queueReteta = queue;
        this.queueCasier = queue1;
        //this.linkedList = linkedList;
        this.numar = numar;
        this.nr_threads = nr_threads;
    }

    @Override
    public void run() {

        while (true) {

            Reteta reteta;

            reteta = queueReteta.get();

            if (reteta.getId() == -1) {
                break;
            }

            if (reteta != null) {
                System.out.println("Reteta nr " + reteta.getId() + " a fost preluat de farmacistul " + numar);
                int pret = 0;
                for (int i = 0; i < reteta.getMedicamente().size(); i++) {
                    pret += reteta.getMedicamente().get(0).getPret();
                }
                reteta.setPret(pret);
                System.out.println("Reteta nr " + reteta.getId() + " a fost pregatita si pretul ei este " + pret);

                queueCasier.put(new Node(reteta));

                System.out.println("Reteta nr " + reteta.getId() + " a fost adaugata in coada de la casierie");
            }

        }

        queueCasier.setCount(queueCasier.getCount() + 1);
        if (queueCasier.getCount() == nr_threads) {
            queueCasier.put(new Node(new Reteta(-1, -1, null)));
        }

    }
}
