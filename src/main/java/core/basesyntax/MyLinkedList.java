package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private Node<T> getNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " out of bound, size is " + size);
        }
        Node<T> currentNode;
        if (index < size / 2) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private Node<T> getNodeByValue(T value) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (currentNode.value == value || currentNode.value != null
                    && currentNode.value.equals(value)) {
                return currentNode;
            }
            currentNode = currentNode.next;
        }
        return null;
    }

    private void unlink(Node<T> currentNode) {
        if (currentNode == tail) {
            tail = currentNode.prev;
        } else {
            currentNode.next.prev = currentNode.prev;
        }
        if (currentNode == head) {
            head = currentNode.next;
        } else {
            currentNode.prev.next = currentNode.next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, null, value);
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
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            head.prev = new Node<>(null, head, value);
            head = head.prev;
        } else {
            Node<T> currentNode = getNodeByIndex(index);
            Node<T> newNode = new Node<>(currentNode.prev, currentNode, value);
            currentNode.prev = newNode;
            newNode.prev.next = newNode;
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
        return getNodeByIndex(index).value;
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
        size--;
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = getNodeByValue(object);
        if (currentNode == null) {
            return false;
        } else {
            unlink(currentNode);
            size--;
        }
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
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
