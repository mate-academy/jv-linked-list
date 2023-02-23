package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    static class Node<T> {
        T element;
        Node<T> next;
        Node<T> prev;

        public Node(T element) {
            this.element = element;
        }
    }

        private Node<T> head;
        private Node<T> tail;
        private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
         if (head == null) {
            head = tail = newNode;
        } else {
             tail.next = newNode;
         }
         size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, size + 1);
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            head = newNode;
        } else if (index == size) {
            tail.next = newNode;
            tail = newNode;
        } else {
            Node<T> prevNode = getNodebyIndex(index - 1);
            newNode.next = prevNode.next;
            prevNode.next = newNode;
        }
        size++;
    }



    @Override
    public void addAll(List<T> list) {
    }

    @Override
    public T get(int index) {
        return null;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, size);
        Node<T> node = getNodebyIndex(index);
        node.element = value;

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

    private void checkIndex(int index, int size) {
        if (index < 0 || index >= size) {
            throw new ArrayListIndexOutOfBoundsException("Index:" + index + ", Out Of Bounds");
        }
    }

    private Node<T> getNodebyIndex(int index) {
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }
}
