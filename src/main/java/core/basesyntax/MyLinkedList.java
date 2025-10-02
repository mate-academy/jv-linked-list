package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    public static final String NO_INDEX_MESSAGE = "no index of value: ";
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode;
        if (head == null && tail == null) {
            newNode = new Node<>(null, value, null);
            head = newNode;
            tail = newNode;
        } else {
            newNode = new Node<>(tail, value, null);
            newNode.prev.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(NO_INDEX_MESSAGE + index);
        }
        Node<T> newNode;
        Node<T> nextNode;
        Node<T> prevNode;
        if (index == 0 && size == 0) {
            newNode = new Node<>(null, value, null);
            head = newNode;
            tail = newNode;
        } else if (index == 0) {
            nextNode = getNodeByIndex(index);
            newNode = new Node<>(null, value, nextNode);
            nextNode.prev = newNode;
            head = newNode;
        } else if (index == size) {
            newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        } else {
            nextNode = getNodeByIndex(index);
            prevNode = nextNode.prev;
            newNode = new Node<>(prevNode, value, nextNode);
            nextNode.prev = newNode;
            prevNode.next = newNode;
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
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIfIndexExists(index);
        Node<T> currentNode = getNodeByIndex(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIfIndexExists(index);
        Node<T> nextNode;
        Node<T> prevNode;
        Node<T> currentNode = getNodeByIndex(index);
        if (index == 0 && size > 1) {
            nextNode = head.next;
            nextNode.prev = null;
            head = nextNode;
        } else if (index == 0) {
            head = null;
            tail = null;
        } else if (index == size - 1) {
            prevNode = tail.prev;
            prevNode.next = null;
            tail = prevNode;
        } else {
            prevNode = currentNode.prev;
            nextNode = currentNode.next;
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
        }
        size--;
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            if (object == null && get(i) == null) {
                remove(i);
                return true;
            }
            if (get(i) != null && get(i).equals(object)) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    private Node<T> getNodeByIndex(int index) {
        checkIfIndexExists(index);
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private void checkIfIndexExists(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(NO_INDEX_MESSAGE + index);
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

}
