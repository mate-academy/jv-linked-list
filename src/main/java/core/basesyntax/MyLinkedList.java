package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        if (size == 0) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            tail.next = new Node<>(tail, value, null);
            tail = tail.next;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> nodeToInsert = new Node<>(null, value, null);
        if (index == 0) {
            nodeToInsert.next = head;
            head.prev = nodeToInsert;
            head = nodeToInsert;
        } else {
            Node<T> current = getNodeByIndex(index);
            nodeToInsert.prev = current.prev;
            nodeToInsert.next = current;
            current.prev.next = nodeToInsert;
            current.prev = nodeToInsert;
        }
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
        return getNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        Node<T> nodeByIndex = getNodeByIndex(index);
        T previousValue = nodeByIndex.element;
        nodeByIndex.element = value;
        return previousValue;
    }

    @Override
    public T remove(int index) {
        Node<T> current = getNodeByIndex(index);
        T element = current.element;
        unlink(current);
        return element;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (object == current.element || object != null && object.equals(current.element)) {
                unlink(current);
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

    private void unlink(Node<T> node) {
        if (node == head) {
            head = node.next;
        } else if (node == tail) {
            tail = node.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        node.next = null;
        node.prev = null;
        size--;
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        Node<T> current;
        if ((float)(index / size) < 0.5) {
            current = head;
            for (int i = 0; i < index; ++i) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size; i > index; --i) {
                current = current.prev;
            }
        }
        return current;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is not valid");
        }
    }

    private static class Node<T> {
        private T element;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> head, T item, Node<T> tail) {
            this.element = item;
            this.prev = head;
            this.next = tail;
        }
    }
}
