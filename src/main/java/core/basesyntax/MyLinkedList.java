package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> first;
    private Node<T> last;

    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    public void add(T value) {
        linkLast(value);
    }

    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, node(index));
        }
    }

    public void addAll(List<T> list) {
        for (T temp : list) {
            add(temp);
        }
    }

    public T get(int index) {
        checkElementIndex(index);
        return node(index).item;
    }

    public void set(T value, int index) {
        checkElementIndex(index);
        node(index).item = value;
    }

    public T remove(int index) {
        checkElementIndex(index);
        return unlink(node(index));
    }

    public T remove(T t) {
        if (t == null) {
            for (Node<T> x = first; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(x);
                    return null;
                }
            }
        } else {
            for (Node<T> x = first; x != null; x = x.next) {
                if (t.equals(x.item)) {
                    return unlink(x);
                }
            }
        }
        return null;
    }

    private void linkLast(T element) {
        final Node<T> l = last;
        final Node<T> newNode = new Node<>(l, element, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    private void linkBefore(T element, Node<T> currentNode) {
        final Node<T> pred = currentNode.prev;
        final Node<T> newNode = new Node<>(pred, element, currentNode);
        currentNode.prev = newNode;
        if (pred == null) {
            first = newNode;
        } else {
            pred.next = newNode;
        }
        size++;
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

    private Node<T> node(int index) {
        if (index < (size >> 1)) {
            Node<T> x = first;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            Node<T> x = last;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " must be in bounds from 0 to " + (size - 1));
        }
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " must be in bounds from 0 to " + (size));
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
