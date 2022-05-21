class Node {
    private Monom data;
    private Node next;


    Node(Monom d) { data = d; next=null;}

    public Monom getData() {
        return data;
    }

    public void setData(Monom data) {
        this.data = data;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", next=" + next +
                '}';
    }
}