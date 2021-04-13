package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public boolean add(T value) {
        addTail(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            addTail(value);
            return;
        }
        checkIndex(index);
        addLast(value, index);
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return find(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> current = find(index);
        T oldValue = current.item;
        current.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> removed = find(index);
        final T result = removed.item;
        unlink(removed);
        return result;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        for (int i = 0; i < size; i++) {
            if (object == current.item || object != null && object.equals(current.item)) {
                unlink(current);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(T item) {
            this.item = item;
        }

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounce");
        }
    }

    private void addTail(T value) {
        if (size == 0) {
            last = new Node<>(value);
            first = last;
        } else {
            Node<T> addElement = new Node<>(last, value, null);
            last.next = addElement;
            last = addElement;
        }
        size++;
    }

    private void addLast(T value, int index) {
        Node<T> current = find(index);
        Node<T> newNode = new Node<>(current.prev, value, current);
        if (index == 0) {
            first = newNode;
        } else {
            current.prev.next = newNode;
        }
        current.prev = newNode;
        size++;
    }

    private void unlink(Node<T> removed) {
        if (removed.next == null) {
            last = removed.prev;
        } else {
            removed.next.prev = removed.prev;
        }
        if (removed.prev == null) {
            first = removed.next;
        } else {
            removed.prev.next = removed.next;
        }
        size--;
    }

    private Node<T> find(int index) {
        Node<T> current;
        int count = 0;
        if (index <= size / 2) {
            current = first;
            while (count < index) {
                current = current.next;
                count++;
            }
        } else {
            current = last;
            count = size - 1;
            while (count > index) {
                current = current.prev;
                count--;
            }
        }
        return current;
    }
}
