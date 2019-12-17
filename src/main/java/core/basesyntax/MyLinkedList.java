package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    int size = 0;

    public MyLinkedList() {
        head = tail = null;
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            head = new Node<>(value);
            tail = head;
            size++;
            return;
        }
        tail = new Node<>(value, null, tail);
        tail.prev.next = tail;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        Node<T> end = new Node<>(value);
        Node current = head;
        int jump = 0;
        while (jump < index - 1) {
            current = current.next;
            jump++;
        }
        end.next = current.next;
        current.next = end;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T temp : list) {
            add(temp);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.value;
    }

    @Override
    public void set(T value, int index) {
        checkIndex(index);
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        current.setData(value);
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> current = head;
        Node<T> toRemove = null;
        if (index == 0) {
            toRemove = head;
            current.getNext().setNext(null);
            head = current.getNext();
            return toRemove.getData();
        }
        if (index == size - 1) {
            current = tail;
            toRemove = tail;
            current.getPrev().setNext(null);
            tail = current.getPrev();
            return toRemove.getData();
        }
        int i = 0;
        while (i < index - 1) {
            current = current.getNext();
            i++;
        }
        toRemove = current;
        current.getPrev().setNext(current.getNext());
        current.getNext().setPrev(current.getPrev());
        return toRemove.getData();
    }

    @Override
    public T remove(T t) {
        if (isEmpty()) {
            throw new IllegalStateException();
        }
        if (objectExists(t)) {
            Node<T> node = new Node<>(t);
            if (node == tail) {
                node.getPrev().setNext(null);
                tail = node.getPrev();
            } else if (node == head) {
                node.getNext().setPrev(null);
                this.head = node.getNext();
            } else {
                node.getNext().setPrev(node.getPrev());
                node.getPrev().setNext(node.getNext());
            }
        }
        return t;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    public boolean objectExists(T t) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (current == t) {
                return true;
            }
        }
        return false;
    }
}
