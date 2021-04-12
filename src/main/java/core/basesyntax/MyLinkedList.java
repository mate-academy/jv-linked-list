package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String INDEX_OUT_OF_BOUNDS = "Index is out of bounds!";
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public boolean add(T value) {
        if (size == 0) {
            tail = new Node<>(null, value, null);
            head = tail;
        } else {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
        } else {
            Node<T> newNode = getNode(index);
            Node<T> addedValue = new Node<>(newNode.prev, value, newNode);
            newNode.prev.next = addedValue;
            newNode.prev = addedValue;
        }
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
        checkIndex(index);
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> newNode = getNode(index);
        T currentValue = newNode.item;
        newNode.item = value;
        return currentValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> currentValue = getNode(index);
        final T currentElement = currentValue.item;
        if (currentValue.next == null) {
            tail = currentValue.prev;
        } else {
            currentValue.next.prev = currentValue.prev;
        }
        if (currentValue.prev == null) {
            head = currentValue.next;
        } else {
            currentValue.prev.next = currentValue.next;
        }
        size--;
        return currentElement;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentValue = head;
        for (int i = 0; i < size; i++) {
            if (object == currentValue.item || object != null && object.equals(currentValue.item)) {
                remove(i);
                return true;
            }
            currentValue = currentValue.next;
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
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<T> getNode(int index) {
        Node<T> newNode;
        if (index <= size / 2) {
            newNode = head;
            for (int i = 0; i < index; i++) {
                newNode = newNode.next;                
            }
        } else {
            newNode = tail;
            for (int i = size - 1; i > index; i--) {
                newNode = newNode.prev;
            }
        }
        return newNode;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUNDS);
        }
    }
}
