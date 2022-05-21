public class Queue {
    Node head = null;
    boolean finished = false;

    public synchronized void put(Node node) {
        if (head == null) {
            head = node;
        } else {
            Node nod = head;
            while (nod.getNext() != null) {
                nod = nod.getNext();
            }
            node.setNext(null);
            nod.setNext(node);
        }
        this.notifyAll();
    }

    public synchronized Monom get() {
        if (head == null && finished == true) {
            //finished = true;
            return null;
        } else {
            while (head == null) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                }
            }

            Monom m = head.getData();
            head = head.getNext();
            return m;
        }
    }

    public synchronized boolean isDone() {
        if (finished == true && head == null) {
            //this.notifyAll();
            return true;
        }
        return false;
    }
}
