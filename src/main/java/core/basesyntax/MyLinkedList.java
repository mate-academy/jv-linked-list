package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    Node<T> head;
    Node<T> tail;
    int size;

    private static class Node<T> {
        T value;
        Node<T> prev;
        Node<T> next;

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
            tail.next = addedNode;
            addedNode.prev = tail;
            tail = addedNode;
        }
        size++;
    }

    private Node<T> createNode(T value) {
        return new Node<T>(value);
    }

    @Override
    public void add(T value, int index) {
        Objects.checkIndex(index, size + 1);
        Node<T> addedNode = createNode(value);
        if (head == null) {
            head = tail = addedNode;
        } else if (index == 0) {
            head.prev = addedNode;
            addedNode.next = head;
            addedNode.prev = null;
            head = addedNode;
        } else if (index == size) {
            tail.next = addedNode;
            addedNode.prev = tail;
            tail = addedNode;
        } else {
            Node<T> currentNode = getCurrentNode(index);
            addedNode.next = currentNode;
            addedNode.prev = currentNode.prev;
            currentNode.prev.next = addedNode;
            currentNode.prev = addedNode;
        }
        size++;
    }

    private void checkIndex(int index) {
        Objects.checkIndex(index, size);
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> currentNode;
        currentNode = getCurrentNode(index);
        return currentNode.value;
    }

    private Node<T> getCurrentNode(int index) {
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = getCurrentNode(index);
        T returnValue = currentNode.value;
        currentNode.value = value;
        return returnValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T returnValue;
        if (index == 0) {
            returnValue = head.value;
            head.value = null;
            head = head.next;
            head.prev = null;
        } else if (index == size - 1) {
            returnValue = tail.value;
            tail.value = null;
            tail = tail.prev;
            tail.next = null;
        } else {
            Node<T> currentNode = getCurrentNode(index);
            returnValue = currentNode.value;
            removeCurrentNode(currentNode);
        }
        size--;
        return returnValue;
    }

    private void removeCurrentNode(Node<T> currentNode) {
        currentNode.prev.next = currentNode.next;
        currentNode.next.prev = currentNode.prev;
        currentNode.prev = currentNode.next = null;
        currentNode.value = null;
    }

    @Override
    public boolean remove(T object) {
        if (size == 0) {
            return false;
        }
        Node<T> removedNode;
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (currentNode.value != null && currentNode.value.equals(object)) {
                removedNode = currentNode;
                if (removedNode.next == null) {
                    remove(size - 1);
                } else if (removedNode.prev == null) {
                    remove(0);
                } else {
                    removeCurrentNode(removedNode);
                }
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
}
