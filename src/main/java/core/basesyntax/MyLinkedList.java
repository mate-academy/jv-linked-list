package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T element;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        tail = new Node<>(tail, value, null);
        if (isEmpty()) {
            head = tail;
        } else {
            tail.prev.next = tail;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode;
        if (index == size) {
            add(value);
            return;
        }
        validateIndex(index);
        Node<T> next = getNode(index);
        Node<T> prev = next.prev;
        newNode = new Node<>(prev, value, next);
        if (prev == null) {
            head = newNode;
        } else {
            prev.next = newNode;
        }
        next.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T listElement: list) {
            add(listElement);
        }
    }

    @Override
    public T get(int index) {
        validateIndex(index);
        return getNode(index).element;
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index);
        Node<T> node = getNode(index);
        T element = node.element;
        node.element = value;
        return element;
    }

    @Override
    public T remove(int index) {
        T element;
        validateIndex(index);
        Node<T> oldNode = getNode(index);
        element = oldNode.element;
        unLink(oldNode);
        size--;
        return element;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        while (node != null) {
            if ((node.element == object)
                    || (node.element != null) && (node.element.equals(object))) {
                unLink(node);
                size--;
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

    private void unLink(Node<T> oldNode) {
        if ((oldNode == head) && (oldNode == tail)) {
            head = null;
            tail = null;
            return;
        }
        if (oldNode == head) {
            oldNode.next.prev = null;
            head = oldNode.next;
            return;
        }
        if (oldNode == tail) {
            oldNode.prev.next = null;
            tail = oldNode.prev;
            return;
        }
        oldNode.prev.next = oldNode.next;
        oldNode.next.prev = oldNode.prev;
    }

    private Node<T> getNode(int index) {
        Node<T> node;
        if (index == 0) {
            return head;
        }
        if (index == size - 1) {
            return tail;
        }
        if (index > size / 2) {
            node = tail;
            for (int i = 0; i < size - index; i++) {
                node = node.prev;
            }
            return node.next;
        } else {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        }
    }

    void validateIndex(int index) {
        if ((index >= size) || (index < 0)) {
            throw new IndexOutOfBoundsException("Can't process element - index bigger than size");
        }
    }
}
