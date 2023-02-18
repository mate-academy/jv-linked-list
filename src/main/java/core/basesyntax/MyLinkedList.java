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
        currentNode = head;
        if (head == null) {
            index = 0;
            head = newNode;
            last = newNode;
        } else {
            while (currentNode.next != null) {
                currentNode = currentNode.next;
            }
            currentNode.next = newNode;
            newNode.prev = currentNode;
            last = newNode;
            index++;
        }
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode = new Node<>(value);
        currentNode = isHeadOrLast(index);
        if (index == 0) {
            currentNode.prev = newNode;
            newNode.next = currentNode;
            head = newNode;
            this.index++;
        } else if (index == this.index + 1) {
            add(value);
        } else {
            currentNode = getNode(index);
            prevNode = currentNode.prev;
            prevNode.next = newNode;
            newNode.next = currentNode;
            currentNode.prev = newNode;
            newNode.prev = prevNode;
            this.index++;
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
        currentNode = head;
        if (index == 0) {
            return currentNode.item;
        } else if (index == this.index) {
            currentNode = last;
            return currentNode.item;
        }
        currentNode = getNode(index);
        return currentNode.item;
    }

    @Override
    public T set(T value, int index) {
        if (index == 0) {
            head.item = value;
        } else if (index == this.index) {
            last.item = value;
        } else {
            currentNode = getNode(index);
            currentNode.item = value;
        }
        return value;
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
