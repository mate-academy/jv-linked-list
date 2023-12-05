package core.basesyntax;

import java.util.LinkedList;
import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    Node<T> first;
    Node<T> last;
    private int size;

    private static class Node <T>{
        T item;
        Node<T> next;
        Node<T> prev;

        public Node(Node<T> next, T item, Node<T> prev) {
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
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        indexCheck(index);
        return null;
    }

    @Override
    public T set(T value, int index) {
        indexCheck(index);
        return null;
    }

    @Override
    public T remove(int index) {
        indexCheck(index);
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
    private void indexCheck(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException("Non existed position "
                    + index + " when size is: " + size);
        }
    }
}
