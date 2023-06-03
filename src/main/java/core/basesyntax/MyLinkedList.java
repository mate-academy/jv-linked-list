package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            linkLast(value);
        } else {
            checkIndex(index);
            linkBefore(value, getNodeByIndex(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            linkLast(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> node = head; node != null; node = node.next) {
            if (node.value == object || object != null && object.equals(node.value)) {
                unlink(node);
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Incorrect index! List`s size: "
                    + size + ". You input: " + index);
        }
    }

    private void linkLast(T value) {
        Node<T> newTail = tail;
        Node<T> newNode = new Node<>(newTail, value, null);
        tail = newNode;
        if (newTail == null) {
            head = newNode;
        } else {
            newTail.next = newNode;
        }
        size++;
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        if (index < (size / 2)) {
            Node<T> firstNode = head;
            for (int i = 0; i < index; i++) {
                firstNode = firstNode.next;
            }
            return firstNode;
        } else {
            Node<T> lastNode = tail;
            for (int i = size - 1; i > index; i--) {
                lastNode = lastNode.prev;
            }
            return lastNode;
        }
    }

    private void linkBefore(T value, Node<T> beforeElement) {
        Node<T> prev = beforeElement.prev;
        Node<T> newNode = new Node<>(prev, value, beforeElement);
        beforeElement.prev = newNode;
        if (prev == null) {
            head = newNode;
        } else {
            prev.next = newNode;
        }
        size++;
    }

    private T unlink(Node<T> node) {
        final T element = node.value;
        Node<T> next = node.next;
        Node<T> prev = node.prev;

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
        return element;
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
