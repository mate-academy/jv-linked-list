package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node head;
    private int size;

    @Override
    public boolean add(T value) {
        if (isEmpty()) {
            head = new Node(value, null, null);
            size++;
        } else {
            getLast().next = new Node(value, getLast(), null);
            size++;
        }
        return true;
    }

    @Override
    public void add(T value, int index) {
        indexReview(index, size);
        if (index == size ) {
            add(value);
            return;
        }
        if (index == 0) {
            Node current = new Node(value, null, head);
            head.prev = current;
            head = current;
            size++;
        } else {
            Node current = new Node(value, getByIndex(index).prev, getByIndex(index));
            current.prev.next = current;
            current.next.prev = current;
            size++;
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
        return true;
    }

    @Override
    public T get(int index) {
        indexReview(index, size - 1);
        return getByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        indexReview(index, size - 1);
        T oldValue = get(index);
        getByIndex(index).item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        indexReview(index, size - 1);
        Node removed = getByIndex(index);
        if (removed.prev != null && removed.next != null) {
            removed.prev.next = removed.next;
            removed.next.prev = removed.prev;
        } else if (removed.prev == null && removed.next != null) {
            head = removed.next;
            head.prev = null;
        } else if (removed.prev != null && removed.next == null) {
            removed.prev.next = null;
        }
        size--;
        return removed.item;
    }

    @Override
    public boolean remove(T t) {
        Node current = head;
        for (int i = 0; i < size; i++) {
            T item = current.item;
            if (t == null ? item == null : item.equals(t)) {
                remove(i);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node getLast() {
        Node current = head;
        while (current.next != null) {
            current = current.next;
        }
        return current;
    }

    private void indexReview(int index, int condition) {
        if (index < 0 || index > condition) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node getByIndex(int index) {
        Node current = head;
        int count = 0;
        while (count < index) {
            current = current.next;
            count++;
        }
        return current;
    }

    private class Node {
        private T item;
        private Node prev;
        private Node next;

        public Node(T item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}
