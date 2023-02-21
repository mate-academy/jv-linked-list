package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            tail.next = new Node<>(tail, value, null);
            tail = tail.next;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bound for length "
                    + size);
        }
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> newNode = new Node<>(head.prev, value, head);
            head = newNode;
            size++;
            return;
        }
        Node<T> currentNode = getCurrentNode(index);
        Node<T> anotherNode = new Node<>(currentNode.prev, value, currentNode);
        currentNode.prev.next = anotherNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            throw new RuntimeException("Current list is null!");
        }
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> currentNode = getCurrentNode(index);
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = getCurrentNode(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> currentNode = getCurrentNode(index);
        T oldValue = currentNode.value;
        if (index == 0) {
            head = currentNode.next;
            size--;
            return oldValue;
        }
        if (currentNode.next != null) {
            currentNode.next.prev = currentNode.prev;
        }
        if (currentNode.prev != null) {
            currentNode.prev.next = currentNode.next;
        }
        size--;
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        int index = 0;
        if (size == 1) {
            remove(size - 1);
            return true;
        }
        Node<T> currentNode = head;
        while (currentNode.next != null) {
            if (currentNode.value == object || currentNode.value != null
                    && currentNode.value.equals(object)) {
                remove(index);
                return true;
            }
            index++;
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

    private Node<T> getCurrentNode(int index) {
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Incorrect index " + index + " for length = "
                    + size);
        }
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
