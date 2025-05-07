package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(null, value, null);
        if (size == 0) {
            head = node;
        } else {
            node.prev = tail;
            tail.next = node;
        }
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (size < index || index < 0) {
            throw new IndexOutOfBoundsException("Illegal index value. "
                    + "No such index or index less than 0");
        }
        if (index == size) {
            add(value);
        } else {
            Node<T> node = new Node<>(null, value, null);
            Node<T> booferNode = getNodeByIndex(index);
            if (index == 0) {
                head.prev = node;
                node.next = head;
                head = node;
            } else {
                node.prev = booferNode.prev;
                node.next = booferNode;
                booferNode.prev.next = node;
                booferNode.prev = node;

            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            throw new NullPointerException("This Collection is empty");
        }
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNodeByIndex(index);
        T result = node.element;
        node.element = value;
        return result;
    }

    @Override
    public T remove(int index) {
        Node<T> result = getNodeByIndex(index);
        unlink(result);
        return result.element;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> node = head; node != null; node = node.next) {
            if (object == node.element || object != null
                    && object.equals(node.element)) {
                unlink(node);
                return true;
            }
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

    private Node<T> getNodeByIndex(int index) {
        if (size <= index || index < 0) {
            throw new IndexOutOfBoundsException("Illegal index value. "
                    + "No such index or index less than 0");
        }
        Node<T> node = index < (size >> 1) ? head : tail;
        if (index < (size >> 1)) {
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            for (int i = 0; i < (size - index - 1); i++) {
                node = node.prev;
            }
        }
        return node;
    }

    private void unlink(Node<T> node) {
        if (node.prev == null) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node.next == null) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        size--;
    }
}
