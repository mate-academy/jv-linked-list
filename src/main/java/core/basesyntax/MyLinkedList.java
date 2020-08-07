package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Entry headerEntry;
    private int size;

    public MyLinkedList() {
        this.headerEntry = new Entry(null, headerEntry, headerEntry);
        size = 0;
    }

    @Override
    public boolean add(T value) {
        Entry currentEntry = new Entry(value, null, null);

        Entry prevEntry = size == 0 ? headerEntry : headerEntry.prev;
        prevEntry.next = currentEntry;

        headerEntry.prev = currentEntry;

        currentEntry.prev = prevEntry;

        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else if (isValidIndex(index)) {
            Entry insertEntry = new Entry(value, null, null);

            Entry currentWithIndex = getEntryByIndex(index);
            currentWithIndex.prev = insertEntry;

            Entry previousEntry = getEntryByIndex(index - 1);
            previousEntry.next = insertEntry;

            insertEntry.next = currentWithIndex;
            insertEntry.prev = previousEntry;

            size++;
        }
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
        if (isValidIndex(index)) {
            Entry<T> entry = getEntryByIndex(index);
            return entry.value;
        }
        return null;
    }

    @Override
    public T set(T value, int index) {
        if (isValidIndex(index)) {
            Entry<T> entry = getEntryByIndex(index);
            T oldvalue = entry.value;
            entry.value = value;
            return oldvalue;
        }
        return null;
    }

    @Override
    public T remove(int index) {
        if (isValidIndex(index)) {
            Entry<T> entry = getEntryByIndex(index);

            Entry entryPrevious = entry.prev;
            if (null != entryPrevious) {
                entryPrevious.next = entry.next;
            }

            Entry entryNext = entry.next;
            if (entryNext != null) {
                entryNext.prev = entry.prev;
            }

            size--;
            return entry.value;
        }
        return null;
    }

    @Override
    public boolean remove(T t) {
        Entry<T> entry = headerEntry;
        for (int i = 0; i < size; i++) {
            entry = entry.next;
            if (t == entry.value || (entry.value != null && entry.value.equals(t))) {
                remove(i);
                return true;
            }
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

    private boolean isValidIndex(int index) {
        if (index < 0 || (index >= size)) {
            throw new IndexOutOfBoundsException("Index [" + index + "] is out of size: " + size);
        }
        return true;
    }

    private Entry getEntryByIndex(int index) {
        if (index == -1) {
            return headerEntry;
        }

        if (isValidIndex(index)) {
            Entry entry = headerEntry;
            for (int i = 0; i <= index; i++) {
                entry = entry.next;
            }
            return entry;
        }
        return null;
    }

    private static class Entry<T> {
        T value;
        Entry<T> next;
        Entry<T> prev;

        Entry(T value, Entry<T> next, Entry<T> prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }
}
