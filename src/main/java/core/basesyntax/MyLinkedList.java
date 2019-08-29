package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static class Node<T> {
        T item;
        MyLinkedList.Node<T> next;
        MyLinkedList.Node<T> prev;

        Node(MyLinkedList.Node<T> prev, T element, MyLinkedList.Node<T> next) {
            item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private int size;
    private MyLinkedList.Node<T> first;
    private MyLinkedList.Node<T> last;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public void add(T value) {
        MyLinkedList.Node<T> l = last;
        MyLinkedList.Node<T> newNode = new MyLinkedList.Node(l, value, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            MyLinkedList.Node<T> l = last;
            MyLinkedList.Node<T> newNode = new MyLinkedList.Node(l, value, null);
            last = newNode;
            if (l == null) {
                first = newNode;
            } else {
                l.next = newNode;
            }

            size++;
        } else {
            MyLinkedList.Node<T> pred = node(index).prev;
            MyLinkedList.Node<T> newNode = new MyLinkedList.Node(pred, value, node(index));
            node(index).prev = newNode;
            if (pred == null) {
                first = newNode;
            } else {
                pred.next = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return node(index).item;
    }

    @Override
    public void set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        MyLinkedList.Node<T> x = node(index);
        T oldVal = x.item;
        x.item = value;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return unlink(node(index));
    }

    @Override
    public T remove(T t) {
        MyLinkedList.Node x;
        if (t == null) {
            for (x = first; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(x);
                    return t;
                }
            }
        } else {
            for (x = first; x != null; x = x.next) {
                if (t.equals(x.item)) {
                    unlink(x);
                    return t;
                }
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private MyLinkedList.Node<T> node(int index) {
        MyLinkedList.Node x;
        int i;
        if (index < size / 2) {
            x = first;

            for (i = 0; i < index; ++i) {
                x = x.next;
            }
            return x;
        } else {
            x = last;
            for (i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }
    }

    T unlink(MyLinkedList.Node<T> x) {
        final T element = x.item;
        MyLinkedList.Node<T> next = x.next;
        MyLinkedList.Node<T> prev = x.prev;

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
}
