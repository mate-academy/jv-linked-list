package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Header<T> node;
    private int size;

    public MyLinkedList() {
        node = new Header(null);
        node.next = node.prev = node;
        size = 0;
    }

    @Override
    public boolean add(T value) {
        Header<T> entry = new Header<>(value, node.prev, node);
        entry.next.prev = entry;
        entry.prev.next = entry;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        Header<T> newEntry = new Header<>(value, getByIndex(index).prev, getByIndex(index));
        newEntry.prev.next = newEntry;
        newEntry.next.prev = newEntry;
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
        return true;
    }

    @Override
    public T get(int index) {
        if (index == size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Header<T> entry = getByIndex(index);
        return entry.data;
    }

    @Override
    public T set(T value, int index) {
        if (index == size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Header<T> newEntry = getByIndex(index);
        T result = newEntry.data;
        newEntry.data = value;
        return result;
    }

    @Override
    public T remove(int index) {
        if (index == size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Header<T> entry = getByIndex(index);
        entry.prev.next = entry.next;
        entry.next.prev = entry.prev;
        size--;
        return entry.data;
    }

    @Override
    public boolean remove(T t) {
        int index = size;
        Header<T> entry = node.next;
        while (index != 0) {
            if ((t == null && entry.data == null) || (t != null && entry.data.equals(t))) {
                entry.prev.next = entry.next;
                entry.next.prev = entry.prev;
                entry = entry.next;
                size--;
                return true;
            }
            entry = entry.next;
            index--;
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

    private Header<T> getByIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Header<T> result = node;
        if (index < (size >> 1)) {
            for (int i = 0; i <= index; i++) {
                result = result.next;
            }
        } else {
            for (int i = size; i > index; i--) {
                result = result.prev;
            }
        }
        return result;
    }

    private class Header<T> {
        private T data;
        private Header<T> prev;
        private Header<T> next;

        public Header(T data, Header prev, Header next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }

        public Header(T data) {
            this.data = data;
        }
    }
}
