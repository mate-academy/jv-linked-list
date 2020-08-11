package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private int size;
    private Entry<T> first;
    private Entry<T> last;

    @Override
    public boolean add(T value) {
        addInTheEnd(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        checkExeption(index);
        if (index == size) {
            addInTheEnd(value);
            return;
        }
        Entry<T> entryAtIndex = findByIndex(index);
        if (entryAtIndex.prev == null) {
            first = new Entry<>(value, entryAtIndex, null);
        } else {
            Entry<T> newNode = new Entry<T>(value, entryAtIndex, entryAtIndex.prev);
            newNode.prev.next = newNode;
            entryAtIndex.prev = newNode;
        }
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
        Entry<T> element = findByIndex(index);
        return element.element;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Entry<T> oldElement = findByIndex(index);
        T oldValue = oldElement.element;
        oldElement.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return deleteLink(index).element;
    }

    @Override
    public boolean remove(T t) {
        Entry<T> removeElement = first;
        for (int i = 0; i < size; i++) {
            if (removeElement.element == t
                    || removeElement.element != null && removeElement.element.equals(t)) {
                deleteLink(i);
                return true;
            }
            removeElement = removeElement.next;
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

    private static class Entry<T> {
        T element;
        Entry<T> next;
        Entry<T> prev;

        public Entry(T element, Entry<T> next, Entry<T> prev) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private void checkExeption(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void addInTheEnd(T value) {
        Entry<T> newEntry = new Entry<>(value, null, last);
        if (size == 0) {
            first = newEntry;
        } else {
            newEntry.prev.next = newEntry;
        }
        last = newEntry;
        size++;
    }

    private Entry<T> findByIndex(int index) {
        Entry<T> entryToFind;
        if (size / 2 >= index) {
            entryToFind = first;
            for (int i = 0; i < index; i++) {
                entryToFind = entryToFind.next;
            }
        } else {
            entryToFind = last;
            for (int i = 0; i < size - index - 1; i++) {
                entryToFind = entryToFind.prev;
            }
        }
        return entryToFind;
    }

    private Entry<T> deleteLink(int index) {
        Entry<T> removeElement = findByIndex(index);
        Entry<T> previousToRemove = removeElement.prev;
        Entry<T> nextToRemove = removeElement.next;
        if (previousToRemove == null) {
            first = nextToRemove;
        } else {
            previousToRemove.next = nextToRemove;
        }
        if (nextToRemove == null) {
            last = previousToRemove;
        } else {
            nextToRemove.prev = previousToRemove;
        }
        size--;
        return removeElement;
    }
}
