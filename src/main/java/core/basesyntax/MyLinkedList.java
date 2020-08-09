package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private int size;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public boolean add(T value) {
        linkLast(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, node(index));
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        return addAll(list, size);
    }

    public boolean addAll(List<T> list, int index) {
        Object[] a = list.toArray();
        int numNew = a.length;
        if (numNew == 0) {
            return false;
        }

        Node<T> pred;
        Node<T> succ;
        if (index == size) {
            succ = null;
            pred = last;
        } else {
            succ = node(index);
            pred = succ.prev;
        }

        for (Object o : a) {
            Node<T> newNode = new Node<T>(pred, (T) o, null);
            if (pred == null) {
                first = newNode;
            } else {
                pred.next = newNode;
            }
            pred = newNode;
        }

        if (succ == null) {
            last = pred;
        } else {
            pred.next = succ;
            succ.prev = pred;
        }

        size += numNew;
        return true;
    }

    @Override
    public T get(int index) {
        return node(index).item;
    }

    @Override
    public T set(T element, int index) {
        Node<T> x = node(index);
        T oldVal = x.item;
        x.item = element;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        Node<T> node = node(index);
        unlink(node);
        return node.item;
    }

    @Override
    public boolean remove(T t) {
        if (t == null) {
            for (Node<T> x = first; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<T> x = first; x != null; x = x.next) {
                if (t.equals(x.item)) {
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

    private Node<T> node(int index) {
        checkPositionIndex(index);

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

    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index)) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private boolean isPositionIndex(int index) {
        return (index >= 0 && index < size);
    }

    private void unlink(Node<T> x) {
        Node<T> next = x.next;
        Node<T> prev = x.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        size--;
    }

    private void linkLast(T e) {
        Node<T> l = last;
        Node<T> newNode = new Node<T>(l, e, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    private void linkBefore(T t, Node<T> succ) {
        Node<T> pred = succ.prev;
        Node<T> newNode = new Node<T>(pred, t, succ);
        succ.prev = newNode;
        if (pred == null) {
            first = newNode;
        } else {
            pred.next = newNode;
        }
        size++;
    }

    private static class Node<T> {
        T item;
        Node<T> prev;
        Node<T> next;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.prev = prev;
            this.next = next;
        }
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }
}
