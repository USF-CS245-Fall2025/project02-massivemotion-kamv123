public class DoublyLinkedList<T> implements List<T> {
    private class Node {
        T data;
        Node next, prev;
        Node(T data) { this.data = data; }
    }

    private Node head, tail;
    private int size;

    public DoublyLinkedList() { head = tail = null; size = 0; }


    public void add(int index, T element) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        Node newNode = new Node(element);
        if (index == 0) {
            newNode.next = head;
            if (head != null) head.prev = newNode;
            head = newNode;
            if (tail == null) tail = head;
        } else if (index == size) {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        } else {
            Node curr = head;
            for (int i = 0; i < index; i++) curr = curr.next;
            newNode.prev = curr.prev;
            newNode.next = curr;
            curr.prev.next = newNode;
            curr.prev = newNode;
        }
        size++;
    }


    public boolean add(T element) { add(size, element); return true; }


    public T get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node curr = head;
        for (int i = 0; i < index; i++) curr = curr.next;
        return curr.data;
    }


    public T remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node curr = head;
        for (int i = 0; i < index; i++) curr = curr.next;
        if (curr.prev != null) curr.prev.next = curr.next;
        else head = curr.next;
        if (curr.next != null) curr.next.prev = curr.prev;
        else tail = curr.prev;
        size--;
        return curr.data;
    }


    public int size() { return size; }
}
