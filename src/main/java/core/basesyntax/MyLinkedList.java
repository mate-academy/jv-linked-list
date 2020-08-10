package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public boolean add(T value) {
        Node<T> node;
        if (isEmpty()) {
            node = new Node<>(value, null, null);
            first = node;
        } else {
            node = new Node<>(value, null, last);
            last.next = node;
        }
        last = node;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> newNode = new Node<>(value, first, null);
            first.prev = newNode;
            first = newNode;
            size++;
            return;

        }
        Node<T> node = getNode(index);
        Node<T> newNode = new Node<>(value, node, node.prev);
        node.prev.next = newNode;
        node.prev = newNode;
        size++;
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
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNode(index);
        if (size == 1) {
            first = null;
            last = null;
            size--;
            return node.value;
        } else if (index == size - 1) {
            node.prev.next = null;
            last = node.prev;
            size--;
            return node.value;
        } else if (index == 0) {
            node.next.prev = null;
            first = node.next;
            size--;
            return node.value;
        }
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
        return node.value;
    }

    @Override
    public boolean remove(T t) {

        for (int i = 0; i < size; i++) {
            if (t == get(i) || get(i).equals(t)) {
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

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException(
                    "This index is not suitable for the given size");
        }
        Node<T> node;
        if (size / 2 < index) {
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
            return node;
        }
        node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        Node(T value, Node<T> next, Node<T> prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }
}
