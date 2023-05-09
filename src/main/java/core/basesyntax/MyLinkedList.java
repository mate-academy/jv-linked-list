package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int DEFAULT_SIZE = 0;
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        size = DEFAULT_SIZE;
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            firstAdd(value);
        } else {
            addToEnd(value);
        }
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkInRange(index);
        if (index == 0) {
            MyLinkedList.Node<T> newNode = new MyLinkedList.Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
            size++;
            return;
        }

        Node<T> currentNode = searchNode(index);
        Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
        newNode.prev.next = newNode;
        newNode.next.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkInRange(index);
        return searchNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkInRange(index);
        Node<T> currentNode = searchNode(index);
        T oldValue = currentNode.item;
        currentNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkInRange(index);
        Node<T> currentNode = searchNode(index);
        unLink(currentNode);
        return currentNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (object == currentNode.item
                    || (object != null && object.equals(currentNode.item))) {
                unLink(currentNode);
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

    private void firstAdd(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        head = newNode;
        tail = newNode;
        size++;
    }

    private void addToEnd(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        tail.next = newNode;
        tail = newNode;
        size++;
    }

    private Node<T> searchNode(int index) {
        Node<T> currentNode;
        if (size - index >= index) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private void unLink(Node<T> currentNode) {
        if (currentNode.prev != null) {
            currentNode.prev.next = currentNode.next;
        } else {
            head = currentNode.next;
        }
        if (currentNode.next != null) {
            currentNode.next.prev = currentNode.prev;
        } else {
            tail = currentNode.prev;
        }
        size--;
    }

    private void checkInRange(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(String
                    .format("No such index(%d) in this list", index));
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T value, Node<T> next) {
            this.item = value;
            this.next = next;
            this.prev = prev;
        }
    }
}
