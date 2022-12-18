package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;

    private int size = 0;

    public MyLinkedList() {
        head = new Node<>(null, null, null);
        tail = new Node<>(null, null, null);
        head.next = tail;
        tail.prev = head;
    }

    @Override
    public void add(T value) {
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
        Node<T> nodeToAdd = new Node<>(null, value, null);
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
        if (index == size) {
            add(value);
            return;
        }
        Node<T> target = getNode(index);
        linkBefore(value, target);
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
        checkIndex(index);
        Node<T> currentNode = getNode(index);
        return currentNode.data;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = getNode(index);
        T oldValue = currentNode.data;
        currentNode.data = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> currentNode;
        currentNode = getNode(index);
        unlink(currentNode);
        return currentNode.data;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (object == null ? currentNode.data == null : object.equals(currentNode.data)) {
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

    private void unlink(Node<T> node) {
        if (size == 1) {
            head = new Node<>(null,null,null);
            size--;
            return;
        }
        if (node == head) {
            head = head.next;
            node.next.prev = null;
            size--;
            return;
        }
        if (node == tail) {
            node.prev.next = null;
            size--;
            return;
        }
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
    }

    private void linkBefore(T value, Node<T> target) {
        Node<T> newNode = new Node<>(null, value, null);
        if (target == head) {
            newNode.next = target;
            target.prev = newNode;
            head = newNode;
            return;
        }
        Node<T> previousNode = target.prev;
        newNode.next = target;
        target.prev = newNode;
        previousNode.next = newNode;
        newNode.prev = previousNode;
    }

    private Node<T> getNode(int index) {
        int middlePoint = size / 2;
        Node<T> currentNode;
        if (middlePoint < index) {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        } else {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        }
        return currentNode;
    }

    private void checkIndex(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("index: "
                    + index
                    + " is bigger than or equals size: "
                    + size);
        }
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index:" + index + " is less than 0");
        }
    }

    private static class Node<T> {
        private T data;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T data, Node<T> next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }
}
