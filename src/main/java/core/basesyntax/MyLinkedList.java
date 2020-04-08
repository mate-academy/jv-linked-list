package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Entry<T> firstEntry;
    private Entry<T> lastEntry;
    private int size;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public boolean add(T value) {
        Entry<T> newEntry = new Entry<>(value);
        if (size == 0) {
            firstEntry = newEntry;
            lastEntry = newEntry;
            size++;
            return true;
        }
        Entry<T> currentEntry = goToIndex(size - 1);
        currentEntry.next = newEntry;
        newEntry.prev = currentEntry;
        lastEntry = newEntry;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Entry<T> newEntry = new Entry<>(value);
        Entry<T> byIndexEntry = goToIndex(index);
        Entry<T> previousEntry = byIndexEntry.prev;
        newEntry.next = byIndexEntry;
        newEntry.prev = previousEntry;
        previousEntry.next = newEntry;
        byIndexEntry.prev = newEntry;
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
        return true;
    }

    @Override
    public T get(int index) {
        Entry<T> byIndexEntry = goToIndex(index);
        return byIndexEntry.element;
    }

    @Override
    public T set(T value, int index) {
        Entry<T> byIndexEntry = goToIndex(index);
        T element = byIndexEntry.element;
        byIndexEntry.element = value;
        return element;
    }

    @Override
    public T remove(int index) {
        Entry<T> byIndexEntry = goToIndex(index);
        Entry<T> previousEntry = byIndexEntry.prev;
        Entry<T> nextEntry = byIndexEntry.next;
        if (size == 1) {
            firstEntry = null;
            lastEntry = null;
        }
        if (index == 0 && size > 1) {
            nextEntry.prev = null;
            firstEntry = nextEntry;
        }
        if (index == size - 1 && size > 1) {
            previousEntry.next = null;
            lastEntry = previousEntry;
        }
        if (index > 0 && index < size - 1) {
            previousEntry.next = nextEntry;
            nextEntry.prev = previousEntry;
        }
        size--;
        return byIndexEntry.element;
    }

    @Override
    public boolean remove(T t) {
        Entry<T> currentEntry = firstEntry;
        for (int i = 0; i < size; i++) {
            if (t == null && currentEntry.element == null
                    || t != null && t.equals(currentEntry.element)) {
                remove(i);
                return true;
            }
            currentEntry = currentEntry.next;
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

    private Entry<T> goToIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Entry<T> byIndexEntry;
        if (index < (size / 2)) {
            byIndexEntry = firstEntry;
            for (int i = 0; i < index; i++) {
                byIndexEntry = byIndexEntry.next;
            }
        } else {
            byIndexEntry = lastEntry;
            for (int i = size - 1; i > index; i--) {
                byIndexEntry = byIndexEntry.prev;
            }
        }

        return byIndexEntry;
    }

    private static class Entry<T> {
        T element;
        Entry<T> next;
        Entry<T> prev;

        Entry(T element) {
            this.element = element;
        }
    }
}
