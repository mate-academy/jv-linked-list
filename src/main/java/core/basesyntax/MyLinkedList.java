package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            Node<T> firstNode = new Node<>(null, value, null);
            head = firstNode;
            tail = firstNode;
        } else {
            Node<T> nextNode = new Node<>(tail, value, null);
            tail.next = nextNode;
            tail = nextNode;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index does not exist");
        }
        if (index == size()) {
            add(value);
        } else if (index == 0) {
            addToTheBegining(value);
        } else {
            addToTheMiddle(value, index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T node : list) {
            add(node);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).element;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T oldValue = node.element;
        node.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> current = getNode(index);
        Node<T> prev = current.prev;
        Node<T> next = current.next;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
        }
        size--;
        return current.element;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size(); i++) {
            T currentElement = getNode(i).element;
            if ((currentElement == object)
                    || (currentElement != null
                    && currentElement.equals(object))) {
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
        return size <= 0;
    }

    private void addToTheBegining(T value) {
        Node<T> newNode = new Node<>(null, value, head);
        head.prev = newNode;
        head = newNode;
    }

    private void addToTheMiddle(T value, int index) {
        Node<T> current = getNode(index);
        Node<T> newNode = new Node<>(current.prev, value, current);
        newNode.prev.next = newNode;
        newNode.next.prev = newNode;
    }

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index does not exist");
        }
        int count = 0;
        Node<T> current = head;
        while (count++ != index) {
            current = current.next;
        }
        return current;
    }

    private class Node<T> {
        private T element;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
            size++;
        }
    }
}
