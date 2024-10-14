package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head = null;
    private Node<T> tail = null;

    @Override
    public void add(T value) {
        Node<T> node;
        node = new Node<>(null, value,null);
        if (head == null) {
            head = node;
        } else {
            tail.next = node;
            node.prev = tail;
        }
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        throwIndexOutOfBoundsAdd(index);
        if (index == size) {
            add(value);
            return;
        }
        Node<T> current = findNodeByIndex(index);
        Node<T> newNode = new Node<>(current.prev, value, current);
        current.prev = (current.prev != null) ? current.prev.next = newNode : (head = newNode);
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        Node<T> current = findNodeByIndex(index);
        return current.item;
    }

    @Override
    public T set(T value, int index) {
        throwIndexOutOfBounds(index);
        Node<T> current = findNodeByIndex(index);
        T oldValue = current.item;
        if (index == 0) {
            head.item = value;
        } else {
            current.item = value;
        }
        return oldValue;
    }

    @Override
    public T remove(int index) {
        throwIndexOutOfBounds(index);
        Node<T> current = findNodeByIndex(index);
        return unlike(current);
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if (object == null) {
                if (current.item == null) {
                    unlike(current);
                    return true;
                }
            } else {
                if (object.equals(current.item)) {
                    unlike(current);
                    return true;
                }
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

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }

        public Node(T item) {
            this.item = item;
        }
    }

    private void throwIndexOutOfBoundsAdd(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds.");
        }
    }

    private void throwIndexOutOfBounds(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds.");
        }
    }

    private Node<T> findNodeByIndex(int index) {
        throwIndexOutOfBounds(index);
        Node<T> node;
        if (index < size / 2) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private T unlike(Node<T> node) {
        final T oldValue = node.item;
        if (node == head) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node == tail) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        size--;
        return oldValue;
    }
}
