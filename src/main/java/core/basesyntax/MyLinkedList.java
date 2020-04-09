package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int count = 0;
    private Node<T> headNode;

    @Override
    public boolean add(T value) {
        if (isEmpty()) {
            headNode = new Node<>(null, value, null);
            count++;
            return true;
        }
        Node<T> current = headNode;
        while (current.next != null) {
            current = current.next;
        }
        Node<T> next = new Node<>(null, value, current);
        current.next = next;
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
        Node<T> current = findNode(index);
        Node<T> prevNode = current.prev;
        Node<T> newNode = new Node<>(current, value, prevNode);
        current.prev = newNode;
        prevNode.next = newNode;
        count++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T element: list) {
            add(element);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> current = findNode(index);
        return current.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> current = findNode(index);
        T result = (T) current.item;
        current.item = value;
        return result;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> current = findNode(index);
        T result = current.item;
        Node<T> before = current.prev;
        Node<T> after = current.next;
        if (before != null && after != null) {
            before.next = after;
            after.prev = before;
        } else if (before == null) {
            headNode = after;
        }
        count--;
        return result;
    }

    @Override
    public boolean remove(T t) {
        Node<T> current = headNode;
        for (int i = 0; i < count; i++) {
            T el = current.item;
            if (t == null ? el == null : el.equals(t)) {
                remove(i);
                return true;
            }
            current = current.next;
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

    private class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> nextLinkedList, T value, Node<T> prevLinkedList) {
            this.item = value;
            this.next = nextLinkedList;
            this.prev = prevLinkedList;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node<T> findNode(int index) {
        Node<T> current = headNode;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }
}

