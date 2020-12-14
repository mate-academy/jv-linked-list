package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    private class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public boolean add(T value) {
        if (size == 0) {
            last = new Node<T>(null, value, null);
            first = last;
        } else {
            Node<T> addedElement = new Node<T>(last, value, null);
            last.next = addedElement;
            last = addedElement;
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
        isIndexOutOfBound(index);
        if (index == 0) {
            Node<T> current = new Node<>(null, value, first);
            first.prev = current;
            first = current;
        } else {
            Node<T> current = find(index);
            Node<T> added = new Node<>(current.prev, value, current);
            current.prev.next = added;
            current.prev = added;
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
        isIndexOutOfBound(index);
        return find(index).item;
    }

    @Override
    public T set(T value, int index) {
        isIndexOutOfBound(index);
        Node<T> current = find(index);
        T oldValue = current.item;
        current.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        isIndexOutOfBound(index);
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
        return size == 0;
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

    private void isIndexOutOfBound(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index is out of bound!");
        }
    }
}
