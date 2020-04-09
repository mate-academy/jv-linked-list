package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Entry<T> first;
    private Entry<T> last;
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

    private Entry getByIndex(int index) {
        if (index <= size / 2) {
            Entry currentElement = first;
            for (int i = 0; i <= index; i++) {
                currentElement = currentElement.next;
                if (i == index) {
                    return currentElement;
                }
            }
        } else {
            Entry currentElement = last;
            for (int i = (size - 1); i >= index; i--) {
                currentElement = currentElement.prev;
                if (i == index) {
                    return currentElement;
                }
            }
        }
        return null;
    }

    @Override
    public boolean add(T value) {
        Entry<T> newEntry = new Entry<>(value, last, last.prev);
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
        if (size == index) {
            add(value);
            return;
        }
        Entry element = getByIndex(index);
        Entry<T> newEntry = new Entry<>(value, element, element.prev);
        newEntry.prev.next = newEntry;
        newEntry.next.prev = newEntry;
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T element: list) {
            add(element);
        }
        return true;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return (T) (getByIndex(index).element);
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
        Entry elementAtIndex = getByIndex(index);
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
