import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Node{
    private final ReentrantLock lock = new ReentrantLock();
    static Lock staticLock = new ReentrantLock();
    private Obiect data;
    private Node next;


    Node(Obiect d) {
        data = d;
        next = null;
    }

//    public static boolean headIsNull(Node node, Monom m) {
//        staticLock.lock();
//        try {
//            if (node.getData().getExponent() == -1) {
//                node.setData(m);
//                return true;
//            }
//            return false;
//        } finally {
//            staticLock.unlock();
//        }
//    }

//    public void addNode(Monom m) {
//        lock.lock();
//        Node node = this;
//        boolean ok = true;
//        if (m.getExponent() < this.getData().getExponent()) {
//            Node node1 = new Node(this.getData());
//            node1.setNext(this.getNext());
//            this.setData(m);
//            this.setNext(node1);
//            ok = false;
//        } else if (m.getExponent() == this.getData().getExponent()) {
//            int x = this.getData().getCoeficient();
//            //System.out.println("x="+x);
//            this.getData().setCoeficient(x + m.getCoeficient());
//            ok = false;
//        }
//        while (node.getNext() != null && ok) {
//            if (node.getNext().getData().getExponent() > m.getExponent()) {
//                Node node1 = new Node(m);
//                node1.setNext(node.getNext());
//                node.setNext(node1);
//                break;
//            } else if (node.getNext().getData().getExponent() == m.getExponent()) {
//                int x = node.getNext().getData().getCoeficient();
//                //System.out.println("x="+x);
//                node.getNext().getData().setCoeficient(x + m.getCoeficient());
//                break;
//            }
//            node = node.getNext();
//        }
//        if (node.getNext() == null && ok) {
//            Node node1 = new Node(m);
//            node.setNext(node1);
//        }
//        lock.unlock();
//    }

    public Obiect getData() {
        //lock.lock();
        try {
            return data;
        } finally {
            //lock.unlock();
        }
    }

    public void setData(Obiect data) {
        //lock.lock();
        try {
            this.data = data;
        } finally {
            //lock.unlock();
        }
    }

    public Node getNext() {
        //lock.lock();
        try {
            return next;
        } finally {
            //lock.unlock();
        }
    }

    public void setNext(Node next) {
        //lock.lock();
        try {
            this.next = next;
        } finally {
            //lock.unlock();
        }
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", next=" + next +
                '}';
    }

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }


//
//    @Override
//    public void lock() {
//
//    }
//
//    @Override
//    public void lockInterruptibly() throws InterruptedException {
//
//    }
//
//    @Override
//    public boolean tryLock() {
//        return false;
//    }
//
//    @Override
//    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
//        return false;
//    }
//
//    @Override
//    public void unlock() {
//
//    }
//
//    @Override
//    public Condition newCondition() {
//        return null;
//    }
}