package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> tail;
    private Node<T> head;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail,value,null);
        if (tail != null) {
            tail.next = newNode;
        } else {
            head = newNode;
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
        Node<T> current = getNode(index);
        Node<T> newNode = new Node<>(current.prev, value, current);
        if (current.prev != null) {
            current.prev.next = newNode;
        } else {
            head = newNode;
        }
        current.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value: list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = getNode(index);
        return unlink(node);
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if ((object == null && current.value == null)
                    || (object != null && object.equals(current.value))) {
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

    private static class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + "; size: " + size);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + "; size: " + size);
        }
    }

    private Node<T> getNode(int index) {
        if (index < (size / 2)) {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else {
            Node<T> current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
            return current;
        }
    }

    private T unlink(Node<T> node) {
        Node<T> next = node.next;
        Node<T> prev = node.prev;
        if (prev != null) {
            prev.next = next;
        } else {
            head = next;
        }
        if (next != null) {
            next.prev = prev;
        } else {
            tail = prev;
        }
        size--;
        T value = node.value;
        return value;
    }
}
