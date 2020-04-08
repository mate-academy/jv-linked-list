package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Entry first;
    private Entry last;
    private int size;

    public MyLinkedList() {
        this.first = new Entry(null, last, last);
        this.last = new Entry(null, first, first);
    }

    private static class Entry<T> {
        T element;
        Entry<T> next;
        Entry<T> prev;

        Entry(T element, Entry<T> next, Entry<T> prev) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private Entry iterator(int index) {
        Entry currentElement = first;
        for (int i = 0; i <= index; i++) {
            currentElement = currentElement.next;
            if (i == index) {
                return currentElement;
            }
        }
        return null;
    }

    @Override
    public boolean add(T value) {
        Entry newEntry = new Entry(value, last, last.prev);
        newEntry.prev.next = newEntry;
        newEntry.next.prev = newEntry;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (size == 0) {
            add(value);
            return;
        }
        Entry element = iterator(index);
        Entry newEntry = new Entry(value, element, element.prev);
        newEntry.prev.next = newEntry;
        newEntry.next.prev = newEntry;
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T element: list) {
            this.add(element);
        }
        return true;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return (T) (iterator(index).element);
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        T deletedElement = get(index);
        remove(index);
        add(value, index);
        return deletedElement;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Entry elementAtIndex = iterator(index);
        final T result = (T) elementAtIndex.element;
        elementAtIndex.prev.next = elementAtIndex.next;
        elementAtIndex.next.prev = elementAtIndex.prev;
        elementAtIndex.next = elementAtIndex.prev = null;
        elementAtIndex.element = null;
        if (size > 0) {
            size--;
        }
        return result;
    }

    @Override
    public boolean remove(T t) {
        Entry currentElement = first;
        for (int i = 0; i < size; i++) {
            currentElement = currentElement.next;
            if (t == currentElement.element || (t != null && t.equals(currentElement.element))) {
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
}
