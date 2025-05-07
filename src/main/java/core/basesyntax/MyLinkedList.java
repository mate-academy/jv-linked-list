package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<T>(value, tail);
        if (size == 0) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexSize(index, size);
        if (getNode(index) == null) {
            add(value);
        } else {
            Node<T> prev = getNode(index).prev;
            Node<T> newNode = new Node<>(value, prev, getNode(index));
            if (index == 0) {
                head = newNode;
            } else {
                prev.next = newNode;
                getNode(index).prev = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndexValidity(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndexValidity(index);
        Node<T> node = getNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexValidity(index);
        Node<T> node = getNode(index);
        unlink(index, node);
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (object == node.value || object != null && object.equals(node.value)) {
                index = i;
                break;
            } else {
                node = node.next;
            }
        }
        if (index == -1) {
            return false;
        }
        unlink(index, node);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void checkIndexValidity(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("The index " + index + " is invalid");
        }
    }

    private Node<T> getNode(int index) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                break;
            }
            node = node.next;
        }
        return node;
    }

    private void checkIndexSize(int index, int size) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("The index " + index + " is invalid");
        }
    }

    private void unlink(int index, Node<T> node) {
        if (size == 1) {
            head = null;
            tail = null;
        } else if (index == 0) {
            node.next.prev = null;
            head = node.next;
        } else if (index == size - 1) {
            node.prev.next = null;
            tail = node.prev;
        } else {
            Node<T> next = node.next;
            Node<T> prev = node.prev;
            next.prev = prev;
            prev.next = next;
        }
        size--;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(T val, Node<T> prev) {
            this.value = val;
            this.prev = prev;
        }

        public Node(T value, Node<T> prev, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
