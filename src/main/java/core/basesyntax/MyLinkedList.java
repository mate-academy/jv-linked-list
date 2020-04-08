package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
        this.size = 0;
        this.first = null;
        this.last = null;
    }

    @Override
    public boolean add(T value) {
        Node<T> l = last;
        Node<T> newNode = new Node<>(l, value, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = last;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (size - index == 0) {
            add(value);
        } else {
            Node<T> currentNode = getNodeByIndex(index);
            Node<T> newNode = new Node<T>(currentNode.prev, value, currentNode);
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
            size++;
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
        return true;
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = getNodeByIndex(index);
        if (currentNode.prev == null) {
            first = new Node<T>(null, value, currentNode.next);
            currentNode.next.prev = first;
        } else {
            Node<T> newNode = new Node<T>(currentNode.prev, value, currentNode.next);
            currentNode.prev.next = newNode;
            currentNode.next.prev = newNode;
        }
        return currentNode.item;
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
    public boolean remove(T value) {
        Node<T> currentNode = first;
        for (int index = 0; index < size; index++) {
            if (value == currentNode.item
                    || currentNode.item != null && currentNode.item.equals(value)) {
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

    private void isIndexExist(int index) {
        if (index < 0 || index >= size && index != 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    private Node<T> getNodeByIndex(int index) {
        isIndexExist(index);
        Node<T> currentNode = first;
        int currentIndex = 0;
        while (currentIndex < index) {
            currentNode = currentNode.next;
            currentIndex++;
        }
        return currentNode;
    }

    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }

        Node(Node<T> prev, Node<T> next) {
            this.next = next;
            this.prev = prev;
        }
    }
}
