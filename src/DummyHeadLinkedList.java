public class DummyHeadLinkedList<T> implements List<T> {
    private class Node {
        T data;
        Node next;
        Node(T data) { this.data = data; }
    }

    private Node head; // dummy head
    private int size;

    public DummyHeadLinkedList() {
        head = new Node(null); // dummy head node
        size = 0;
    }


    public void add(int index, T element) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        Node prev = head;
        for (int i = 0; i < index; i++) prev = prev.next;
        Node newNode = new Node(element);
        newNode.next = prev.next;
        prev.next = newNode;
        size++;
    }


    public boolean add(T element) { add(size, element); return true; }


    public T get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node curr = head.next;
        for (int i = 0; i < index; i++) curr = curr.next;
        return curr.data;
    }


    public T remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node prev = head;
        for (int i = 0; i < index; i++) prev = prev.next;
        Node curr = prev.next;
        prev.next = curr.next;
        size--;
        return curr.data;
    }


    public int size() { return size; }
}
