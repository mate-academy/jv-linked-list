package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> head;
    private Node<T> tail;

    MyLinkedList() {
    }

    @Override
    public void add(T value) {
        if (isEmpty()) {
            addHead(value);
        } else {
            addTail(value);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size() || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " is not valid");
        }
        if (isEmpty()) {
            addHead(value);
        } else if (index == 0) {
            addNewHead(value);
        } else if (index == size()) {
            addTail(value);
        } else {
            addInCentre(value, index);
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
        return nodeFromIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = nodeFromIndex(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = nodeFromIndex(index);
        T value = node.item;
        if (index > 0 && index < size() - 1) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        } else if (index == 0) {
            removeHead();
        } else {
            removeTail();
        }
        size--;
        return value;
    }

    @Override
    public boolean remove(T object) {
        if (findIndex(object) == 0) {
            removeHead();
            size--;
            return true;
        } else if (findIndex(object) == size() - 1) {
            removeTail();
            size--;
            return true;
        } else if (findIndex(object) > 0) {
            remove(findIndex(object));
            return true;
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

    private void addHead(T element) {
        Node<T> newNode = new Node<>(null, element, null);
        head = newNode;
        tail = newNode;
    }

    private void addNewHead(T element) {
        Node<T> newNode = new Node<>(null, element, head);
        head.prev = newNode;
        head = newNode;
    }

    private void addTail(T element) {
        Node<T> newNode = new Node<>(tail, element, null);
        tail.next = newNode;
        tail = newNode;
    }

    private void addInCentre(T value, int index) {
        Node<T> current = nodeFromIndex(index);
        Node<T> newNode = new Node<>(current.prev, value, current);
        current.prev.next = newNode;
        current.prev = newNode;
    }

    private Node<T> searchFromHead(int index) {
        Node<T> node = head;
        int tempIndex = 0;
        while (tempIndex < index) {
            node = node.next;
            tempIndex++;
        }
        return node;
    }

    private Node<T> searchFromTail(int index) {
        Node<T> node = tail;
        int tempIndex = size - 1;
        while (tempIndex > index) {
            node = node.prev;
            tempIndex--;
        }
        return node;
    }

    private Node<T> nodeFromIndex(int index) {
        Node<T> node;
        if (index < size() / 2) {
            node = searchFromHead(index);
        } else {
            node = searchFromTail(index);
        }
        return node;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index " + index + " is not valid");
        }
    }

    private void removeHead() {
        if (size() == 1) {
            head = null;
        } else {
            head.next.prev = null;
            head = head.next;
        }
    }

    private void removeTail() {
        tail.prev.next = null;
        tail = tail.prev;
    }

    private int findIndex(T value) {
        int index = 0;
        Node<T> node = head;
        while (node != null) {
            if (isEquals(node.item, value)) {
                return index;
            }
            node = node.next;
            index++;
        }
        return -1;
    }

    private boolean isEquals(T firstValue, T secondValue) {
        return firstValue == secondValue || (firstValue != null
                && firstValue.equals(secondValue));
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
