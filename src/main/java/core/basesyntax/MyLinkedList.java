package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            indexExist(index);
            Node<T> newNode = new Node<>(value);
            if (index == 0) {
                newNode.next = head;
                head = newNode;
            } else {
                Node<T> prevNode = getNodeByIndex(index - 1);
                newNode.next = prevNode.next;
                prevNode.next = newNode;
            }
            size++;
        }
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            head = new Node<>(value);
            tail = head;
        } else {
            Node<T> newNode = new Node<>(tail, value);
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public T get(int index) {
        Node<T> currentNode = getNodeByIndex(index);
        return currentNode.value;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = getNodeByIndex(index);
        T old = currentNode.value;
        currentNode.value = value;
        return old;
    }

    @Override
    public T remove(int index) {

        Node<T> currentNode = getNodeByIndex(index);
        unlink(currentNode);
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(currentNode.value, object)) {
                unlink(currentNode);
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

    private void indexExist(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("This index " + index + " is not exist!");
        }
    }

    private Node<T> getNodeByIndex(int index) {
        indexExist(index);
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private void unlink(Node<T> currentNode) {
        if (currentNode.prev == null) {
            head = currentNode.next;
        }
        if (currentNode.next == null) {
            tail = currentNode.prev;
        }
        if (currentNode.prev != null && currentNode.next != null) {
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
        }
        size--;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(T value) {
            this.value = value;
        }

        public Node(Node<T> prev, T value) {
            this.prev = prev;
            this.value = value;
        }
    }
}
