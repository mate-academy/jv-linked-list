package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public boolean add(T value) {
        if (size == 0) {
            head = new Node<>(null, value, null);
            tail = head;
            size++;
            return true;
        }
        Node<T> localNode = new Node<>(tail, value, null);
        tail.next = localNode;
        tail = localNode;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException("Incorrect index");
        }
        if (size == 0 || size == index) {
            add(value);
            return;
        }
        if (index == 0) {
            head = new Node<>(null, value, head);
            size++;
            return;
        }
        Node<T> localHead = head;
        int count = 0;
        while (count != index) {
            count++;
            localHead = localHead.next;
        }
        Node<T> localNode = new Node<>(localHead.prev, value, localHead);
        localHead.prev.next = localNode;
        localHead.prev = localNode;
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        if (index < (size >> 1)) {
            Node<T> localHead = head;
            for (int i = 0; i < index; i++) {
                localHead = localHead.next;
            }
            return localHead.item;
        }
        Node<T> localTail = tail;
        for (int i = 0; i < size - index - 1; i++) {
            localTail = localTail.prev;
        }
        return localTail.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> localHead = head;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                T prevValue = localHead.item;
                localHead.item = value;
                return prevValue;
            }
            localHead = localHead.next;
        }
        return null;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> localHead = head;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                T value = localHead.item;
                unlink(localHead);
                return value;
            }
            localHead = localHead.next;
        }
        return null;
    }

    @Override
    public boolean remove(T object) {
        Node<T> localHead = head;
        for (int i = 0; i < size; i++) {
            if (localHead.item == object || (localHead.item != null
                    && localHead.item.equals(object))) {
                unlink(localHead);
                return true;
            }
            localHead = localHead.next;
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

    private void unlink(Node<T> node) {
        if (size == 1) {
            head = null;
            tail = null;
        } else if (node == head) {
            head = head.next;
            head.prev = null;
        } else if (node == tail) {
            tail = tail.prev;
            tail.next = null;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
        size--;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Incorrect index");
        }
    }

    private class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}
