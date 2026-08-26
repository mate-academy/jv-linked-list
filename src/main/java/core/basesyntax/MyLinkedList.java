package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    public MyLinkedList() {
        this.size = 0;
        this.head = null;
        this.tail = null;
    }

    @Override
    public void add(T value) {
        if (isEmpty()) {
            Node<T> newNode = new Node<>(head, tail, value);
            head = newNode;
            tail = newNode;
        } else {
            Node<T> prevTail = tail;
            tail = new Node<>(prevTail, null, value);
            prevTail.next = tail;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size()) {
            throw new ArrayIndexOutOfBoundsException("The index is greater than LL size");
        }
        Node<T> currentNode = head;
        int i = 0;
        while (i < index) {
            currentNode = currentNode.next;
            i++;
        }
        if (currentNode == null) {
            if (tail == null) {
                Node<T> newNode = new Node<>(head, tail, value);
                head = newNode;
                tail = newNode;
            } else {
                Node<T> prevTail = tail;
                tail = new Node<>(prevTail, null, value);
                prevTail.next = tail;
            }
        } else if (currentNode.prev == null) {
            Node<T> newNode = new Node<>(null, currentNode, value);
            currentNode.prev = newNode;
            head = newNode;
        } else {
            Node<T> prevNode = currentNode.prev;
            Node<T> newNode = new Node<>(prevNode, currentNode, value);
            currentNode.prev = newNode;
            prevNode.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        indexCheck(index);
        Node<T> searchedNode = searchingByIndex(index);
        return searchedNode.value;
    }

    @Override
    public T set(T value, int index) {
        indexCheck(index);
        Node<T> searchedNode = searchingByIndex(index);
        T oldValue = searchedNode.value;
        searchedNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        indexCheck(index);
        Node<T> currentNode = searchingByIndex(index);
        unlink(currentNode);
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        boolean found = false;
        for (int i = 0; i < size(); i++) {
            if (currentNode.value == null ? object == null : currentNode.value.equals(object)) {
                found = true;
                break;
            }
            currentNode = currentNode.next;
        }
        if (!found) {
            return false;
        }
        unlink(currentNode);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    private void indexCheck(int index) {
        if (index < 0 || index > size() - 1) {
            throw new ArrayIndexOutOfBoundsException("The index is greater than LL size");
        }
    }

    private Node<T> searchingByIndex(int index) {
        Node<T> currentNode = head;
        if (index > size / 2) {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        } else {
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        }
        return currentNode;
    }

    private void unlink(Node<T> currentNode) {
        Node<T> prevNode = currentNode.prev;
        Node<T> nextNode = currentNode.next;
        if (prevNode == null) {
            if (nextNode == null) {
                head = null;
                tail = null;
            } else {
                nextNode.prev = null;
                head = nextNode;
            }
        } else if (nextNode == null) {
            prevNode.next = null;
            tail = prevNode;
        } else {
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
        }
        size--;
    }

    private static class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T value;

        public Node(Node<T> prev, Node<T> next, T value) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }
    }
}
