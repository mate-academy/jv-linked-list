package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Noda<T> head;
    private Noda<T> tail;

    public MyLinkedList() {
        head = new Noda<>(null, null, null);
        tail = head;
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            head.value = value;
            size++;
            return;
        }
        tail = new Noda<>(value, null, tail);
        tail.prev.next = tail;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            head = new Noda<>(value, head, null);
            head.next.prev = head;
            size++;
            return;
        }
        Noda<T> current = findNoda(index);
        Noda<T> added = new Noda<>(value, current, current.prev);
        current.prev = added;
        added.prev.next = added;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return findNoda(index).value;
    }

    @Override
    public void set(T value, int index) {
        checkIndex(index);
        findNoda(index).value = value;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Noda<T> current = findNoda(index);
        if (current == head) {
            head = head.next;
            if (head != null) {
                head.prev = null;
            }
            size--;
            return current.value;
        }
        if (current == tail) {
            tail = tail.prev;
            tail.next = null;
            current.prev = null;
            size--;
            return current.value;
        }
        current.prev.next = current.next;
        current.next.next = current.prev;
        current.prev = null;
        current.next = null;
        size--;
        return current.value;
    }

    @Override
    public T remove(T t) {
        Noda<T> current = head;
        for (int i = 0; i < size; i++) {
            if (t == current.value || t != null && t.equals(current.value)) {
                if (current == head) {
                    head = head.next;
                    if (head != null) {
                        head.prev = null;
                    }
                    size--;
                    return current.value;
                }
                if (current == tail) {
                    tail = tail.prev;
                    tail.next = null;
                    current.prev = null;
                    size--;
                    return current.value;
                }
                current.prev.next = current.next;
                current.next.prev = current.prev;
                current.prev = null;
                current.next = null;
                size--;
                return current.value;
            }
            current = current.next;
        }
        return null;
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
            throw new IndexOutOfBoundsException();
        }
    }

    private Noda<T> findNoda(int index) {
        Noda<T> current;
        if (index > size / 2) {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        } else {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        }
        return current;
    }

    private class Noda<T> {
        private T value;
        private Noda<T> next;
        private Noda<T> prev;

        private Noda(T value, Noda<T> next, Noda<T> prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }
}
