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
        if (index == 0) {
            Entry<T> newEntry = new Entry<>(value, null, first);
            first.previous = newEntry;
            first = newEntry;
        } else {
            Entry<T> entry = findEntryByIndex(index);
            Entry<T> newEntry = new Entry<>(value, entry.previous, entry);
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
        Entry<T> entry = findEntryByIndex(index);
        return entry.item;
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
        if (index == 0) {
            T element = first.item;
            removeFirstElement();
            return element;
        }
        Entry<T> entry = findEntryByIndex(index);
        removeElement(entry);
        return entry.item;
    }

    @Override
    public boolean remove(T t) {
        Entry<T> entry = first;
        for (int i = 0; i < size; i++) {
            if ((entry.item == t) || (entry.item != null && entry.item.equals(t))) {
                if (i == 0) {
                    removeFirstElement();
                    return true;
                }
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
        for (int i = 0; i < index; i++) {
            entry = entry.next;
        }
        return entry;
    }

    private void removeFirstElement() {
        if (size == 1) {
            first = null;
            last = null;
        } else {
            first.next.previous = null;
            first = first.next;
        }
        --size;
    }

    private void removeElement(Entry<T> entry) {
        if (entry.equals(last)) {
            last.previous.next = null;
            last = entry.previous;
        } else {
            entry.previous.next = entry.next;
            entry.next.previous = entry.previous;
        }
        --size;
    }

    private static class Entry<T> {
        T item;
        Entry<T> previous;
        Entry<T> next;

        public Entry(T item, Entry<T> previous, Entry<T> next) {
            this.item = item;
            this.previous = previous;
            this.next = next;
        }
    }
}
