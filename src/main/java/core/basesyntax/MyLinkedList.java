package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;

    /**
     * Pointer to first node.
     */
    private MyLinkedList.Node<T> first;

    /**
     * Pointer to last node.
     */
    private MyLinkedList.Node<T> last;

    public MyLinkedList() {
    }

    T unlink(MyLinkedList.Node<T> x) {
        // assert x != null;
        final T element = x.item;
        final MyLinkedList.Node<T> next = x.next;
        final MyLinkedList.Node<T> prev = x.prev;

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

    @Override
    public void add(T value) {
        final MyLinkedList.Node<T> l = last;
        final MyLinkedList.Node<T> newNode = new MyLinkedList.Node<>(l, value, null);
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
        checkAddIndex(index);

        if (index == size) {
            add(value);
        } else {
            MyLinkedList.Node<T> succ = node(index);
            final MyLinkedList.Node<T> pred = succ.prev;
            final MyLinkedList.Node<T> newNode = new MyLinkedList.Node<>(pred, value, succ);
            succ.prev = newNode;
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
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return node(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        MyLinkedList.Node<T> x = node(index);
        T oldVal = x.item;
        x.item = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(node(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (MyLinkedList.Node<T> x = first; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (MyLinkedList.Node<T> x = first; x != null; x = x.next) {
                if (object.equals(x.item)) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    private void checkIndex(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException("There is no " + index + " index in LinkedList");
        }
    }

    private void checkAddIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("There is no " + index + " index in LinkedList");
        }
    }

    MyLinkedList.Node<T> node(int index) {
        if (index < (size >> 1)) {
            MyLinkedList.Node<T> x = first;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            MyLinkedList.Node<T> x = last;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }
    }

    private static class Node<E> {
        E item;
        MyLinkedList.Node<E> next;
        MyLinkedList.Node<E> prev;

        Node(MyLinkedList.Node<E> prev, E element, MyLinkedList.Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
