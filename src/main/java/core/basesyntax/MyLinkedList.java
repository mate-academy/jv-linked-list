package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        if (head != null) {
            Node<T> newTail = new Node(value, tail, null);
            tail.next = newTail;
            tail = newTail;
        } else {
            head = new Node(value, null, null);
            tail = head;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            this.add(value);
        } else {
            Node<T> nextNode = getNodeElementByIndex(index);
            Node<T> previous = nextNode.previous;
            Node<T> newNode = new Node(value, previous, nextNode);
            nextNode.previous = newNode;
            if (previous == null) {
                head = newNode;
            } else {
                previous.next = newNode;
            }
            size++;
        }
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
        return getNodeElementByIndex(index).data;
    }

    @Override
    public void set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNodeElementByIndex(index);
        node.data = value;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return processNode(getNodeElementByIndex(index));
    }

    @Override
    public T remove(T t) {
        for (Node<T> node = head; node != null; node = node.next) {
            if (node.data.equals(t)) {
                return processNode(node);
            }
        }
        return null;
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
            throw new IndexOutOfBoundsException();
        }
    }

    private T processNode(Node<T> node) {
        final T value = node.data;
        final Node<T> next = node.next;
        final Node<T> previous = node.previous;
        if (previous == null) {
            head = next;
        } else {
            previous.next = next;
            node.previous = null;
        }
        if (next == null) {
            tail = previous;
        } else {
            next.previous = previous;
            node.next = null;
        }
        node.data = null;
        size--;
        return value;
    }

    private Node<T> getNodeElementByIndex(int index) {
        Node<T> element;
        if (index < (size / 2)) {
            element = head;
            for (int i = 0; i < index; i++) {
                element = element.next;
            }
        } else {
            element = tail;
            for (int i = size - 1; i > index; i--) {
                element = element.previous;
            }
        }
        return element;
    }

    private static class Node<T> {
        private T data;
        private Node<T> next;
        private Node<T> previous;

        public Node(T data, Node<T> previous, Node<T> next) {
            this.data = data;
            this.previous = previous;
            this.next = next;
        }
    }
}
