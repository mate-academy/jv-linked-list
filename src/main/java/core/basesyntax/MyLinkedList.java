package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    private static class Node<T> {
        private T data;
        private Node<T> prev;
        private Node<T> next;

        Node(T data, Node<T> prev, Node<T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private void checkForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    @Override
    public void add(T value) {
        addLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkForAdd(index);
        if (index == size) {
            addLast(value);
        } else {
            Node<T> current = getNode(index);
            Node<T> newNode = new Node<>(value, current.prev, current);
            if (current.prev != null) {
                current.prev.next = newNode;
            } else {
                first = newNode;
            }
            current.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            addLast(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> current = getNode(index);
        return current.data;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> current = getNode(index);
        T oldValue = current.data;
        current.data = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> current = getNode(index);
        unlink(current);
        return current.data;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        while (current != null) {
            if ((object == null && current.data == null)
                    || (object != null && object.equals(current.data))) {
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
        return size == 0;
    }

    private void addLast(T value) {
        Node<T> newNode = new Node<>(value, last, null);
        if (last != null) {
            last.next = newNode;
        } else {
            first = newNode;
        }
        last = newNode;
        size++;
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private void unlink(Node<T> current) {
        if (current.prev != null) {
            current.prev.next = current.next;
        } else {
            first = current.next;
        }
        if (current.next != null) {
            current.next.prev = current.prev;
        } else {
            last = current.prev;
        }
        size--;
    }
}
