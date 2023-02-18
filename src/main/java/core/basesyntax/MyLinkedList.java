package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> last;
    private Node<T> currentNode;
    private Node<T> nextNode = null;
    private Node<T> prevNode = null;
    private int index;

    public MyLinkedList() {
        head = null;
        last = null;
    }
    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(T item) {
            next = null;
            this.item = item;
            prev = null;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        Node<T> currentNode = head;
        if (head == null) {
            head = newNode;
        } else {
            while (currentNode.next != null) {
                currentNode = currentNode.next;
            }
            currentNode.next = newNode;
            last = newNode;
        }
    }

    @Override
    public void add(T value, int index) {
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
        return null;
    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public boolean remove(T object) {
        Node<T> curentNode = head;
        Node<T> prevNode = null;
        while (curentNode.next != null) {
            if (curentNode.item.equals(object)) {
                if (curentNode == head) {
                    head = curentNode.next;
                } else {
                    prevNode.next = curentNode.next;
                }
                return true;
            }
            prevNode = curentNode;
            curentNode = curentNode.next;
        }
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
