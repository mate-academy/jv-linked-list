package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Note<T> first;
    private Note<T> last;
    private int size;
    private Note<T> boofNode;
    private Note<T> newNode;

    @Override
    public void add(T value) {
        if (size == 0) {
            linkFirst(value);
        } else {
            linkLast(value);
        }
    }

    @Override
    public void add(T value, int index) {
        if (index == 0) {
            linkFirst(value);
            return;
        }
        if (index == size) {
            linkLast(value);
            return;
        }
        isIndexOutOfBounds(index);
        Note<T> oldNote = findeIndex(index);
        newNode = new Note<>(oldNote.prev, value, oldNote);
        oldNote.prev.next = newNode;
        oldNote.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T newNode : list) {
            linkLast(newNode);
        }
    }

    @Override
    public T get(int index) {
        isIndexOutOfBounds(index);
        return findeIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        isIndexOutOfBounds(index);
        boofNode = findeIndex(index);
        T oldValue = boofNode.value;
        boofNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        isIndexOutOfBounds(index);
        boofNode = findeIndex(index);
        T removedValue = boofNode.value;
        if (index == 0) {
            first = first.next;
        } else if (index == size - 1) {
            last = last.prev;
        } else {
            boofNode.prev.next = boofNode.next;
            boofNode.next.prev = boofNode.prev;
        }
        size--;
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            boofNode = findeIndex(i);
            if (object == boofNode.value || object != null && object.equals(boofNode.value)) {
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

    private Note<T> findeIndex(int index) {
        boofNode = first;
        if (index == 0) {
            return boofNode;
        }
        int count = 1;
        while (count <= index) {
            boofNode = boofNode.next;
            count++;
        }
        return boofNode;
    }

    private void linkFirst(T value) {
        Note<T> f = first;
        newNode = new Note<>(null, value, f);
        first = newNode;
        if (f == null) {
            last = newNode;
        } else {
            f.prev = newNode;
        }
        size++;
    }

    private void linkLast(T value) {
        Note<T> l = last;
        newNode = new Note<>(l, value, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    private boolean isIndexOutOfBounds(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds!");
        } else {
            return true;
        }
    }

    private static class Note<T> {
        private T value;
        private Note<T> prev;
        private Note<T> next;

        public Note(Note<T> prev, T value, Note<T> next) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }
    }
}

