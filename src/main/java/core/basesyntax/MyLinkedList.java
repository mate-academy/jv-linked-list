package core.basesyntax;

import java.util.Collection;
import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    public MyLinkedList(Collection<? extends T> col) {
        addAll((List<T>) col);
    }

    public MyLinkedList() {
    }

    @Override
    public void add(T value) {
        if (isFirst()) {
            addFirst(value);
            return;
        }
        addLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        if (index == 0) {
            addFirst(value);
        } else if (index == size) {
            addLast(value);
        } else {
            addInMiddle(value, findNode(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    private void addFirst(T value) {
        Node<T> f = first;
        Node<T> newNode = new Node<>(null, value, f);
        first = newNode;
        if (f == null) {
            last = newNode;
        } else {
            f.prev = newNode;
        }
        size++;
    }

    private void addLast(T value) {
        final Node<T> l = last;
        Node<T> newNode = new Node<T>(l, value, null);
        last = newNode;
        l.next = newNode;
        size++;
    }

    private void addInMiddle(T value, Node<T> successor) {
        Node<T> predecessor = successor.prev;
        Node<T> newNode = new Node<>(predecessor, value, successor);
        successor.prev = newNode;
        if (predecessor == null) {
            first = newNode;
        } else {
            predecessor.next = newNode;
        }
        size++;
    }

    @Override
    public T get(int index) {
        checkIndexNotEqualsSize(index);
        return findNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndexNotEqualsSize(index);
        T oldValue = findNode(index).item;
        findNode(index).item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexNotEqualsSize(index);
        final T oldValue = findNode(index).item;
        Node<T> nextFromRemoved = findNode(index).next;
        Node<T> prevFromRemoved = findNode(index).prev;
        if (last == findNode(index) && size != 1) {
            prevFromRemoved.next = null;
            last = prevFromRemoved;
        } else if (first == findNode(index) && size != 1) {
            nextFromRemoved.prev = null;
            first = nextFromRemoved;
        } else if (size != 1) {
            nextFromRemoved.prev = prevFromRemoved;
            prevFromRemoved.next = nextFromRemoved;
        }
        size--;
        sizeChecker();
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i <= size; i++) {
            if (findNode(i).item == object || findNode(i).item != null
                    && findNode(i).item.equals(object)) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    private void sizeChecker() {
        if (size == 0) {
            first = null;
            last = null;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private boolean isFirst() {
        return size == 0;
    }

    private void checkIndex(int index) {
        if (!(index >= 0 && index <= size)) {
            throw new IndexOutOfBoundsException("Invalid index: " + index + ", with size: " + size);
        }
    }

    private void checkIndexNotEqualsSize(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException("Invalid index: " + index + ", with size: " + size);
        }
    }

    private Node<T> findNode(int index) {
        if (index < (size / 2)) {
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

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}
