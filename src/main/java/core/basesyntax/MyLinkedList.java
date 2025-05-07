package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> addedNode = new Node<>(null, value, null);
        if (size == 0) {
            head = tail = addedNode;
        } else {
            tail.next = addedNode;
            addedNode.prev = tail;
            tail = addedNode;;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, size + 1);
        Node<T> addedNode = new Node<>(null, value, null);
        if (index == size) {
            add(addedNode.value);
            return;
        }
        if (index == 0) {
            addFirstNode(addedNode);
        } else {
            addMiddleNode(index, addedNode);
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
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
        Node<T> currentNode = getCurrentNode(index);
        checkIndex(index, size);
        T result = currentNode.value;
        unlink(currentNode);
        return result;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int index = 0; index < size; index++) {
            if (currentNode.value == object || currentNode.value != null
                    && currentNode.value.equals(object)) {
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
        checkIndex(index, size);
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

    private T unlink(Node<T> node) {
        Node<T> next = node.next;
        Node<T> prev = node.prev;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            node.prev = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        T value = node.value;
        node.value = null;
        size--;
        return value;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
