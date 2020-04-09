package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
        size = 0;
    }

    public void addLast(T value) {
        Node<T> last = this.last;
        Node<T> newNode = new Node(last, value, null);
        last = newNode;
        if (last == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        ++size;
    }

    @Override
    public boolean add(T value) {
        addLast(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (size == index) {
            add(value);
        } else {
            Node<T> currentNode = getNodeByIndex(index);
            Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
            size++;
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkPositionIndex(index);
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = getNodeByIndex(index);
        T oldNodeItem = currentNode.item;
        currentNode.item = value;
        return oldNodeItem;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = getNodeByIndex(index);
        if (currentNode.prev == null) {
            first = currentNode.next;
        } else {
            currentNode.prev.next = currentNode.next;
        }
        if (currentNode.next == null) {
            last = currentNode.prev;
        } else {
            currentNode.next.prev = currentNode.prev;
        }
        currentNode.prev = null;
        currentNode.next = null;
        size--;
        return currentNode.item;
    }

    @Override
    public boolean remove(T t) {
        Node<T> currentNode = first;
        for (int index = 0; index < size; index++) {
            if (Objects.equals(currentNode.item, t)) {
                remove(index);
                return true;
            }
            currentNode = currentNode.next;
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
        checkPositionIndex(index);
        Node<T> currentNode = first;
        int currentIndex = 0;
        while (currentIndex < index) {
            currentNode = currentNode.next;
            currentIndex++;
        }
        return currentNode;
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index >= size && index != 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}

