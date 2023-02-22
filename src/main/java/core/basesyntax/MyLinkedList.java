package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode;
        if (head == null) {
            newNode = new Node<>(null, value, null);
            head = tail = newNode;
        } else {
            newNode = new Node<>(this.tail, value, null);
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, size);
        Node<T> newNode;
        if (head == null) {
            newNode = new Node<>(null, value, null);
            head = tail = newNode;
        } else if (index == 0) {
            newNode = new Node<>(null, value, head);
            head = newNode;
            newNode.next.prev = newNode;
        } else if (index == size) {
            add(value);
            return;
        } else {
            Node<T> current = getNode(index - 1, size);
            newNode = new Node<>(current.prev, value, current.next);
            newNode.next.prev = newNode;
            current.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            return;
        }
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index, size - 1).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index, size - 1);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> removedNode = getNode(index, size - 1);
        unlink(removedNode);
        return removedNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if (current.value == object || current.value != null && current.value.equals(object)) {
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
        return head == null;
    }

    private Node<T> getNode(int index, int length) {
        checkIndex(index, length);
        Node<T> current;
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

    private void unlink(Node<T> node) {
        if (node.prev == null) {
            head = head.next;
        } else if (node.next == null) {
            tail = node.prev;
            tail.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }

    private void checkIndex(int index, int length) {
        if (index < 0 || index > length) {
            throw new IndexOutOfBoundsException("Wrong index " + index);
        }
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
}
