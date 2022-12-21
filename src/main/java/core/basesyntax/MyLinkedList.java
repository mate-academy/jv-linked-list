package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private ListNode<T> head;
    private ListNode<T> tail;
    private int size;

    @Override
    public void add(T value) {
        ListNode<T> node = tail;
        ListNode<T> newNode = new ListNode<>(node, value, null);
        tail = newNode;
        if (head == null) {
            head = newNode;
        } else {
            node.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        ListNode<T> node = getNode(index);
        ListNode<T> newNode = new ListNode<>(node.prev, value, node);
        if (node.prev != null) {
            node.prev.next = newNode;
        } else {
            head = newNode;
        }
        node.prev = newNode;
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
        checkIndex(index);
        return getNode(index).data;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        ListNode<T> node = getNode(index);
        T oldData = node.data;
        node.data = value;
        return oldData;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        size--;
        ListNode<T> node = getNode(index);
        if (size == 0 && index == 0) {
            head = null;
            tail = null;
        } else if (index == 0) {
            head = head.next;
            head.prev = null;
        } else if (index == size) {
            tail = tail.prev;
            tail.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        return node.data;
    }

    @Override
    public boolean remove(T object) {
        ListNode<T> node = head;
        for (int i = 0; i < size; i++) {
            if (node.data == null && object == null
                    || node.data != null && node.data.equals(object)) {
                remove(i);
                return true;
            }
            node = node.next;
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

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds");
        }
    }

    private ListNode<T> getNode(int index) {
        ListNode<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    public static class ListNode<T> {
        private T data;
        private ListNode<T> next;
        private ListNode<T> prev;

        ListNode(ListNode<T> prev, T data, ListNode<T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }
}
