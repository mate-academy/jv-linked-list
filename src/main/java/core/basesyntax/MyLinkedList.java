package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public void add(T value) {
        Node<T> item = new Node<>(value);

        if (head == null) {
            head = item;
        } else {
            tail.next = item;
            item.prev = tail;
        }

        tail = item;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }

        Node<T> oldItem = findItemByIndex(index);
        Node<T> oldItemPrev = oldItem.prev;
        Node<T> newItem = new Node<>(value);

        if (index == 0) {
            head = newItem;
        } else {
            newItem.prev = oldItemPrev;
            oldItemPrev.next = newItem;
        }

        newItem.next = oldItem;
        oldItem.prev = newItem;

        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        Node<T> item = findItemByIndex(index);

        return item.value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> item = findItemByIndex(index);
        final T oldValue = item.value;

        item.value = value;

        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> oldItem = findItemByIndex(index);
        Node<T> oldItemPrev = oldItem.prev;
        Node<T> oldItemNext = oldItem.next;

        if (oldItemNext != null) {
            oldItemNext.prev = oldItemPrev;
        } else {
            tail = oldItemPrev;
        }

        if (oldItemPrev != null) {
            oldItemPrev.next = oldItemNext;
        } else {
            head = oldItemNext;
        }

        size--;
        return oldItem.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> item = head;

        for (int i = 0; i < size; i++) {
            if (item.value == object || (object != null && object.equals(item.value))) {
                remove(i);
                return true;
            }
            item = item.next;
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

    private Node<T> findItemByIndex(int index) {
        if (index > size - 1 || index < 0) {
            throw new IndexOutOfBoundsException("Can't find element by index " + index);
        }

        Node<T> item = head;
        int currentIndex = 0;

        while (currentIndex != index) {
            item = item.next;
            currentIndex++;
        }

        return item;
    }

    private static class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T value;

        public Node(T value) {
            this.prev = null;
            this.next = null;
            this.value = value;
        }
    }
}
