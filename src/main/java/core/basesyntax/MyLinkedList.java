package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
    }

    @Override
    public void add(T value) {
        Node<T> currentNode = tail;
        Node<T> newNode = new Node<>(currentNode, value, null);
        tail = newNode;
        if (head == null) {
            head = newNode;
        } else {
            currentNode.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> currentNode = findByIndex(index);
        Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
        if (index == 0) {
            head = newNode;
        } else {
            currentNode.prev.next = newNode;
        }
        currentNode.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T listItem : list) {
            add(listItem);
        }
    }

    @Override
    public T get(int index) {
        return findByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = findByIndex(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = findByIndex(index);
        unlink(currentNode);
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(currentNode.value, object)) {
                remove(i);
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

    private Node<T> findByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Element with such index doesn't exist " + index);
        }
        int currentIndex = 0;
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (currentIndex == index) {
                return currentNode;
            }

            currentNode = currentNode.next;
            currentIndex++;
        }

        return head;
    }

    private void unlink(Node<T> currentNode) {
        if (currentNode == head) {
            head = currentNode.next;
            if (size > 1) {
                head.prev = null;
            }
        } else if (currentNode == tail) {
            tail = currentNode.prev;
            tail.next = null;
        } else {
            currentNode.next.prev = currentNode.prev;
            currentNode.prev.next = currentNode.next;
        }
        size--;
    }

    private static class Node<T> {
        private T value;

        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }
    }
}
