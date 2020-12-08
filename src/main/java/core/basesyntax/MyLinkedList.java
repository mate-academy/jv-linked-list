package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private static class Node<T> {
        T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> next, T item, Node<T> prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<T> first;
    private Node<T> last;
    private int size;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public boolean add(T value) {

        return true;
    }

    @Override
    public void add(T value, int index) {

    }

    @Override
    public boolean addAll(List<T> list) {
        return false;
    }

    @Override
    public T get(int index) {
        return null;
    }

    @Override
    public T set(T value, int index) {
        return null;
    }

    @Override
    public T remove(int index) {
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
        return first == null;
    }

    private void validateIndex(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("Given index "
                    + index + " isn't within list size " + size);
        }
        if (index < 0) {
            throw new IndexOutOfBoundsException("Given index is less than 0");
        }
    }
}
