package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (last == null) {
            first = newNode;
        } else {
            newNode.prev = last;
            last.next = newNode;
        }
        last = newNode;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        if (index == size()) {
            add(value);
            return;
        }
        Node<T> newNode = new Node<>(value);
        Node<T> current = getNode(index);
        Node<T> prev = current.prev;

        newNode.prev = prev;
        newNode.next = current;
        current.prev = newNode;
        if (prev == null) {
            first = newNode;
        } else {
            prev.next = newNode;
        }

    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        Node<T> node = getNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        Node<T> node = getNode(index);
        Node<T> prev = node.prev;
        Node<T> next = node.next;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            node.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        while (current != null) {
            if (current.value == null && object == null
                    || current.value != null && current.value.equals(object)) {
                Node<T> prev = current.prev;
                Node<T> next = current.next;

                if (prev == null) {
                    first = next;
                } else {
                    prev.next = next;
                    current.prev = null;
                }
                if (next == null) {
                    last = prev;
                } else {
                    next.prev = prev;
                    current.next = null;
                }
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        int size = 0;
        Node<T> current = first;
        while (current != null) {
            size++;
            current = current.next;
        }
        return size;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        Node<T> current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Node<T> getPrev() {
            return prev;
        }

        public void setPrev(Node<T> prev) {
            this.prev = prev;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }
    }
}
