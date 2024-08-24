package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int HALF_DIVIDER_INDEX = 2;
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, findNodeByIndex(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            linkLast(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndexForAccess(index);
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndexForAccess(index);
        Node<T> x = findNodeByIndex(index);
        T oldValue = x.item;
        x.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexForAccess(index);
        return unlink(findNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        if (indexOf(object) >= 0) {
            unlink(findNodeByIndex(indexOf(object)));
            return true;
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

    public int indexOf(T object) {
        int index = 0;
        for (Node<T> x = first; x != null; x = x.next) {
            if ((size != 0) && (x.item == object || (x.item != null && x.item.equals(object)))) {
                return index;
            }
            index++;
        }
        return -1;
    }

    private T unlink(Node<T> element) {
        final T item = element.item;
        final Node<T> prev = element.prev;
        final Node<T> next = element.next;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            element.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            element.next = null;
        }
        element.item = null;
        size--;
        return item;
    }

    private void linkLast(T value) {
        final Node<T> l = last;
        final Node<T> newNode = new Node<>(l, value, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    private void linkBefore(T value, Node<T> succ) {
        final Node<T> pred = succ.prev;
        final Node<T> newNode = new Node<>(pred, value, succ);
        succ.prev = newNode;
        if (pred == null) {
            first = newNode;
        } else {
            pred.next = newNode;
        }
        size++;
    }

    private Node<T> findNodeByIndex(int index) {
        if (index < (size / HALF_DIVIDER_INDEX)) {
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

    private void checkIndexForAdd(int index) {
        if (!(index >= 0 && index <= size)) {
            throw new IndexOutOfBoundsException("Index is incorrect " + index);
        }
    }

    private void checkIndexForAccess(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException("Index is incorrect " + index);
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.item = element;
            this.next = next;
        }
    }
}
