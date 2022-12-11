package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head = new Node<>(null, null, null);
    private Node<T> tail = new Node<>(null, null, null);
    private int size = 0;

    public MyLinkedList() {
        head.next = tail;
        tail.prev = head;
    }

    @Override
    public void add(T value) {
        Node<T> nodeToAdd = new Node<>(value, null, null);
        if (head.data == null) {
            head.data = value;
            size++;
            return;
        }
        if (size == 1) {
            tail.data = value;
            size++;
            return;
        }
        Node<T> currentNode = tail;
        tail = nodeToAdd;
        tail.prev = currentNode;
        currentNode.next = tail;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("invalid index");
        }
        if (index == size || head == null) {
            add(value);
            return;
        }
        if (index == 0) {
            linkBefore(value, head);
            size++;
            return;
        }
        Node<T> target = getNode(index);
        linkBefore(value, target);
        size++;
    }

    private void linkBefore(T value, Node<T> target) {
        Node<T> newNode = new Node<>(value, null, null);
        Node<T> previousNode = target.prev;
        if (target == head) {
            newNode.next = target;
            target.prev = newNode;
            head = newNode;
            return;
        }
        newNode.next = target;
        target.prev = newNode;
        previousNode.next = newNode;
        newNode.prev = previousNode;
    }

    private Node<T> getNode(int index) {
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("invalid index");
        }
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode.data;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("invalid index");
        }
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        T oldData = currentNode.data;
        currentNode.data = value;
        return oldData;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> currentNode;
        if (index == 0) {
            currentNode = head;
            head = head.next;
            currentNode.next.prev = null;
            size--;
            return currentNode.data;
        }
        currentNode = getNode(index);
        if (currentNode.next == null) {
            currentNode.prev.next = null;
            size--;
            return currentNode.data;
        }
        currentNode.prev.next = currentNode.next;
        currentNode.next.prev = currentNode.prev;
        size--;
        return currentNode.data;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (object == null ? currentNode.data == null : object.equals(currentNode.data)) {
                if (currentNode == head) {
                    head = head.next;
                    head.prev = null;
                    size--;
                    return true;
                }
                if (currentNode.next == null) {
                    currentNode.prev.next = null;
                    size--;
                    return true;
                }
                currentNode.prev.next = currentNode.next;
                size--;
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

    static class Node<T> {
        private T data;
        private Node<T> next;
        private Node<T> prev;

        public Node(T data, Node<T> next, Node<T> prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }
}
