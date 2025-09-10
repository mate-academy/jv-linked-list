package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (tail == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            if (head != null) {
                head.prev = newNode;
            }
            head = newNode;
            if (tail == null) {
                tail = newNode;
            }
            size++;
            return;
        }
        Node<T> current = findNodeByIndex(index);
        Node<T> newNode = new Node<>(current.prev, value, current);
        current.prev.next = newNode;
        current.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> current = findNodeByIndex(index);
        return current.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> current = findNodeByIndex(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> current = findNodeByIndex(index);
        final T removedValue = current.value;
        unLink(current);
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if ((object == null && current.value == null)
                    || (object != null && object.equals(current.value))) {
                unLink(current);
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

    private Node<T> findNodeByIndex(int index) {
        checkIndex(index);
        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private void unLink(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
        node.prev = null;
        node.next = null;
        size--;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds.");
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds.");
        }
    }

    class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
