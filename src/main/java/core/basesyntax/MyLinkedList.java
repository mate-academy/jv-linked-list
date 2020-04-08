package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Header<T> header;
    private int size;

    public MyLinkedList() {
        this.header = new Header(null);
        header.next = header.prev = header;
        this.size = 0;
    }

    @Override
    public boolean add(T value) {
        Header<T> entry = new Header<>(value, header.prev, header);
        entry.next.prev = entry;
        entry.prev.next = entry;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        Header<T> newEntry = new Header<>(value, entry(index).prev, entry(index));
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
        Header<T> entry = entry(index);
        return entry.data;
    }

    @Override
    public T set(T value, int index) {
        if (index == size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Header<T> newEntry = entry(index);
        T result = newEntry.data;
        newEntry.prev.next.data = value;
        return result;
    }

    @Override
    public T remove(int index) {
        if (index == size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Header<T> entry = entry(index);
        entry.prev.next = entry.next;
        entry.next.prev = entry.prev;
        size--;
        return entry.data;
    }

    @Override
    public boolean remove(T t) {
        int index = size;
        Header<T> entry = header.next;
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

    private Header<T> entry(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Header<T> e = header;
        if (index < (size >> 1)) {
            for (int i = 0; i <= index; i++) {
                e = e.next;
            }
        } else {
            for (int i = size; i > index; i--) {
                e = e.prev;
            }
        }
        return e;
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
