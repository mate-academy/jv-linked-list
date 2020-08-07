package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Entry<T> head;
    private Entry<T> tail;
    private int size;

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public boolean add(T value) {
        Entry<T> currentEntry = new Entry<T>(value, null, null);

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
        } else if (index == 0) {
            Entry<T> insertEntry = new Entry<T>(value, head, null);
            head = insertEntry;
            size++;
        } else if (isValidIndex(index)) {
            Entry<T> insertEntry = new Entry<T>(value, null, null);

            Entry<T> currentWithIndex = getEntryByIndex(index);
            if (currentWithIndex != null) {
                currentWithIndex.prev = insertEntry;
            }

            Entry<T> previousEntry = getEntryByIndex(index - 1);
            if (previousEntry != null) {
                previousEntry.next = insertEntry;
            }

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
            if (entry != null) {
                return entry.value;
            }
        }
        return null;
    }

    @Override
    public T set(T value, int index) {
        if (isValidIndex(index)) {
            Entry<T> entry = getEntryByIndex(index);
            if (entry != null) {
                T oldvalue = entry.value;
                entry.value = value;
                return oldvalue;
            }
        }
        return null;
    }

    @Override
    public T remove(int index) {
        if (isValidIndex(index)) {
            Entry<T> entry = getEntryByIndex(index);
            if (entry != null) {
                Entry<T> entryPrevious = entry.prev;
                if (entryPrevious != null) {
                    entryPrevious.next = entry.next;
                } else {
                    head = entry.next;
                }

                Entry<T> entryNext = entry.next;
                if (entryNext != null) {
                    entryNext.prev = entry.prev;
                }

                size--;
                return entry.value;
            }
        }
        return null;
    }

    @Override
    public boolean remove(T t) {
        Entry<T> entry = head;
        for (int i = 0; i < size; i++) {
            if (t == entry.value || (entry.value != null && entry.value.equals(t))) {
                remove(i);
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

    private boolean isValidIndex(int index) {
        if (index < 0 || (index >= size)) {
            throw new IndexOutOfBoundsException("Index [" + index + "] is out of size: " + size);
        }
        return true;
    }

    private Entry<T> getEntryByIndex(int index) {
        if (isValidIndex(index)) {
            Entry<T> entry = head;
            for (int i = 1; i <= index; i++) {
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
