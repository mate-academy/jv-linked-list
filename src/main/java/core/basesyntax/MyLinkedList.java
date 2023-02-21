package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        if (isEmpty()) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> current = getNodeByIndex(index);
        Node<T> newNode = new Node<>(null, value, current);
        if (index == 0) {
            first = newNode;
        } else {
            checkIndex(index);
            Node<T> prevNode = current.prev;
            newNode.prev = prevNode;
            prevNode.next = newNode;
        }
        current.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> current = getNodeByIndex(index);
        T deletedValue = current.item;
        current.item = value;
        return deletedValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> current = getNodeByIndex(index);
        return unlink(current);
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        for (int i = 0; i < size; i++) {
            if (current.item == object || current.item != null && current.item.equals(object)) {
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

    private T unlink(Node<T> current) {
        Node<T> nextNode = current.next;
        Node<T> prevNode = current.prev;
        size--;
        if (isEmpty()) {
            first = null;
        } else if (current.equals(first)) {
            nextNode.prev = null;
            first = nextNode;
        } else if (current.equals(last)) {
            prevNode.next = null;
            last = prevNode;
        } else {
            nextNode.prev = current.prev;
            prevNode.next = current.next;
        }
        return current.item;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> current;
        if (index <= size / 2) {
            current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = last;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is invalid!");
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
