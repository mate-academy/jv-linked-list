package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        final Node<T> currentLastNode = tail;
        final Node<T> newNode = new Node<>(currentLastNode, value, null);
        tail = newNode;

        if (currentLastNode == null) {
            head = newNode;
        } else {
            currentLastNode.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            linkBefore(value, getNode(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> setNode = getNode(index);
        final T oldValue = setNode.item;
        setNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> current = head; current != null; current = current.next) {
            if ((current.item != null && current.item.equals(object)) || current.item == object) {
                unlink(current);
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

    private void linkBefore(T value, Node<T> current) {
        final Node<T> prev = current.prev;
        final Node<T> newNode = new Node<>(prev, value, current);
        current.prev = newNode;

        if (prev == null) {
            head = newNode;
        } else {
            prev.next = newNode;
        }
        size++;
    }

    private Node<T> getNode(int index) {
        checkElementIndex(index);

        Node<T> result;
        if ((index < (size / 2)) && (index != 0)) {
            result = head;
            for (int i = 0; i < index; i++) {
                result = result.next;
            }
        } else {
            result = tail;
            for (int i = size - 1; i > index; i--) {
                result = result.prev;
            }
        }
        return result;
    }

    private T unlink(Node<T> removeNode) {
        final T removedElement = removeNode.item;
        final Node<T> prev = removeNode.prev;
        final Node<T> next = removeNode.next;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            removeNode.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            removeNode.next = null;
        }

        removeNode.item = null;
        size--;
        return removedElement;
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " out of bounce of size " + size);
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
