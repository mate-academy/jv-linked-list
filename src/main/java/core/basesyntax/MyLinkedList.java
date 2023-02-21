package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head = null;
    private Node<T> tail = null;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);

        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode = new Node<>(null, value, null);

        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Illegal index value");
        }
        if (head == null) {
            head = tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else if (index == size) {
            add(value);
            size--;
        } else {
            Node<T> prevNode = nodeByIndex(index).prev;
            Node<T> nextNode = prevNode.next;
            Node<T> node = new Node<>(prevNode, value, nextNode);
            prevNode.next = node;
            nextNode.prev = node;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        indexVerification(index);
        Node<T> node = nodeByIndex(index);
        return node.value;
    }

    @Override
    public T set(T value, int index) {
        indexVerification(index);
        Node<T> node = nodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        indexVerification(index);
        return unlink(nodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> node = head; node != null; node = node.next) {
            if (node.value == object || node.value != null && node.value.equals(object)) {
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

    T unlink(Node<T> node) {
        final T element = node.value;
        Node<T> previous = node.prev;
        Node<T> after = node.next;

        if (previous == null) {
            head = after;
        } else {
            previous.next = after;
        }

        if (after == null) {
            tail = previous;
        } else {
            after.prev = previous;
            node.next = null;
        }
        node.value = null;
        size--;
        return element;
    }

    public void indexVerification(int index) {
        if (size <= index || index < 0) {
            throw new IndexOutOfBoundsException("Illegal index value");
        }
    }

    private static class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T value;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private Node<T> nodeByIndex(int index) {
        if (index < (size >> 1)) {
            Node<T> node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        } else {
            Node<T> node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
            return node;
        }
    }

}
