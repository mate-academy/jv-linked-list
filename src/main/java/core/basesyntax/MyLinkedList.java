package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    public MyLinkedList() {
    }

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == size()) {
            linkLast(value);
        } else {
            linkBefore(value, findNode(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> wanted = findNode(index);
        return wanted.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> wanted = findNode(index);
        T oldValue = wanted.item;
        wanted.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(findNode(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> wanted = first; wanted != null; wanted = wanted.next) {
                if (wanted.item == null) {
                    unlink(wanted);
                    return true;
                }
            }
        } else {
            for (Node<T> wanted = first; wanted != null; wanted = wanted.next) {
                if (object.equals(wanted.item)) {
                    unlink(wanted);
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
