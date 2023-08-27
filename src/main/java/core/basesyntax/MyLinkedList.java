package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(T value) {
            this.value = value;
            prev = null;
            next = null;
        }
    }

    @Override
    public void add(T value) {
        Node<T> addedNode = createNode(value);
        if (head == null) {
            head = tail = addedNode;
        } else {
            addLastNode(addedNode);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, size + 1);
        Node<T> addedNode = createNode(value);
        if (head == null) {
            head = tail = addedNode;
        } else if (index == 0) {
            addFirstNode(addedNode);
        } else if (index == size) {
            addLastNode(addedNode);
        } else {
            addMiddleNode(index, addedNode);
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
        checkIndex(index, size);
        return getCurrentNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, size);
        Node<T> currentNode = getCurrentNode(index);
        T result = currentNode.value;
        currentNode.value = value;
        return result;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, size);
        T result = get(index);
        unlink(index);
        size--;
        return result;
    }

    @Override
    public boolean remove(T object) {
        if (size == 0) {
            return false;
        }
        Node<T> currentNode = head;
        for (int index = 0; index < size; index++) {
            if (areEqual(currentNode.value, object)) {
                unlink(index);
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

    private Node<T> createNode(T value) {
        return new Node<T>(value);
    }

    private void addLastNode(Node<T> addedNode) {
        tail.next = addedNode;
        addedNode.prev = tail;
        tail = addedNode;
    }

    private void checkIndex(int index, int bound) {
        if (index < 0 || index >= bound) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds");
        }
    }

    private void addFirstNode(Node<T> addedNode) {
        head.prev = addedNode;
        addedNode.next = head;
        addedNode.prev = null;
        head = addedNode;
    }

    private void addMiddleNode(int index, Node<T> addedNode) {
        Node<T> currentNode = getCurrentNode(index);
        addedNode.next = currentNode;
        addedNode.prev = currentNode.prev;
        currentNode.prev.next = addedNode;
        currentNode.prev = addedNode;
    }

    private Node<T> getCurrentNode(int index) {
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private void unlink(int index) {
        if (head == tail) {
            head = tail = null;
        } else if (index == 0) {
            head.next.prev = null;
            head = head.next;
        } else if (index == size - 1) {
            tail.prev.next = null;
            tail = tail.prev;
        } else {
            Node<T> currentNode = getCurrentNode(index);
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
            currentNode.prev = currentNode.next = null;
            currentNode.value = null;
        }
    }

    private boolean areEqual(T a, T b) {
        return a == b || (a != null && a.equals(b));
    }
}
