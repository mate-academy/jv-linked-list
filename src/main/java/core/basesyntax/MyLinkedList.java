package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
        last = new Node<T>(null, first, null);
        first = new Node<T>(null, null, last);
    }

    private static class Node<T> {
        T item;
        Node<T> prev;
        Node<T> next;

        public Node(T item, Node<T> prev, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> prev = last;
        prev.item = value;
        last = new Node<T>(null, prev, null);
        prev.next = last;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        } else if (index == 0 || index == size) {
            add(value);
            return;
        }
        Node<T> target = first.next;
        for (int i = 1; i < index; i++) {
            target = target.next;
        }
        Node<T> node = new Node<>(value, target.prev, target);
        target.prev.next = node;
        target.prev = node;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            this.add(value);
        }
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> target = first.next;
        for (int i = 0; i < index; i++) {
            target = target.next;
        }
        return target.item;
    }

    @Override
    public void set(T value, int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> target = first.next;
        for (int i = 0; i < index; i++) {
            target = target.next;
        }
        target.item = value;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> target = first.next;
        for (int i = 0; i < index; i++) {
            target = target.next;
        }
        target.next.prev = target.prev;
        if (index == 0) {
            first.next = target.next;
        } else {
            target.prev.next = target.next;
        }
        size--;
        return target.item;
    }

    @Override
    public T remove(T t) {
        Node<T> target = first.next;
        for (int i = 0; i < size; i++) {
            if ((target.item == null && t == null)
                    || target.item != null && target.item.equals(t)) {
                return remove(i);
            }
            target = target.next;
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
