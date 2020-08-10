package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Entry<T> first;
    private Entry<T> last;
    private int size;

    @Override
    public boolean add(T value) {
        Entry<T> tempLast = last;
        last = new Entry<>(value, tempLast, null);
        if (tempLast == null) {
            first = last;
        } else {
            tempLast.next = last;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of range!");
        }
        if (size == index) {
            add(value);
            return;
        }
        Entry<T> tempEntry = getEntryByIndex(index);
        if (tempEntry.previous == null) {
            first = new Entry<>(value, null, tempEntry);
        } else {
            Entry<T> currentEntry = new Entry<>(value, tempEntry.previous, tempEntry);
            tempEntry.previous.next = currentEntry;
            tempEntry.previous = currentEntry;
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        if (list.size() == 0) {
            return false;
        }
        for (T element : list) {
            add(element);
        }
        return true;
    }

    @Override
    public T get(int index) {
        return getEntryByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        Entry<T> currentEntry = getEntryByIndex(index);
        T oldValue = currentEntry.element;
        currentEntry.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Entry<T> entryToRemove = getEntryByIndex(index);
        unlink(entryToRemove);
        return entryToRemove.element;
    }

    @Override
    public boolean remove(T t) {
        Entry<T> tempEntry = first;
        for (int i = 0; i < size; i++) {
            if (tempEntry.element == t || tempEntry.element.equals(t)) {
                remove(i);
                return true;
            }
            tempEntry = tempEntry.next;
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

    private void unlink(Entry<T> entryToRemove) {
        if (entryToRemove.previous == null) {
            first = entryToRemove.next;
        } else {
            entryToRemove.previous.next = entryToRemove.next;
        }
        if (entryToRemove.next == null) {
            last = entryToRemove.previous;
        } else {
            entryToRemove.next.previous = entryToRemove.previous;
        }
        size--;
    }

    private Entry<T> getEntryByIndex(int index) {
        checkIndex(index);
        Entry<T> entry;
        if (index < size >> 1) {
            entry = first;
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range!");
        }
    }

    private static class Entry<T> {
        T element;
        Entry<T> next;
        Entry<T> previous;

        Entry(T element, Entry<T> previous, Entry<T> next) {
            this.element = element;
            this.next = next;
            this.previous = previous;
        }
    }
}
