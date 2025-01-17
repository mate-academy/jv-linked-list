package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T data;
        private Node<T> next;
        private Node<T> prev;

        Node(T data, Node<T> next, Node<T> prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, null, tail);
        if (tail != null) {
            tail.next = newNode;
        }
        tail = newNode;
        if (head == null) {
            head = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> current = findNodeByIndex(index);
        Node<T> newNode = new Node<>(value, current, current.prev);
        if (current.prev != null) {
            current.prev.next = newNode;
        }
        current.prev = newNode;
        if (index == 0) {
            head = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            throw new IllegalArgumentException("List cannot be null");
        }
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index, false);
        return findNodeByIndex(index).data;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, false);
        Node<T> node = findNodeByIndex(index);
        T oldValue = node.data;
        node.data = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, false);
        Node<T> nodeToRemove = findNodeByIndex(index);
        unlink(nodeToRemove);
        size--;
        return nodeToRemove.data;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        
        while (current != null) {
            if (object == null && current.data == null) {
                unlink(current);
                size--;
                return true;
            } else if (object != null && object.equals(current.data)) {
                unlink(current);
                size--;
                return true;
            }
            current = current.next;
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

    private void checkIndex(int index, boolean isAddOperation) {
        if (index < 0 || index >= size || (index == size && !isAddOperation)) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
    }

    private Node<T> findNodeByIndex(int index) {
        if (index < size / 2) {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else {
            Node<T> current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
            return current;
        }
    }

    private void unlink(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
        node.prev = null;
        node.next = null;
    }
}
