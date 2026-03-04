package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> tail;
    private Node<T> head;
    private int size = 0;

    @Override
    public void add(T value) {
        if (head == null && tail == null) {
            Node<T> node = new Node<>(null, value, null);

            head = node;
            tail = node;

            size++;

            return;
        }
        Node<T> node = new Node<>(tail, value, null);
        tail.next = node;
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }

        if (index == 0) {
            if (head == null) {
                add(value);
                return;
            }
            Node<T> node = new Node<>(null, value, head);
            head.prev = node;
            head = node;
            size++;
            return;
        }

        Node<T> current = findByIndex(index);
        Node<T> newNode = new Node<>(current.prev, value, current);
        current.prev.next = newNode;
        current.prev = newNode;
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
        return findByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> current = findByIndex(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> current = findByIndex(index);
        T oldValue = current.value;

        unlink(current);

        size--;
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null && !((current.value == object)
                || ((current.value != null) && current.value.equals(object)))) {
            current = current.next;
        }

        if (current != null) {
            unlink(current);
            size--;
            return true;
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

    private Node<T> findByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index is out of bound. Index: " + index + ", size: " + size);
        }
        Node<T> current;

        if (index > size / 2) {
            current = tail;
            int i = size - 1;
            while (i > index) {
                current = current.prev;
                i--;
            }
        } else {
            current = head;
            int i = 0;
            while (i < index) {
                current = current.next;
                i++;
            }
        }
        return current;
    }

    private void unlink(Node<T> node) {
        if (node.next == null && node.prev == null) {
            head = null;
            tail = null;
        } else if (node.next == null) {
            node.prev.next = null;
            tail = node.prev;
        } else if (node.prev == null) {
            node.next.prev = null;
            head = node.next;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    private class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
