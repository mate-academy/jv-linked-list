package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node firstNode = null;
    private Node lastNode = null;
    private int size = 0;

    @Override
    public void add(T value) {
        Node currentNode = firstNode;
        if (firstNode == null) {
            firstNode = lastNode = new Node(value);
            size++;
            } else {
                lastNode.next = new Node(value);
                lastNode.next.previous = currentNode;
                lastNode = lastNode.next;
                size++;
            }
        }

    @Override
    public void add(T value, int index) {
        Node currentNode = null;
        Node tmp = new Node(value);

        if (firstNode != null) {
            currentNode = firstNode;
            for (int i = 0; i < index + 1; i++) {
                currentNode = currentNode.next;
                if (i == index && index > 0) {
                    currentNode.previous.next = tmp;
                    currentNode.next.previous = tmp;
                    tmp.next = currentNode;
                    tmp.previous = currentNode.previous;

                }
            }
        } else {
            firstNode = tmp;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {

        if (index == 0) {
            return firstNode.data;
        }
        int i = 0;
        Node currentNode = null;
        if (firstNode != null) {
            currentNode = firstNode;
        }
        while (i != index && currentNode != null) {
            i++;
            currentNode = currentNode.next;
        }
        return currentNode == null ? null : currentNode.data;
    }

    @Override
    public void set(T value, int index) {

    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public T remove(T t) {
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    public class Node {
        T data;
        Node previous;
        Node next;

        public Node(T data) {
            this.data = data;
        }
    }
}
