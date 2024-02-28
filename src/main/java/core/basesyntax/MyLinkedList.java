package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (isEmpty()) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        validateIndex(index);

        Node<T> newNode = new Node<>(value);
        if (index == 0) {
            addToHead(newNode);
        } else {
            addByIndex(newNode, index);
        }
        size++;
    }

    private void addToHead(Node<T> newNode) {
        newNode.next = head;
        head = newNode;
    }

    private void addByIndex(Node<T> newNode, int index) {
        Node<T> prevNode = getNode(index - 1);
        newNode.next = prevNode.next;
        prevNode.next = newNode;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
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
        T oldValue = node.element;
        node.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        validateIndex(index);

        Node<T> removedNode;
        if (index == 0) {
            removedNode = head;
            head = head.next;
            if (head == null) {
                tail = null;
            }
        } else {
            Node<T> prevNode = getNode(index - 1);
            removedNode = prevNode.next;
            prevNode.next = removedNode.next;
            if (prevNode.next == null) {
                tail = prevNode;
            }
        }
        size--;
        return removedNode.element;
    }

    @Override
    public boolean remove(T object) {
        if (isEmpty()) {
            return false;
        }

        Node<T> current = head;
        Node<T> prev = null;

        while (current != null) {
            if ((object == null && current.element == null)
                    || (object != null && object.equals(current.element))) {
                unlink(current);
                size--;
                return true;
            }
            prev = current;
            current = current.next;
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

    private Node<T> getNode(int index) {
        validateIndex(index);
        Node<T> current = head;
        for (int i = 0; i < index; ++i) {
            current = current.next;
        }
        return current;
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private void unlink(Node<T> node) {
        Node<T> prev = findPreviousNode(node);

        if (prev == null) {
            head = node.next;
            if (head == null) {
                tail = null;
            }
        } else {
            prev.next = node.next;
            if (prev.next == null) {
                tail = prev;
            }
        }
    }

    private Node<T> findPreviousNode(Node<T> targetNode) {
        Node<T> current = head;
        Node<T> prev = null;

        while (current != null) {
            if (current == targetNode) {
                return prev;
            }
            prev = current;
            current = current.next;
        }
        return null;
    }

    private class Node<T> {
        private T element;
        private Node<T> next;

        public Node(T element) {
            this.element = element;
            this.next = null;
        }
    }
}
