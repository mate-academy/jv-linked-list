package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
            head = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode;
        if (index == size) {
            add(value);
        } else {
            if (index == 0) {
                newNode = new Node<>(null, value, head);
                head.prev = newNode;
                head = newNode;
            } else {
                Node<T> nextNode = getNode(index);
                newNode = new Node<T>(nextNode.prev, value, nextNode);
                nextNode.prev.next = newNode;
                nextNode.prev = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T object : list) {
            add(object);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = getNode(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = getNode(index);
        Node<T> prev = currentNode.prev;
        Node<T> next = currentNode.next;

        if (currentNode == head) {
            head = next;
            if (size > 1) {
                head.prev = null;
            }
        } else if (currentNode == tail) {
            tail = prev;
            tail.next = null;
        } else {
            next.prev = prev;
            prev.next = next;
        }
        size--;
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

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> item = head;
        for (int i = 0; i < size; i++) {
            if (index == i) {
                return item;
            }
            item = item.next;
        }
        return null;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Non-existent list index");
        }
    }

    private void unlink(Node<T> node) {

    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
