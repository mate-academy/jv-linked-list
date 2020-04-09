package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int count = 0;
    private Node<T> ourNode;

    @Override
    public boolean add(T value) {
        if (isEmpty()) {
            ourNode = new Node<>(null, value, null);
            count++;
            return true;
        }
        Node<T> current = ourNode;
        while (current.nextLinkedList != null) {
            current = current.nextLinkedList;
        }
        Node<T> next = new Node<>(null, value, current);
        current.nextLinkedList = next;
        count++;
        return true;
    }

    @Override
    public void add(T value,int index) {
        if (index < 0 || index > count) {
            throw new IndexOutOfBoundsException();
        }
        if (index == count) {
            add(value);
            return;
        }
        Node<T> current = ourNode;
        for (int i = 0; i < index; i++) {
            current = current.nextLinkedList;
        }
        Node<T> prevNode = current.prevLinkedList;
        Node<T> newNode = new Node<>(current, value, prevNode);
        current.prevLinkedList = newNode;
        prevNode.nextLinkedList = newNode;
        count++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T l: list) {
            add(l);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> current = ourNode;
        for (int i = 0; i < index; i++) {
            current = current.nextLinkedList;
        }
        return current.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> current = ourNode;
        for (int i = 0; i < index; i++) {
            current = current.nextLinkedList;
        }
        T result = (T) current.item;
        current.item = value;
        return result;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> current = ourNode;
        for (int i = 0; i < index; i++) {
            current = current.nextLinkedList;
        }
        T result = current.item;
        Node<T> before = current.prevLinkedList;
        Node<T> after = current.nextLinkedList;
        if (before != null && after != null) {
            before.nextLinkedList = after;
            after.prevLinkedList = before;
        } else if (before == null) {
            ourNode = after;
        } else if (after == null) {
            ourNode = before;
        }
        count--;
        return result;
    }

    @Override
    public boolean remove(T t) {
        Node<T> current = ourNode;
        for (int i = 0; i < count; i++) {
            T el = current.item;
            if (t == null ? el == null : el.equals(t)) {
                remove(i);
                return true;
            }
            current = current.nextLinkedList;
        }
        return false;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    public class Node<T> {
        private T item;
        private Node<T> nextLinkedList;
        private Node<T> prevLinkedList;
        private int count;

        public Node(Node<T> nextLinkedList, T value, Node<T> prevLinkedList) {
            this.item = value;
            this.nextLinkedList = nextLinkedList;
            this.prevLinkedList = prevLinkedList;
        }
    }

    public void checkIndex(int index) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException();
        }
    }
}

