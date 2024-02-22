package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<T>(value);
        if (size > 0) {
            node.prev = tail;
            tail.next = node;
            tail = node;
        } else {
            head = node;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        addByIndex(value, index);
    }

    @Override
    public void addAll(List<T> list) {
        for (T node: list) {
            add(node);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T removedValue;
        Node<T> node = getNode(index);
        removedValue = node.value;
        unlink(node);
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        try {
            Node<T> node = getNode(object);
            unlink(node);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    private void unlink(Node node) {
        if (node.equals(head) && node.equals(tail)) {
            head = null;
            tail = null;
            size--;
        } else if (node.equals(head)) {
            node.next.prev = null;
            head = node.next;
            size--;
        } else if (node.equals(tail)) {
            node.prev.next = null;
            size--;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            size--;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void addByIndex(T value, int index) {
        Node<T> node = new Node<T>(value);
        if (index == 0) {
            node.next = head;
            head = node;
            node.next.prev = node;
        } else {
            Node<T> currentNode = getNode(index);
            node.prev = currentNode.prev;
            node.next = currentNode;
            currentNode.prev.next = node;
            currentNode.prev = node;
        }
        size++;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", size: " + size());
        }
    }

    private Node<T> getNode(int index) {
        Node<T> current;
        if (index <= size / 2) {
            current = head;
            int i = 0;
            while (i < index) {
                current = current.next;
                i++;
            }
        } else {
            current = tail;
            int i = size - 1;
            while (i > index) {
                current = current.prev;
                i--;
            }
        }
        return current;
    }

    private Node<T> getNode(T object) {
        Node<T> current = head;
        if (object == null) {
            while (current != null) {
                if (current.value == null) {
                    return current;
                }
                current = current.next;
            }
        }
        while (current != null) {
            if (current.value == null) {
                current = current.next;
            } else if (current.value.equals(object)) {
                return current;
            } else {
                current = current.next;
            }
        }
        throw new RuntimeException(object + " not found.");
    }

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(T value) {
            this.value = value;
            this.prev = null;
            this.next = null;
        }
    }
}
