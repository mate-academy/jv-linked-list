package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Entry<T> head;
    private Entry<T> tail;
    private int size;

    @Override
    public boolean add(T value) {
        Entry<T> currentEntry = new Entry<>(value, null, null);

        if (size == 0) {
            head = currentEntry;
            tail = currentEntry;
        } else {
            Entry<T> prevEntry = tail;
            prevEntry.next = currentEntry;
            currentEntry.prev = prevEntry;
            tail = currentEntry;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        } else if (index == 0) {
            Entry<T> insertEntry = new Entry<>(value, head, null);
            head.prev = insertEntry;
            head = insertEntry;
            size++;
            return;
        }
        checkIndex(index);
        Entry<T> insertEntry = new Entry<>(value, null, null);
        Entry<T> currentWithIndex = getEntryByIndex(index);
        insertEntry.prev = currentWithIndex.prev;
        currentWithIndex.prev.next = insertEntry;
        currentWithIndex.prev = insertEntry;
        insertEntry.next = currentWithIndex;
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
        checkIndex(index);
        Entry<T> entry = getEntryByIndex(index);
        return entry.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Entry<T> entry = getEntryByIndex(index);
        T oldvalue = entry.value;
        entry.value = value;
        return oldvalue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Entry<T> entry = getEntryByIndex(index);
        unlink(entry);
        return entry.value;
    }

    @Override
    public boolean remove(T t) {
        Entry<T> entry = head;
        for (int i = 0; i < size; i++) {
            if (t == entry.value || (entry.value != null && entry.value.equals(t))) {
                unlink(entry);
                return true;
            }
            entry = entry.next;
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
        if (index < 0 || (index >= size)) {
            throw new IndexOutOfBoundsException("Index [" + index + "] is out of size: " + size);
        }
    }

    private Entry<T> getEntryByIndex(int index) {
        checkIndex(index);
        Entry<T> entry;
        if (index < size / 2) {
            entry = head;
            for (int i = 1; i <= index; i++) {
                entry = entry.next;
            }
        } else {
            entry = tail;
            for (int i = size - 2; i >= index; i--) {
                entry = entry.prev;
            }
        }
        return entry;
    }

    private void unlink(Entry<T> entry) {
        Entry<T> entryPrevious = entry.prev;
        if (entryPrevious != null) {
            entryPrevious.next = entry.next;
        } else {
            head = entry.next;
        }

        Entry<T> entryNext = entry.next;
        if (entryNext != null) {
            entryNext.prev = entry.prev;
        } else {
            tail = entry.prev;
        }
        size--;
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
