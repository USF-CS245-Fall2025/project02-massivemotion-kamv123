public class SinglyLinkedList<T> implements List<T> {
    private class Node {
        T data;
        Node next;
        Node(T data) { this.data = data; }
    }

    private Node head;
    private int size;

    public SinglyLinkedList() { head = null; size = 0; }

    public void add(int index, T element) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        Node newNode = new Node(element);
        if (index == 0) {
            newNode.next = head;
            head = newNode;
        } else {
            Node curr = head;
            for (int i = 0; i < index - 1; i++) curr = curr.next;
            newNode.next = curr.next;
            curr.next = newNode;
        }
        size++;
    }

    public boolean add(T element) {
        add(size, element);
        return true;
    }

    public T get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node curr = head;
        for (int i = 0; i < index; i++) curr = curr.next;
        return curr.data;
    }


    public T remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        T removed;
        if (index == 0) {
            removed = head.data;
            head = head.next;
        } else {
            Node curr = head;
            for (int i = 0; i < index - 1; i++) curr = curr.next;
            removed = curr.next.data;
            curr.next = curr.next.next;
        }
        size--;
        return removed;
    }

    public int size() { return size; }
}
