package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private int size = 0;
    private Node<T> first;
    private Node<T> last;

    @Override
    public boolean add(T value) {
        linkLast(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        checkElementIndex(index);
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, node(index));
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        return addAll(size, list);
    }

    private boolean addAll(int index, List<T> c) {
        checkElementIndex(index);
        Object[] a = c.toArray();
        int lengthList = a.length;
        if (lengthList == 0) {
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
            T e = (T) o;
            Node<T> newNode = new Node<>(pred, e, null);
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
        size += lengthList;
        return true;
    }

    @Override
    public T get(int index) {
        checkPositionIndex(index);
        return node(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkPositionIndex(index);
        Node<T> x = node(index);
        T oldVal = x.item;
        x.item = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        checkPositionIndex(index);
        return unlink(node(index));
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

    private void checkElementIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(index + " is out of bound!");
        }
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(index + " is out of bound!");
        }
    }

    private void linkLast(T e) {
        final Node<T> l = last;
        final Node<T> newNode = new Node<>(l, e, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    private void linkBefore(T e, Node<T> node) {
        if (node != null) {
            final Node<T> previous = node.prev;
            final Node<T> newNode = new Node<>(previous, e, node);
            node.prev = newNode;
            if (previous == null) {
                first = newNode;
            } else {
                previous.next = newNode;
            }
            size++;
        }
    }

    private Node<T> node(int index) {
        checkElementIndex(index);
        if (index >= 0 && index < size / 2) {
            Node<T> node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        } else {
            Node<T> node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
            return node;
        }
    }

    private T unlink(Node<T> x) {
        if (x != null) {
            final T element = x.item;
            final Node<T> next = x.next;
            final Node<T> prev = x.prev;
            if (prev == null) {
                first = next;
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
            return element;
        }
        return null;
    }

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
}

