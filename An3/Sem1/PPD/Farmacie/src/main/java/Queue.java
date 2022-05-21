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

    public synchronized void put(Node node) {

        if (head == null) {
            head = node;
            this.notify();
        } else {
            Node nod = head;
            while (nod.getNext() != null) {
                nod = nod.getNext();
            }
            node.setNext(null);
            nod.setNext(node);
        }
        //this.notifyAll();
    }

    public synchronized Reteta get() {

        while (head == null) {
            try {
                this.wait();
            } catch (InterruptedException e) {
            }
        }

        Reteta r = head.getData();
        head = head.getNext();
        return r;
        //}

    }

}
