package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(value);
        if (head == null) {
            head = node;
        } else {
            tail.next = node;
            node.previous = tail;
        }
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, size + 1);
        Node<T> node = new Node<>(value);
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            node.next = head;
            head = node;
        } else {
            Node<T> previousNode = findNodeByIndex(index - 1);
            node.next = previousNode.next;
            node.next.previous = node;
            node.previous = previousNode;
            previousNode.next = node;
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
        Node<T> node = findNodeByIndex(index);
        return node.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, size);
        Node<T> node = findNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, size);
        Node<T> current = findNodeByIndex(index);
        T deletedElement = current.value;
        unlink(current);
        size--;
        return deletedElement;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = findNodeByValue(object);
        if (current != null) {
            unlink(current);
            size--;
            return true;
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

    private void checkIndex(int index, int size) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("Invalid index " + index);
        }
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.previous;
            }
        }
        return current;
    }

    private Node<T> findNodeByValue(T value) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (current.value == value || current.value != null && current.value.equals(value)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    private void unlink(Node<T> node) {
        if (node == head) {
            head = head.next;
            if (head == null) {
                tail = null;
            }
        } else {
            Node<T> previousNode = node.previous;
            previousNode.next = node.next;
            if (node.next == null) {
                tail = previousNode;
            } else {
                node.next.previous = previousNode;
            }
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> previous;

        public Node(T value) {
            this.value = value;
        }
    }
}
