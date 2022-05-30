package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String MESSAGE = "index out of bounds";
    private static final int DOUBLE_GO_ROUND_BOTTOM_LIMIT = 3;
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    @Override
    public void add(T value) {
        Node<T> node = (size == 0) ? new Node<>(null, value, null)
                : (tail == null) ? new Node<>(head, value, null)
                : new Node<>(tail, value, null);
        if (size == 0) {
            head = node;
        } else {
            tail = node;
            if (size == 1) {
                head.next = tail;
            } else {
                tail.prev.next = tail;
            }
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIfIndexValid(index);
        Node<T> node = getNode(index);
        Node<T> current = (index == 0) ? new Node<>(null, value, head)
                : (index == 1) ? new Node<>(head, value, node)
                : new Node<>(node.prev, value, node);
        if (index == 0) {
            head.prev = current;
            head = current;
        } else if (index == 1) {
            node.prev = current;
            head.next = current;
        } else {
            node.prev.next = current;
            node.prev = current;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            this.add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIfIndexValid(index);
        Node<T> node = getNode(index);
        return node.value;
    }

    @Override
    public T set(T value, int index) {
        checkIfIndexValid(index);
        Node<T> node = getNode(index);
        T current = node.value;
        node.value = value;
        return current;
    }

    @Override
    public T remove(int index) {
        checkIfIndexValid(index);
        Node<T> node = getNode(index);
        T value = node.value;
        unlink(node);
        return value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        while (node != null) {
            if (node.value == null ? object == null : node.value.equals(object)) {
                unlink(node);
                return true;
            }
            node = node.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    private void unlink(Node<T> node) {
        Node<T> prev = node.prev;
        Node<T> next = node.next;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            node.prev = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        node.value = null;
        size--;
    }

    private Node<T> getNode(int index) {
        int count;
        Node<T> node = (index > size / 2 && size >= DOUBLE_GO_ROUND_BOTTOM_LIMIT) ? tail : head;
        if (node == head) {
            count = 0;
            while (count != index) {
                node = node.next;
                count++;
            }
        } else {
            count = size - 1;
            while (count != index) {
                node = node.prev;
                count--;
            }
        }
        return node;
    }

    private void checkIfIndexValid(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(MESSAGE);
        }
    }

    static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.next = next;
            this.value = value;
            this.prev = prev;
        }
    }
}
