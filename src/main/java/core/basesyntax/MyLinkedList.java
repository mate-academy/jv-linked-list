package core.basesyntax;

import java.util.LinkedList;
import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    transient Node<T> first;
    transient Node<T> last;
    private int size;

    private static class Node <T>{
        T item;
        Node<T> next;
        Node<T> prev;

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
        indexCheck(index);
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, node(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkPositionIndex(index);
        MyLinkedList.Node<T> x = node(index);
        return x.item;
    }

    @Override
    public T set(T value, int index) {
        indexCheck(index);
        if (index == size) {
            throw new IndexOutOfBoundsException();
        }
        MyLinkedList.Node<T> x = node(index);
        if (x != null) {
            T oldVal = x.item;
            x.item = value;
            return oldVal;
        } else {
             add(value);
             return value;
        }
    }

    @Override
    public T remove(int index) {
        checkPositionIndex(index);
        MyLinkedList.Node<T> x = node(index);
        return unlink(x);
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
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    MyLinkedList.Node<T> node(int index) {
        if (index < (size >> 1)) {
            MyLinkedList.Node<T> x = first;
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
        } else {
            MyLinkedList.Node<T> x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
    }

    private void linkLast(T t) {
        final MyLinkedList.Node<T> l = last;
        final MyLinkedList.Node<T> newNode = new MyLinkedList.Node<>(l, t, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        size++;
    }

    private void linkBefore(T t, MyLinkedList.Node<T> x) {
        final MyLinkedList.Node<T> pred = x.prev;
        final MyLinkedList.Node<T> newNode = new MyLinkedList.Node<>(pred, t, x);
        x.prev = newNode;
        if (pred == null)
            first = newNode;
        else
            pred.next = newNode;
        size++;
    }

    private void indexCheck(int index) {
        if ((index < 0 || index > size)) {
            throw new IndexOutOfBoundsException("Non existed position "
                    + index + " when size is: " + size);
        }
    }

    private void checkPositionIndex(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException("Non existed position "
                    + index + " when size is: " + size);
        }
    }

    T unlink(Node<T> x) {
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
}
