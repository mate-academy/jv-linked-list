package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Entry<T> entry;
    private int size;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public boolean add(T value) {
        Entry<T> newEntry = new Entry<>(value);
        if (size == 0) {
            entry = newEntry;
            size++;
            return true;
        }
        Entry<T> lastEntry = goToIndex(size - 1);
        lastEntry.next = newEntry;
        newEntry.prev = lastEntry;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (size == 0 || index == size) {
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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Entry<T> byIndexEntry = goToIndex(index);
        return byIndexEntry.element;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Entry<T> byIndexEntry = goToIndex(index);
        T element = byIndexEntry.element;
        byIndexEntry.element = value;
        return element;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }
        Entry<T> byIndexEntry = goToIndex(index);
        Entry<T> previousEntry = byIndexEntry.prev;
        Entry<T> nextEntry = byIndexEntry.next;
        if (index == 0) {
            if (size != 1) {
                nextEntry.prev = null;
                entry = nextEntry;
            } else {
                entry = null;
            }
            size--;
            return byIndexEntry.element;
        }
        if (index == size - 1) {
            previousEntry.next = null;
            goToTheBeginning(previousEntry);
            size--;
            return byIndexEntry.element;
        }
        previousEntry.next = nextEntry;
        nextEntry.prev = previousEntry;
        goToTheBeginning(previousEntry);
        size--;
        return byIndexEntry.element;
    }

    @Override
    public boolean remove(T t) {
        Entry<T> currentEntry = entry;
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
        Entry<T> byIndexEntry = entry;
        for (int i = 0; i < index; i++) {
            byIndexEntry = byIndexEntry.next;
        }
        return byIndexEntry;
    }

    private void goToTheBeginning(Entry<T> currentEntry) {
        while (currentEntry.prev != null) {
            currentEntry = currentEntry.prev;
        }
        entry = currentEntry;
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
