package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> second;

    @Override
    public void add(T value) {
        if (first == null) {
            first = new Node<>(null, value, null);
            second = first;
        } else {
            Node<T> newNode = new Node<>(second, value, null);
            second.next = newNode;
            second = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bound exception");
        }
        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, first);
            first.prev = newNode;
            first = newNode;
            size++;
        } else {
            Node<T> timeNode = getNodeByIndex(index);
            Node<T> newNode = new Node<>(timeNode.prev, value, timeNode);
            timeNode.prev.next = newNode;
            timeNode.prev = newNode;
            size++;
        }
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
        Node<T> timeNode = getNodeByIndex(index);
        T value = timeNode.item;
        return value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> test = getNodeByIndex(index);
        if (test == null) {
            throw new NullPointerException("Index not correct");
        }
        T newValue = test.item;
        test.item = value;
        return newValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> timeNode = getNodeByIndex(index);
        deletNode(timeNode);
        T revoveItem = timeNode.item;
        return revoveItem;
    }

    @Override
    public boolean remove(T object) {
        Node<T> timeNode = first;
        for (int i = 0; i < size; i++) {
            if (object == timeNode.item || object != null && object.equals(timeNode.item)) {
                deletNode(timeNode);
                return true;
            }
            timeNode = timeNode.next;
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

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        Node<T> timeNode = first;
        if (timeNode == null) {
            throw new IndexOutOfBoundsException("Index out of bound exception");
        } else {
            for (int i = 0; i < index; i++) {
                timeNode = timeNode.next;
            }
            return timeNode;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bound exception");
        }
    }

    private void deletNode(Node<T> node) {
        Node<T> prevNode = node.prev;
        Node<T> nextNode = node.next;
        if (size == 1) {
            first = second = null;
        } else if (prevNode == null) {
            first.next.prev = null;
            first = first.next;
        } else if (nextNode == null) {
            second.prev.next = null;
            second = second.prev;
        } else {
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
        }
        size--;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
