package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private int size;
    private Entry<T> first;
    private Entry<T> last;

    @Override
    public boolean add(T value) {
        Entry<T> newEntry = new Entry<>(value, last, null);
        if (isEmpty()) {
            first = newEntry;
        } else {
            newEntry.previous.next = newEntry;
        }
        last = newEntry;
        ++size;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Incorrect index!");
        }
        if (index == size) {
            add(value);
            return;
        }
        Entry<T> entry = findEntryByIndex(index);
        Entry<T> newEntry = new Entry<>(value, entry.previous, entry);
        if (index == 0) {
            entry.previous = newEntry;
            first = newEntry;
        } else {
            entry.previous.next = newEntry;
            entry.previous = newEntry;
            if (index == size - 1) {
                newEntry.next = entry;
            } else {
                newEntry.next.previous = newEntry;
            }
        }
        ++size;
    }

    @Override
    public boolean addAll(List<T> list) {
        if (list == null || list.isEmpty()) {
            return false;
        }
        for (T t : list) {
            add(t);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return findEntryByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Entry<T> entry = findEntryByIndex(index);
        T element = entry.item;
        entry.item = value;
        return element;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Entry<T> entry = findEntryByIndex(index);
        removeElement(entry);
        return entry.item;
    }

    @Override
    public boolean remove(T t) {
        Entry<T> entry = first;
        for (int i = 0; i < size; i++) {
            if ((entry.item == t) || (entry.item != null && entry.item.equals(t))) {
                removeElement(entry);
                return true;
            }
            entry = first.next;
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
            throw new IndexOutOfBoundsException("Incorrect index!");
        }
    }

    private Entry<T> findEntryByIndex(int index) {
        Entry<T> entry = first;
        if (size - index < size / 2) {
            for (int i = 0; i < index; i++) {
                entry = entry.next;
            }
        } else {
            entry = last;
            for (int i = size - 1; i > index; i--) {
                entry = entry.previous;
            }
        }
        return entry;
    }

    private void removeElement(Entry<T> entry) {
        Entry<T> previousEntry = entry.previous;
        Entry<T> nextEntry = entry.next;
        if (entry.equals(first)) {
            if (size == 1) {
                first = null;
                last = null;
            } else {
                nextEntry.previous = null;
                first = nextEntry;
            }
        } else if (entry.equals(last)) {
            previousEntry.next = null;
            last = previousEntry;
        } else {
            previousEntry.next = nextEntry;
            nextEntry.previous = previousEntry;
        }
        --size;
    }

    private static class Entry<T> {
        T item;
        Entry<T> previous;
        Entry<T> next;

        private Entry(T item, Entry<T> previous, Entry<T> next) {
            this.item = item;
            this.previous = previous;
            this.next = next;
        }
    }
}
