package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public boolean add(T value) {
        if (size == 0) {
            last = new Node<>(null, value, null);
            first = last;
        } else {
            Node<T> addElement = new Node<>(last, value, null);
            last.next = addElement;
            last = addElement;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        indexOutOfBoundsException(index);
        if (index == 0) {
            Node<T> newNode = new Node<>(null, value, first);
            first.prev = newNode;
            first = newNode;
        } else {
            Node<T> current = find(index);
            Node<T> addIndex = new Node<>(null, value, current);
            current.prev.next = addIndex;
            current.prev = addIndex;
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
        indexOutOfBoundsException(index);
        return find(index).item;
    }

    @Override
    public T set(T value, int index) {
        indexOutOfBoundsException(index);
        Node<T> current = find(index);
        T oldValue = current.item;
        current.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        indexOutOfBoundsException(index);
        Node<T> removed = find(index);
        final T result = removed.item;
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
        return result;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        for (int i = 0; i < size; i++) {
            if (object == current.item || object != null && object.equals(current.item)) {
                remove(i);
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

    private class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    private void indexOutOfBoundsException(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounce");
        }
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
