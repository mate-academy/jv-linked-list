package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int OFFSET_ONE = 1;
    private static final int EMPTY_LIST = 0;
    private int size;
    private Node<T> first;
    private Node<T> last;

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        public Node(Node<E> prev, E item, Node<E> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkInsertIndex(index);
        if (index == size) {
            linkLast(value);
            return;
        }
        Node<T> newNode = new Node<>(null, value, null);
        if (index == EMPTY_LIST) {
            newNode.next = first;
            first = newNode;
            size++;
            return;
        }
        Node<T> temp = first;
        for (int i = 0; i < index - OFFSET_ONE; i++) {
            temp = temp.next;
        }
        temp.next.prev = newNode;
        newNode.next = temp.next;
        newNode.prev = temp;
        temp.next = newNode;

        size++;
    }

    @Override
    public void addAll(List<T> list) {
        T[] listToArray = (T[]) list.toArray();
        for (T values : listToArray) {
            linkLast(values);
        }
    }

    @Override
    public T get(int index) {
        checkAccessIndex(index);
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkAccessIndex(index);
        final T initialItem;
        if (index == EMPTY_LIST) {
            initialItem = first.item;
            first.item = value;
            return initialItem;
        }
        if (index == size - OFFSET_ONE) {
            initialItem = last.item;
            last.item = value;
            return initialItem;
        }
        Node<T> newNode = new Node<>(null, value, null);
        Node<T> temp = findNodeByIndex(index);

        temp.next.prev = newNode;
        temp.prev.next = newNode;
        newNode.prev = temp.prev;
        newNode.next = temp.next;

        return temp.item;
    }

    @Override
    public T remove(int index) {
        checkAccessIndex(index);
        if (index == EMPTY_LIST) {
            return removeFirst();
        }
        if (index == size - OFFSET_ONE) {
            return removeLast();
        }
        Node<T> temp = findNodeByIndex(index);
        unlink(temp);
        return temp.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> temp = first;
        while (temp != null) {
            if (object != null && temp.equals(first) && object.equals(first.item)) {
                removeFirst();
                return true;
            }
            if (object != null && temp.equals(last) && object.equals(last.item)) {
                removeLast();
                return true;
            }
            if (Objects.equals(temp.item, object)) {
                unlink(temp);
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == EMPTY_LIST;
    }

    private void linkLast(T value) {
        Node<T> l = last;
        Node<T> newNode = new Node<>(l, value, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> temp = first;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        return temp;
    }

    private T removeFirst() {
        final T initialItem = first.item;
        if (first.next == null) {
            first = null;
            last = null;
            size--;
            return initialItem;
        }
        first = first.next;
        size--;
        return initialItem;
    }

    private T removeLast() {
        final T initialItem = last.item;
        last.prev.next = null;
        last = last.prev;
        size--;
        return initialItem;
    }

    private void unlink(Node<T> temp) {
        temp.next.prev = temp.prev;
        temp.prev.next = temp.next;
        size--;
    }

    private void checkInsertIndex(int index) {
        if (index > size || index < EMPTY_LIST) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    private void checkAccessIndex(int index) {
        if (index >= size || index < EMPTY_LIST) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }
}
