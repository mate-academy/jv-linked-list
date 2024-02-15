package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
        this.first = null;
        this.last = null;
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

    public void linkFirst(T t) {
        Node<T> f = first;
        Node<T> newNode = new Node<>(null, t, first);
        first = newNode;
        if (f == null) {
            last = newNode;
        } else {
            f.prev = newNode;
        }
        size++;
    }

    public void linkLast(T t) {
        if (last == null) {
            Node<T> newNode = new Node<>(null, t, null);
            first = newNode;
            last = newNode;
        } else {
            Node<T> newNode = new Node<>(last, t, null);
            last.next = newNode;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Wrong index " + index);
        }
        if (index == size) {
            linkLast(value);
        } else if (index == 0) {
            linkFirst(value);
        } else {
            Node<T> pred = getNode(index - 1);
            Node<T> succ = pred.next;
            Node<T> newNode = new Node<>(pred, value, succ);
            pred.next = newNode;
            succ.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    private Node<T> getNode(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Wrong index " + index);
        }
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

    @Override
    public T get(int index) {
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNode(index);
        final T oldValue = node.item;
        removeNode(node);
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            Node<T> x = first;
            while (x != null) {
                if (x.item == null) {
                    removeNode(x);
                    return true;
                }
                x = x.next;
            }
        } else {
            Node<T> x = first;
            int index = 0;
            while (x != null) {
                if (object.equals(x.item)) {
                    remove(index);
                    return true;
                }
                x = x.next;
                index++;
            }
        }
        return false;
    }

    private void removeNode(Node<T> node) {
        if (node.prev == null) {
            first = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node.next == null) {
            last = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        node.prev = null;
        node.next = null;
        node.item = null;
        size--;
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
