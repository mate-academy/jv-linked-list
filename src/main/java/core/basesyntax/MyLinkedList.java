package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> firstNode;
    private Node<T> lastNode;
    private int size;

    @Override
    public void add(T value) {
        if (firstNode == null) {
            Node<T> newNode = new Node<>(null, value, null);
            firstNode = newNode;
            lastNode = newNode;
        } else {
            lastNode.next = new Node<>(lastNode, value, null);
            lastNode = lastNode.next;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, firstNode);
            if (firstNode != null) {
                firstNode.prev = newNode;
            }
            firstNode = newNode;
            if (lastNode == null) {
                lastNode = newNode;
            }
        } else {
            Node<T> current = findNodeByIndex(index);
            Node<T> prevNode = current.prev;
            Node<T> newNode = new Node<>(prevNode, value, current);
            current.prev = newNode;
            prevNode.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        Node<T> current = findNodeByIndex(index);
        return current.item;

    }

    @Override
    public T set(T value, int index) {
        Node<T> current = findNodeByIndex(index);
        T oldValue = current.item;
        current.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> current = findNodeByIndex(index);
        T deletedValue = current.item;
        if (current.prev == null) {
            firstNode = current.next;
            if (firstNode != null) {
                firstNode.prev = null;
            }
        } else if (current.next == null) {
            lastNode = current.prev;
            lastNode.next = null;
        } else {
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }
        size--;
        return deletedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = firstNode;
        for (int i = 0; i < size; i++) {
            if ((object == null && current.item == null)
                    || (object != null && object.equals(current.item))) {
                remove(i);
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

    private Node<T> findNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        Node<T> current = firstNode;
        if (index < size / 2) {
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = lastNode;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        public Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
