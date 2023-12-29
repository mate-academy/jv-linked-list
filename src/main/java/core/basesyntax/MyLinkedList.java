package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    private static class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T value;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }
    }

    @Override
    public void add(T value) {
        Node<T> node;
        if (size == 0) {
            node = new Node<>(null, value, null);
            first = node;
            last = node;
            size++;
        } else {
            node = new Node<>(last, value,null);
            last.next = node;
            last = node;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, first);
            first.prev = newNode;
            first = newNode;
        } else {
            checkIndex(index);
            Node indexNode = findNodeByIndex(index);
            Node<T> newNode = new Node<>(indexNode.prev, value, indexNode);
            indexNode.prev.next = newNode;
            indexNode.prev = newNode;
        }
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
        checkIndex(index);
        Node<T> node = findNodeByIndex(index);
        return node.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = findNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = findNodeByIndex(index);
        unlink(node);
        size--;
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node;
        try {
            node = findNodeByValue(object);
            unlink(node);
            size--;
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void checkIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index can't be less than zero");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("Index out of range exception. "
                    + "Max index in array: " + (size - 1) + " yours: " + index);
        }
    }

    private Node<T> findNodeByIndex(int index) {
        Node node;
        if (index <= size / 2) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private Node<T> findNodeByValue(T value) {
        Node node = first;
        for (int i = 0; i < size; i++) {
            if (node.value == value || node.value != null
                    && node.value.equals(value)) {
                return node;
            }
            node = node.next;
        }
        throw new RuntimeException("There isn't node with value: " + value);
    }

    private void unlink(Node<T> node) {
        if (node.prev == null && node.next == null) {
            first = null;
            last = null;
        } else if (node.prev == null) {
            node.next.prev = null;
            first = node.next;
        } else if (node.next == null) {
            node.prev.next = null;
            last = node;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }
}
