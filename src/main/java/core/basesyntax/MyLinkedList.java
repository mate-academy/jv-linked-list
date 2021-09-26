package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, node(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        Object[] a = list.toArray();
        int numNew = a.length;
        if (numNew == 0) {
            return;
        }
        Node<T> pred;
        pred = tail;
        for (Object o : a) {
            T e = (T) o;
            Node<T> newNode = new Node<>(pred, e, null);
            if (pred == null) {
                head = newNode;
            } else {
                pred.next = newNode;
            }
            pred = newNode;
        }
        tail = pred;
        size += numNew;
    }

    @Override
    public T get(int index) {
        if (index < 0) {
            checkIndex(index);
        } else {
            checkIndex(index + 1);
        }
        return node(index).item;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0) {
            checkIndex(index);
        } else {
            checkIndex(index + 1);
        }
        Node<T> x = node(index);
        T oldVal = x.item;
        x.item = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        if (index < 0) {
            checkIndex(index);
        } else {
            checkIndex(index + 1);
        }
        return unlink(node(index));
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

    private void linkLast(T value) {
        final Node<T> t = tail;
        final Node<T> newNode = new Node<>(t,value,null);
        tail = newNode;
        if (t == null) {
            head = newNode;
        } else {
            t.next = newNode;
        }
        size++;
    }

    void linkBefore(T e, Node<T> succ) {
        final Node<T> pred = succ.prev;
        final Node<T> newNode = new Node<T>(pred, e, succ);
        succ.prev = newNode;
        if (pred == null) {
            head = newNode;
        } else {
            pred.next = newNode;
        }
        size++;
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    Node<T> node(int index) {
        if (index < (size >> 1)) {
            Node<T> x = head;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            Node<T> x = tail;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
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
}
