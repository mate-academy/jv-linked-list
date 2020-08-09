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
        addToTail(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            addToTail(value);
            return;
        }
        if (index == 0) {
            addToHead(value);
        } else {
            addToBottom(value, index);
        }
        size += 1;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> temp = getNodeByIndex(index);
        T oldItem = temp.item;
        temp.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> temp = getNodeByIndex(index);
        removeNode(temp);
        return temp.item;
    }

    @Override
    public boolean remove(T t) {
        Node<T> temp = head;
        for (int i = 0; i < size; i++) {
            if (t != null ? t.equals(temp.item) : temp.item == null) {
                return removeNode(temp);
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
        return size == 0;
    }

    private void addToHead(T value) {
        Node<T> temp = head;
        Node<T> newNode = new Node<>(null, value, temp);
        temp.prev = newNode;
        head = newNode;
    }

    private void addToTail(T value) {
        Node<T> t = tail;
        Node<T> newNode = new Node<>(t, value, null);
        tail = newNode;
        if (t == null) {
            head = newNode;
        } else {
            t.next = newNode;
        }
        size += 1;
    }

    private void addToBottom(T value, int index) {
        Node<T> temp = getNodeByIndex(index);
        Node<T> newNode = new Node<>(temp.prev, value, temp);
        temp.prev.next = newNode;
        temp.prev = newNode;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> temp = head;
        int i = 0;
        while (temp != null && i != index) {
            temp = temp.next;
            i++;
        }
        return temp;
    }

    private boolean removeNode(Node<T> node) {
        Node<T> prev = node.prev;
        Node<T> next = node.next;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }

        size -= 1;
        return true;
    }

    private static class Node<T> {
        private Node<T> prev;
        private T item;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
