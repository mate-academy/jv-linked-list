package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String INDEX_OUT_OF_BOUND_MESSAGE = "Index out of bound";
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, tail, null);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throwIndexOutOfBoundsException(index);
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> indexNode = getNode(index);
        Node<T> prev = indexNode.prev;
        Node<T> newNode = new Node<>(value, prev, indexNode);
        indexNode.prev = newNode;
        if (prev == null) {
            head = newNode;
        } else {
            prev.next = newNode;
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
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if ((current.item == object)
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
            throwIndexOutOfBoundsException(index);
        }
    }

    private Node<T> getNode(int index) {
        Node<T> currentNode;
        if (index < (size / 2)) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private T unlink(Node<T> item) {
        final T element = item.item;
        final Node<T> next = item.next;
        final Node<T> prev = item.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            item.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            item.next = null;
        }

        size--;
        return element;
    }

    public void throwIndexOutOfBoundsException(int index) {
        throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUND_MESSAGE + index);
    }

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        private Node(T item, Node<T> prev, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}
