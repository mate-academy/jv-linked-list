package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
        first = new Node<>(null, null, last);
        last = new Node<>(null, first, null);
    }

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(T item, Node<T> prev, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public boolean add(T value) {
        Node<T> newNode;
        if (size == 0) {
            newNode = new Node<>(value, first, last);
        } else {
            Node<T> target = first.next;
            for (int i = 1; i < size; i++) {
                target = target.next;
            }
            newNode = new Node<>(value, target, target.next);
        }
        newNode.prev.next = newNode;
        newNode.next.prev = newNode;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            add(value);
            return;
        } else {
            Node<T> target = first.next;
            for (int i = 1; i <= index; i++) {
                target = target.next;
            }
            Node<T> newNode = new Node<>(value, target.prev, target);
            newNode.prev.next = newNode;
            newNode.next.prev = newNode;
            size++;
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
        return true;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> target = first.next;
        for (int i = 0; i < index; i++) {
            target = target.next;
        }
        return target.item;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> target = first.next;
        for (int i = 0; i < index; i++) {
            target = target.next;
        }
        return target.item = value;
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
        target.prev.next = target.next;
        target.next.prev = target.prev;
        size--;
        return target.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> target = first.next;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(target.item, object)) {
                remove(i);
                return true;
            }
            target = target.next;
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
}
