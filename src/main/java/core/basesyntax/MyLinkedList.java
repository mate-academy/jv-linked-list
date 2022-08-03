package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int numberOfElements;

    public MyLinkedList() {
        head = null;
        tail = null;
        numberOfElements = 0;
    }
    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(null, value, null);
        numberOfElements++;
        if (tail == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index >= 0 && index <= numberOfElements) {
            if (index == numberOfElements) {
                add(value);
                return;
            }
            Node<T> node = new Node<>(null, value, null);
            numberOfElements++;
            if (index == 0) {
                node.next = head;
                head.prev = node;
                head = node;
            } else {
                int counter = 0;
                Node<T> currentNode = head;
                while (counter < index - 1) {
                    currentNode = currentNode.next;
                    counter++;
                }
                node.next = currentNode.next;
                node.prev = currentNode;
                currentNode.next.prev = node;
                currentNode.next = node;
            }
        } else {
            throw new IndexOutOfBoundsException("There is no such index.");
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        if (numberOfElements != 0 && (index >= 0 && index < numberOfElements)) {
            int counter = 0;
            Node<T> currentNode = head;
            while (counter < index) {
                currentNode = currentNode.next;
                counter++;
            }
            return currentNode.value;
        } else {
            throw new IndexOutOfBoundsException("There is no such index.");
        }
    }

    @Override
    public T set(T value, int index) {
        return null;
    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public boolean remove(T object) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
