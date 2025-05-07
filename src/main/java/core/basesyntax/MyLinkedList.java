package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static final class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(T item) {
            this.item = item;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
            size++;
        } else {
            addLast(newNode);
        }
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        validateIndex(index, size + 1);
        Node<T> next = getNode(index);
        Node<T> newNode = new Node<>(value);
        if (next.prev == null) {
            next.prev = newNode;
            head = newNode;
            newNode.next = next;
        } else {
            next.prev.next = newNode;
            newNode.prev = next.prev;
            newNode.next = next;
            next.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list != null) {
            for (T node : list) {
                add(node);
            }
        }
    }

    @Override
    public T get(int index) {
        Node<T> node = getNode(index);
        return node.item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> nodeByIndex = getNode(index);
        T oldValue = nodeByIndex.item;
        nodeByIndex.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNode(index);
        return unlink(node);
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        int index = 0;
        while (node != null) {
            if (node.item == object || node.item != null && node.item.equals(object)) {
                unlink(getNode(index));
                return true;
            }
            node = node.next;
            index++;
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

    private void validateIndex(int index, int size) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index " + index + " for size " + size);
        }
    }

    private void addLast(Node<T> newNode) {
        newNode.prev = tail;
        tail.next = newNode;
        tail = newNode;
        size++;
    }

    private Node<T> getNode(int index) {
        validateIndex(index, size);
        Node<T> node;
        if (index < size / 2) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size; i > (index + 1); i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private T unlink(Node<T> node) {
        size--;
        if (node == head) {
            if (!isEmpty()) {
                head.next.prev = null;
                head = head.next;
            } else {
                head = null;
                tail = null;
            }
        } else if (node == tail) {
            tail.prev.next = null;
            tail = tail.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        return node.item;
    }
}
