public class LinkedList {
    Node head = null;
    //int count=0;

    public synchronized void add(Monom m) {
        //System.out.println(head);
        //count++;
        //System.out.println(count);
        if (head == null) {
            head = new Node(m);
        } else {
            Node node = head;
            boolean ok = true;
            if (m.getExponent() < node.getData().getExponent()) {
                Node node1 = new Node(head.getData());
                node1.setNext(head.getNext());
                head.setData(m);
                head.setNext(node1);
                ok=false;
            }
            else if (m.getExponent() == node.getData().getExponent()) {
                int x = node.getData().getCoeficient();
                //System.out.println("x="+x);
                node.getData().setCoeficient(x + m.getCoeficient());
                ok=false;
            }
            while (node.getNext() != null && ok) {
                if (node.getNext().getData().getExponent() > m.getExponent()) {
                    Node node1 = new Node(m);
                    node1.setNext(node.getNext());
                    node.setNext(node1);
                    break;
                }
                else if (node.getNext().getData().getExponent() == m.getExponent()) {
                    int x = node.getNext().getData().getCoeficient();
                    //System.out.println("x="+x);
                    node.getNext().getData().setCoeficient(x + m.getCoeficient());
                    break;
                }
                node = node.getNext();
            }
            if (node.getNext() == null && ok) {
                Node node1 = new Node(m);
                node.setNext(node1);
            }
        }

        //System.out.println(head);
        //System.out.println(count);
    }

    public void show() {
        System.out.println("Linked List");
        Node node = head;
        while (node.getNext() != null) {
            System.out.println(node.getData());
            node = node.getNext();
        }
        System.out.println(node.getData());
    }

    public boolean equal(LinkedList list){
        Node node1 = head,node2 = list.head;


        while (node1.getNext() != null && node2.getNext() != null){
            //System.out.println(node1);
            //System.out.println(node2);
            if(node1.getData().getCoeficient() != node2.getData().getCoeficient() || node1.getData().getExponent() != node2.getData().getExponent()){
                System.out.println(node1);
                System.out.println(node2);
                return false;
            }
            node1 = node1.getNext();
            node2 = node2.getNext();
        }

        if(node1.getData().getCoeficient() != node2.getData().getCoeficient() || node1.getData().getExponent() != node2.getData().getExponent()){
            System.out.println(node1);
            System.out.println(node2);
            return false;
        }

        return true;
    }
}



