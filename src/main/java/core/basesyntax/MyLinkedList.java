package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        if (last == null) {
            first = new Node<>(null, value, null);
            last = first;
        } else {
            last.next = new Node<>(last, value,null);
            last = last.next;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            checkIndex(index);
            if (index == 0) {
                first.prev = new Node<>(null, value, first);
                first = first.prev;
            } else {
                Node<T> newNode = new Node<>(nodeByIndex(index).prev, value, nodeByIndex(index));
                nodeByIndex(index).prev.next = newNode;
                nodeByIndex(index).prev = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t: list) {
            add(t);
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
        Node<T> node = nodeByIndex(index);
        T oldVal = node.item;
        node.item = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T element = nodeByIndex(index).item;
        unlink(nodeByIndex(index));
        return element;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> x = first; x != null; x = x.next) {
            if (object == null && x.item == null) {
                unlink(x);
                return true;
            }
            if (object != null && object.equals(x.item)) {
                unlink(x);
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    Node<T> nodeByIndex(int index) {
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

    private void unlink(Node<T> x) {
        final Node<T> next = x.next;
        final Node<T> prev = x.prev;
        if (prev == null) {
            first = x.next;
        } else {
            prev.next = next;
            x.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }
        x.item = null;
        size--;
    }
}
