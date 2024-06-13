package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, null, tail);
        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        Node<T> newNode = new Node<>(value, null, null);
        if (index == size) {
            add(value);
        } else if (index == 0) {
            newNode.next = head;
            if (head != null) {
                head.prev = newNode;
            }
            head = newNode;
            if (tail == null) {
                tail = newNode;
            }
            size++;
        } else {
            Node<T> current = findNodeByIndex(index);
            newNode.next = current;
            newNode.prev = current.prev;
            if (current.prev != null) {
                current.prev.next = newNode;
            }
            current.prev = newNode;
            size++;
        }
    }

    @Override
    public T get(int index) {
        checkIndexBounds(index);
        Node<T> node = findNodeByIndex(index);
        return node.data;
    }

    @Override
    public T remove(int index) {
        checkIndexBounds(index);
        Node<T> nodeToRemove = findNodeByIndex(index);
        unlink(nodeToRemove);
        return nodeToRemove.data;
    }

    @Override
    public boolean remove(T object) {
        int index = findIndexByValue(object);
        if (index != -1) {
            remove(index);
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            this.add(element);
        }
    }

    @Override
    public T set(T value, int index) {
        checkIndexBounds(index);
        Node<T> currentNode = findNodeByIndex(index);
        T oldValue = currentNode.data;
        currentNode.data = value;
        return oldValue;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> findNodeByIndex(int index) {
        checkIndexBounds(index);
        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private void checkIndexBounds(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private class Node<T> {
        private T data;
        private Node<T> next;
        private Node<T> prev;

        public Node(T data, Node<T> next, Node<T> prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    private int findIndexByValue(T value) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if ((value == null && node.data == null)
                    || (value != null && value.equals(node.data))) {
                return i;
            }
            node = node.next;
        }
        return -1;
    }

    private void unlink(Node<T> nodeToRemove) {
        if (nodeToRemove.prev == null) {
            head = nodeToRemove.next;
        } else {
            nodeToRemove.prev.next = nodeToRemove.next;
        }
        if (nodeToRemove.next == null) {
            tail = nodeToRemove.prev;
        } else {
            nodeToRemove.next.prev = nodeToRemove.prev;
        }
        size--;
    }
}
