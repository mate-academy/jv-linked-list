package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private int size;
    private Node<T> header;

    public MyLinkedList() {
        header = new Node<>(null, null, null);
        header.next = header.prev = header;
        size = 0;
    }

    @Override
    public boolean add(T value) {
        Node<T> newNode = new Node<>(value, header, header.prev);
        newNode.prev.next = newNode;
        newNode.next.prev = newNode;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        Node<T> node = (index == size ? header : getNodeByIndex(index));
        Node<T> newNode = new Node<>(value, node, node.prev);
        newNode.next.prev = newNode;
        newNode.prev.next = newNode;
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
        return true;
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        T result = getNodeByIndex(index).value;
        getNodeByIndex(index).value = value;
        return result;
    }

    @Override
    public T remove(int index) {
        Node<T> element = getNodeByIndex(index);
        final T result = element.value;
        element.value = null;
        element.prev.next = element.next;
        element.next.prev = element.prev;
        element.next = element.prev = null;
        size--;
        return result;
    }

    @Override
    public boolean remove(T t) {
        for (int i = 0; i < size; i++) {
            if ((t == null && getNodeByIndex(i).value == null) || getNodeByIndex(i).value != null
                    && getNodeByIndex(i).value.equals(t)) {
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
        T value;
        Node<T> next;
        Node<T> prev;

        Node(T value, Node<T> next, Node<T> previous) {
            this.value = value;
            this.next = next;
            this.prev = previous;
        }
    }

    private Node<T> getNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> n = header;
        if (index < (size >> 1)) {
            for (int i = 0; i <= index; i++) {
                n = n.next;
            }
        } else {
            for (int i = size; i > index; i--) {
                n = n.prev;
            }
        }
        return n;
    }
}
