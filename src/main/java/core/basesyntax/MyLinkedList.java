package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private transient int size = 0;
    private transient Node<T> head;
    private transient Node<T> tail;

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.item = value;
            this.prev = prev;
            this.next = next;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    Node<T> nodeByIndex(int index) {
        if (index < (size >> 1)) {
            Node<T> node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        } else {
            Node<T> node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
            return node;
        }
    }

    T unlink(Node<T> x) {
        final T element = x.item;
        final Node<T> next = x.next;
        final Node<T> prev = x.prev;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }
        x.item = null;
        size--;
        return element;
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            Node<T> newNode = new Node<>(null, value, null);
            tail = newNode;
            head = newNode;
        } else {
            Node<T> newNode = new Node<>(tail, value, null);
            tail = newNode;
            newNode.prev.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (size == index) {
            final Node<T> l = tail;
            final Node<T> newNode = new Node<>(l, value, null);
            tail = newNode;
            if (l == null) {
                head = newNode;
            } else {
                l.next = newNode;
            }
            size++;
        } else {
            final Node<T> beginingNode = nodeByIndex(index).prev;
            final Node<T> newNode = new Node<>(beginingNode, value, nodeByIndex(index));
            nodeByIndex(index).prev = newNode;
            if (beginingNode == null) {
                head = newNode;
            } else {
                beginingNode.next = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T node : list) {
            add(node);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return nodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> x = nodeByIndex(index);
        T oldVal = x.item;
        x.item = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(nodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> x = head; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<T> x = head; x != null; x = x.next) {
                if (object.equals(x.item)) {
                    unlink(x);
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
}
