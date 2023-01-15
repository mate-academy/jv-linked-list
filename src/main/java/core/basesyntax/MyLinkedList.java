package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (tail == null) {
            head = tail = new Node<>(value, null, null);
            size++;
            return;
        }

        Node<T> newNode = new Node(value, tail, null);
        tail = tail.next = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }

        checkIndex(index);

        Node<T> movedNode = getNode(index);
        Node<T> newNode = new Node(value, movedNode.prev, movedNode);
        if (movedNode.prev != null) {
            movedNode.prev.next = newNode;
        } else {
            head = newNode;
        }
        movedNode.prev = newNode;

        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T listValue: list) {
            add(listValue);
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
        return unlink(getNode(index));
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
        return size == 0;
    }

    private static class Node<T> {
        private Node<T> next;
        private T value;
        private Node<T> prev;

        Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    private boolean isFromTailCount(int index) {
        return index >= size / 2;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(
              "index: " + index + " is more than list size: " + size + "."
            );
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> node;
        if (isFromTailCount(index)) {
            node = tail;
            for (int i = size - 2; i >= index; --i) {
                node = node.prev;
            }
        } else {
            node = head;
            for (int i = 1; i <= index; i++) {
                node = node.next;
            }
        }

        return node;
    }

    private T unlink(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
        size--;

        return node.value;
    }
}
