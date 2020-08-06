package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Entry<T> first;
    private Entry<T> last;
    private int size;

    public MyLinkedList() {
        this.size = 0;
    }

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
        } else {
            Entry<T> tempEntry = getEntry(index);
            if (tempEntry.previous == null) {
                first = new Entry<>(value, null, tempEntry);
            } else {
                tempEntry.previous.next = new Entry<>(value, tempEntry.previous, tempEntry);
            }
            size++;
        }
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
        return getEntry(index).element;
    }

    @Override
    public T set(T value, int index) {
        T oldValue = getEntry(index).element;
        getEntry(index).element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Entry<T> removedEntry = getEntry(index);
        if (removedEntry.previous == null) {
            first = removedEntry.next;
        } else {
            removedEntry.previous.next = removedEntry.next;
        }
        if (removedEntry.next == null) {
            last = removedEntry.previous;
        } else {
            removedEntry.next.previous = removedEntry.previous;
        }
        size--;
        return removedEntry.element;
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

    private Entry<T> getEntry(int index) {
        checkIndex(index);
        Entry<T> entry = first;
        for (int i = 0; i < index; i++) {
            entry = entry.next;
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
