package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private int size;

    public MyLinkedList() {
        head = new Node<>(null, null, null);
        head.next = head.prev = head;
        this.size = 0;
    }

    @Override
    public boolean add(T value) {
        Node<T> newNode = new Node<>(value, head, head.prev);
        newNode.prev.next = newNode;
        newNode.next.prev = newNode;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        Node<T> indNode = (index == size ? head : getNode(index));
        Node<T> newNode = new Node<>(value, indNode, indNode.prev);
        newNode.next.prev = newNode;
        newNode.prev.next = newNode;
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T a : list) {
            add(a);
        }
        return true;
    }

    @Override
    public T get(int index) {
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        T old = getNode(index).item;
        getNode(index).item = value;
        return old;
    }

    @Override
    public T remove(int index) {
        Node<T> element = getNode(index);
        final T past = element.item;
        element.prev.next = element.next;
        element.next.prev = element.prev;
        size--;
        return past;
    }

    @Override
    public boolean remove(T t) {
        for (int i = 0; i < size; i++) {
            if (getNode(i).item == null && t == null
                    || getNode(i).item.equals(t) && getNode(i).item != null) {
                remove(i);
                return true;
            }
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

    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        Node(T item, Node<T> next, Node<T> prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Out of bounds!!!");
        }
        Node<T> element = head;
        if (index < (size / 2)) {
            for (int i = 0; i <= index; i++) {
                element = element.next;
            }
        } else {
            for (int i = size; i > index; i--) {
                element = element.prev;
            }
        }
        return element;
    }
}
