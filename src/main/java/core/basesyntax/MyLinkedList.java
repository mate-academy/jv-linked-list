package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    private static class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T element, Node<T> next) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        addLast(value);
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexToAdd(index);
        if (index == size) {
            addLast(value);
        } else {
            addBefore(value, getNodeByIndex(index));
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list != null) {
            for (T value : list) {
                add(value);
            }
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> current = getNodeByIndex(index);
        T oldValue = current.element;
        current.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> current = getNodeByIndex(index);
        unlink(current);
        size--;
        return current.element;
    }

    @Override
    public boolean remove(T object) {
        int index = getNodeIndexByValue(object);
        if (index == -1) {
            return false;
        }
        remove(index);
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

    private void addLast(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
            first = last = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        }
    }

    private void addBefore(T value, Node<T> current) {
        Node<T> previous = current.prev;
        Node<T> newNode = new Node<>(previous, value, current);
        current.prev = newNode;
        if (previous == null) {
            first = newNode;
        } else {
            previous.next = newNode;
        }
    }

    private void checkIndexToAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " is out of bounds for size " + size);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " is out of bounds for size " + size);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private int getNodeIndexByValue(T value) {
        Node<T> current = first;
        for (int i = 0; i < size; i++) {
            if (current.element == value
                    || (current.element != null && current.element.equals(value))) {
                return i;
            }
            current = current.next;
        }
        return -1;
    }

    private void unlink(Node<T> node) {
        if (size == 1) {
            first = last = null;
        } else if (node == first) {
            first = node.next;
            node.next.prev = null;
            node.next = null;
        } else if (node == last) {
            last = node.prev;
            node.prev.next = null;
            node.prev = null;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
    }
}
