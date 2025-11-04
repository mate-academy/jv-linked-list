package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int ZERO = 0;
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        head = null;
        tail = null;
    }

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(value, null, null);
        if (size == ZERO) {
            head = node;
        } else {
            node.prev = tail;
            tail.next = node;
        }
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == size) {
            add(value);
            return;
        }
        Node<T> currentNode = getNode(index);
        Node<T> newNode = new Node<>(value, currentNode.prev, currentNode);
        if (currentNode.prev != null) {
            currentNode.prev.next = newNode;
        } else {
            head = newNode;
        }
        currentNode.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (!list.isEmpty()) {
            for (T item : list) {
                add(item);
            }
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = getNode(index);
        unlink(node);
        size--;
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> current = head; current != null; current = current.next) {
            if ((object == null && current.value == null)
                    || (current.value != null && current.value.equals(object))) {
                unlink(current);
                size--;
                return true;
            }
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

    private void unlink(Node<T> node) {
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
    }

    private void checkIndex(int index) {
        if (index < ZERO || index >= size) {
            throw new ArrayIndexOutOfBoundsException(
                    "Index - %d is out of bounds for size - %d.".formatted(index, size));
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < ZERO || index > size) {
            throw new ArrayIndexOutOfBoundsException(
                    "Index - %d is out of bounds for size - %d.".formatted(index, size));
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> currentNode;
        if (index < (size / 2)) {
            currentNode = head;
            for (int i = ZERO; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private static class Node<T> {

        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
