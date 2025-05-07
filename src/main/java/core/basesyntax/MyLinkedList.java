package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        if (first == null) {
            addFirst(value);
        } else {
            addLast(value);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        if (index == size) {
            linkLast(value);
        } else {
            linkFirst(value, getNode(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        verifyIndex(index);
        Node<T> result = first;
        for (int i = 0; i < index; i++) {
            result = result.next;
        }
        return result.item;
    }

    @Override
    public T set(T value, int index) {
        verifyIndex(index);
        Node<T> current = getNode(index);
        T oldVal = current.item;
        current.item = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        verifyIndex(index);
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> nullNode = first; nullNode != null; nullNode = nullNode.next) {
                if (nullNode.item == null) {
                    unlink(nullNode);
                    return true;
                }
            }
        } else {
            for (Node<T> elseNode = first; elseNode != null; elseNode = elseNode.next) {
                if (areEquals(object, elseNode.item)) {
                    unlink(elseNode);
                    return true;
                }
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

    private Node<T> addFirst(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        first = newNode;
        last = newNode;
        return newNode;
    }

    private Node<T> addLast(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        last.next = newNode;
        last = newNode;
        return newNode;
    }

    private void checkIndex(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Incorrect index: " + index);
        }
    }

    private void verifyIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range: " + index);
        }
    }

    private Node<T> getNode(int index) {
        Node<T> node;
        if (index < (size >> 1)) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private T unlink(Node<T> node) {
        final T element = node.item;
        final Node<T> next = node.next;
        final Node<T> prev = node.prev;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            node.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        node.item = null;
        size--;
        return element;
    }

    private boolean areEquals(T a, T b) {
        return a == b || a != null && a.equals(b);
    }

    private void linkLast(T element) {
        final Node<T> lastNode = this.last;
        final Node<T> newNode = new Node<>(lastNode, element, null);
        this.last = newNode;
        if (lastNode == null) {
            first = newNode;
        } else {
            lastNode.next = newNode;
        }
        size++;
    }

    private void linkFirst(T element, Node<T> node) {
        final Node<T> prev = node.prev;
        final Node<T> newNode = new Node<>(prev, element, node);
        node.prev = newNode;
        if (prev == null) {
            first = newNode;
        } else {
            prev.next = newNode;
        }
        size++;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
