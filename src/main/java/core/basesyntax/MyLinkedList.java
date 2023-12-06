package core.basesyntax;

import java.util.List;
import java.lang.IndexOutOfBoundsException;

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
        if (index == size)
            linkLast(value);
        else
            linkBefore(value, node(index));
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
        return null;
    }

    @Override
    public boolean remove(T object) {
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

    void linkBefore(T e, MyLinkedList.Node<T> succ) {
        final MyLinkedList.Node<T> pred = succ.prev;
        final MyLinkedList.Node<T> newNode = new MyLinkedList.Node<>(pred, e, succ);
        succ.prev = newNode;
        if (pred == null)
            first = newNode;
        else
            pred.next = newNode;
        size++;
    }

    private void linkFirst(T t) {
        final MyLinkedList.Node<T> f = first;
        final MyLinkedList.Node<T> newNode = new MyLinkedList.Node<>(null, t, f);
        first = newNode;
        if (f == null)
            last = newNode;
        else
            f.prev = newNode;
        size++;
    }

    private void indexCheck(int index) {
        if ((index < 0 || index >= size)) {
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
}
