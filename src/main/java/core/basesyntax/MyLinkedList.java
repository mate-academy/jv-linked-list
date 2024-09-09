package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        linkLast(value);
        size++;
    }

    @Override
    public void add(T value, int index) {
        indexCheck(index);
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, findNodeByIndex(index));
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        indexCheck(index);
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        indexCheck(index);
        T oldValue = findNodeByIndex(index).item;
        findNodeByIndex(index).item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        T removedElement = findNodeByIndex(index).item;
        unlink(findNodeByIndex(index));
        size--;
        return removedElement;
    }

    @Override
    public boolean remove(T object) {
        Node<T> loopObject = first;
        while (loopObject != null) {
            if (areEquals(object, loopObject.item)) {
                unlink(loopObject);
                size--;
                return true;
            }
            loopObject = loopObject.next;
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

    private Node<T> findNodeByIndex(int index) {
        indexCheckToFindNodeByIndexMethod(index);
        Node<T> newNode = first;
        for (int i = 0; i < index; i++) {
            newNode = newNode.next;
        }
        return newNode;
    }

    private void indexCheck(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bound: " + index);
        }
    }

    private void indexCheckToFindNodeByIndexMethod(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bound: " + index);
        }
    }

    private void unlink(Node<T> current) {
        if (current.prev != null) {
            current.prev.next = current.next;
        } else {
            first = current.next;
        }

        if (current.next != null) {
            current.next.prev = current.prev;
        } else {
            last = current.prev;
        }
    }

    private void linkBefore(T t, Node<T> succ) {
        if (succ == null) {
            throw new RuntimeException("Successor node cant not be null");
        }

        Node<T> antec = succ.prev;
        Node<T> newNode = new Node<>(antec, t, succ);
        succ.prev = newNode;
        if (antec == null) {
            first = newNode;
        } else {
            antec.next = newNode;
            newNode.prev = antec;
        }
    }

    private void linkLast(T t) {
        Node<T> l = last;
        Node<T> newNode = new Node<>(last, t, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
    }

    private boolean areEquals(T a, T b) {
        if (a == null) {
            return b == null;
        }
        return a.equals(b);
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev,T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
