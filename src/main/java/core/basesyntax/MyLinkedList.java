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
            Node<T> newNode = new Node<>(value);
            Node<T> currentNode = getNodeByIndex(index);
            if (currentNode == head) {
                head = newNode;
            } else {
                currentNode.prev.next = newNode;
                newNode.prev = currentNode.prev;
            }
            newNode.next = currentNode;
            currentNode.prev = newNode;
            size++;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
        size++;
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).value;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = getNodeByIndex(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
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
        return head == null && tail == null;
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
        if (currentNode == head) {
            head = currentNode.next;
        } else {
            currentNode.prev.next = currentNode.next;
        }
        if (currentNode == tail) {
            tail = currentNode.prev;
        } else {
            currentNode.next.prev = currentNode.prev;
        }
        size--;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        private Node(T value) {
            this.value = value;
        }
    }
}
