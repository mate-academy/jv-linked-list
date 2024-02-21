package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<T>(value);
        if (size > 0) {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
            size++;
        }
        ifEmptyList(newNode);
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", size: " + size());
        }
        if (index == size) {
            add(value);
        } else {
            Node<T> newNode = new Node<T>(value);
            if (index == 0) {
                newNode.next = head;
                head = newNode;
                newNode.next.prev = newNode;
            } else {
                Node<T> currentNode = getNode(index);
                newNode.prev = currentNode.prev;
                newNode.next = currentNode;
                currentNode.prev.next = newNode;
                currentNode.prev = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T node: list) {
            add(node);
        }
    }

    @Override
    public T get(int index) {
        checkInvalidIndex(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkInvalidIndex(index);
        Node<T> node = getNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkInvalidIndex(index);
        T removedValue;
        if (index == 0) {
            removedValue = head.value;
            if (size() == 1) {
                head = null;
                tail = null;
                size--;
                return removedValue;
            }
            head = head.next;
            head.prev = null;
        } else {
            Node<T> prevNode = getNode(index - 1);
            removedValue = prevNode.next.value;
            if (prevNode.next == tail) {
                prevNode.next = null;
            } else {
                prevNode.next = prevNode.next.next;
                prevNode.next.prev = prevNode;
            }
        }
        size--;
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        try {
            Node<T> node = getNode(object);

            if (node.equals(head) && node.equals(tail)) {
                head = null;
                tail = null;
                size--;
                return true;
            }
            if (node.equals(head)) {
                node.next.prev = null;
                head = node.next;
                size--;
                return true;
            }
            if (node.equals(tail)) {
                node.prev.next = null;
                size--;
                return true;
            }
            node.next.prev = node.prev;
            node.prev.next = node.next;
            size--;
            return true;
        } catch (RuntimeException e) {
            return false;
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

    private void ifEmptyList(Node<T> node) {
        if (isEmpty()) {
            head = node;
            tail = node;
            size++;
        }
    }

    private void checkInvalidIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", size: " + size());
        }
    }

    private Node<T> getNode(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private Node<T> getNode(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (current.value == null && object != null
                    || current.value != null && object == null) {
                current = current.next;
            } else {
                if (current.value == null && object == null || current.value.equals(object)) {
                    return current;
                }
                current = current.next;
            }

        }
        throw new RuntimeException(object + " didn't found.");
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
