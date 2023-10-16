package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private static final String INDEX_OUT_OF_BOUND_MESSAGE = "Index out of bound";
    private Node<T> first;
    private Node<T> last;
    private int size;

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        Node(T item) {
            this.item = item;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (isEmpty()) {
            first = last = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUND_MESSAGE + index);
        }
        if (index == size) {
            add(value);
            return;
        }

        Node<T> newNode = new Node<>(value);
        if (index == 0) {
            newNode.next = first;
            first.prev = newNode;
            first = newNode;
        } else {
            Node<T> current = getNode(index);
            newNode.next = current;
            newNode.prev = current.prev;
            current.prev.next = newNode;
            current.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> current = getNode(index);
        T oldValue = current.item;
        current.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> current = getNode(index);
        return unlink(current);
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        while (current != null) {
            if ((current.item == null && object == null)
                    || (current.item != null && current.item.equals(object))) {
                unlink(current);
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUND_MESSAGE + index);
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    T unlink(Node<T> item) {
        final T element = item.item;
        final Node<T> next = item.next;
        final Node<T> prev = item.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            item.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            item.next = null;
        }

        item.item = null;
        size--;
        return element;
    }
}
