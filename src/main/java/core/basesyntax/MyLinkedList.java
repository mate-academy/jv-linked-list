package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(MyLinkedList.Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    public void removeFirst() {
        if (size != 1) {
            head.next.prev = null;
            head = head.next;
        } else {
            head = null;
            tail = null;
        }
    }

    public void removeLast() {
        tail.prev.next = null;
        tail = tail.prev;
    }

    @Override
    public void add(T value) {
        final Node<T> l = tail;
        final Node<T> temp = new Node<>(l, value, null);
        if (tail == null) {
            tail = temp;
            head = temp;
        } else {
            tail.next = temp;
            temp.prev = tail;
            tail = temp;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Incorrect index " + index);
        }
        if (index == size) {
            add(value);
            return;
        }
        final Node<T> temp = new MyLinkedList.Node<>(null, value, null);
        if (index == 0) {
            temp.next = head;
            head.prev = temp;
            head = temp;
            size++;
        } else {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            temp.prev = current.prev;
            temp.next = current;
            current.prev.next = temp;
            current.prev = temp;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.item;
    }

    @Override
    public T set(T value, int index) {
        T temp;
        checkIndex(index);
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        temp = current.item;
        current.item = value;
        return temp;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> current = head;
        if (index == 0) {
            current = head;
            removeFirst();
        } else if (index == size - 1) {
            current = tail;
            removeLast();
        } else {
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }
        size--;
        return current.item;
    }

    @Override
    public boolean remove(T object) {
        if ((object == head.item) || (object != null && object.equals(head.item))) {
            removeFirst();
            size--;
            return true;
        }
        Node<T> current = head;
        while (current.next != null
                && !((current.item == object)
                || (current.item != null && current.item.equals(object)))) {
            current = current.next;
        }
        if ((current.next == null && object != null)
                || (object == null && current.equals(tail) && tail.item != null)) {
            return false;
        }
        if (current.equals(tail)) {
            removeLast();
            return true;
        }
        current.prev.next = current.next;
        current.next.prev = current.prev;
        size--;
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Incorrect index" + index);
        }
    }
}
