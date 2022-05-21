import java.util.ArrayList;
import java.util.List;

public class Queue {
    Node head = null;

    int count = 0;

    public synchronized int getCount() {
        return count;
    }

    public synchronized void setCount(int count) {
        this.count = count;
        this.notifyAll();
    }

    public synchronized void put(List<Node> node, int nr) {

        if (get_nr() <= 20 - nr) {
            for (int i = 0; i < nr; i++) {
                if (head == null) {
                    head = node.get(i);
                    this.notify();
                } else {
                    Node nod = head;
                    while (nod.getNext() != null) {
                        nod = nod.getNext();
                    }
                    node.get(i).setNext(null);
                    nod.setNext(node.get(i));
                }
            }
            //this.notifyAll();
        }
    }

    public synchronized List<Obiect> get(int nr) {

        List<Obiect> list = new ArrayList<>();

        while (get_nr() < nr) {
            try {
                this.wait();
                System.out.println(count);
                if (count == 5) {
                    this.notifyAll();
                    return null;
                }
            } catch (InterruptedException e) {
            }
        }
        for (int i = 0; i < nr; i++) {
            Obiect r = head.getData();
            head = head.getNext();
            list.add(r);
        }

        return list;
        //}

    }

    public synchronized int get_nr() {
        int count = 1;
        Node nod = head;
        if (head == null) {
            count = 0;
            return count;
        }

        while (nod.getNext() != null) {
            nod = nod.getNext();
            count += 1;
        }
        return count;
    }

}
