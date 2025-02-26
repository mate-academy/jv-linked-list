package core.basesyntax;

import java.util.List;

public class MyLinkedList<E> implements MyLinkedListInterface<E> {

    private Node<E> head;
    private Node<E> tail;
    private int size;

    public MyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public void add(E value) {
        Node<E> newNode = new Node<>(value);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void addFirst(E value) {
        Node<E> newNode = new Node<>(value);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    @Override
    public void addLast(E value) {
        add(value);  // Reuse the add method for adding to the end
    }

    @Override
    public E removeFirst() {
        if (head == null) {
            return null;  // List is empty
        }
        E value = head.value;
        head = head.next;
        if (head != null) {
            head.prev = null;
        } else {
            tail = null;  // List is now empty
        }
        size--;
        return value;
    }

    @Override
    public E removeLast() {
        if (tail == null) {
            return null;  // List is empty
        }
        E value = tail.value;
        tail = tail.prev;
        if (tail != null) {
            tail.next = null;
        } else {
            head = null;  // List is now empty
        }
        size--;
        return value;
    }

    @Override
    public E getFirst() {
        if (head == null) {
            return null;  // List is empty
        }
        return head.value;
    }

    @Override
    public E getLast() {
        if (tail == null) {
            return null;  // List is empty
        }
        return tail.value;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public boolean contains(E value) {
        Node<E> current = head;
        while (current != null) {
            if (current.value.equals(value)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public void remove(E value) {
        Node<E> current = head;
        while (current != null) {
            if (current.value.equals(value)) {
                if (current.prev != null) {
                    current.prev.next = current.next;
                } else {
                    head = current.next;
                }
                if (current.next != null) {
                    current.next.prev = current.prev;
                } else {
                    tail = current.prev;
                }
                size--;
                return;
            }
            current = current.next;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<E> current = head;
        while (current != null) {
            sb.append(current.value).append(" <-> ");
            current = current.next;
        }
        sb.append("null");
        return sb.toString();
    }
}
