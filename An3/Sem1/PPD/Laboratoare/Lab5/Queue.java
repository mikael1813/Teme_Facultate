public class Queue {
    Node head = null;
    boolean finished = false;
    int count = 0;

    public synchronized int getCount() {
        return count;
    }

    public synchronized void setCount(int count) {
        this.count = count;
        this.notifyAll();
    }

    public synchronized void put(Node node) {
//        varianta 1: se poate pune un singur element in coada
//        while (head != null) {
//            try {
//                this.wait();
//            } catch (InterruptedException e) {
//
//            }
//        }
//        head = node;
//        this.notifyAll();


//      varianta 2:  prod tot timpul pun in coada
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
//        varianta 1: se poate pune un singur element in coada
//        while (head == null) {
//            try {
//                this.wait();
//            } catch (InterruptedException e) {
//
//            }
//        }
//
//        Monom m = head.getData();
//        head = null;
//        this.notifyAll();
//        return m;


//          varianta 2:  prod tot timpul pun in coada
//        if (head == null && finished == true) {
//            //finished = true;
//            return null;
//        } else {
        while (head == null) {
            try {
                this.wait();
            } catch (InterruptedException e) {
            }
        }
        this.notifyAll();
        Monom m = head.getData();
        head = head.getNext();
        return m;
        //}

    }

    public synchronized boolean isDone() {
        if (finished == true && head == null) {
            //this.notifyAll();
            return true;
        }
        return false;
    }
}
