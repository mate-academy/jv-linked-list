package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> tail;
    private Node<T> head;
    private int size;

    @Override
    public void add(T value) {
        linkTail(value);
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            linkTail(value);
        } else if (index == 0) {
            linkHead(value);
        } else {
            Node<T> nodeBeforeIndex = getNodeByIndex(index - 1);
            Node<T> newNode = new Node<>(nodeBeforeIndex, value, nodeBeforeIndex.next);
            nodeBeforeIndex.next.prev = newNode;
            nodeBeforeIndex.next = newNode;
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
        Node<T> node = getNodeByIndex(index);
        return node.element;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.element;
        node.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeToRemove = getNodeByIndex(index);
        unlink(nodeToRemove);
        return nodeToRemove.element;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            Node<T> nextNode = current.next;
            if (object == null ? current.element == null : object.equals(current.element)) {
                unlink(current);
                return true;
            }
            current = nextNode;
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
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        Node<T> indexNode = head;
        int halfOfSize = size / 2;
        if (halfOfSize < index) {
            for (int i = 0; i < index; i++) {
                indexNode = indexNode.next;
            }
        } else {
            indexNode = tail;
            for (int i = size - 1; i > index; i--) {
                indexNode = indexNode.prev;
            }
        }
        return indexNode;
    }

    private void linkHead(T value) {
        Node<T> newNode = new Node<>(null, value, head);
        head.prev = newNode;
        head = newNode;
        size++;
    }

    private void linkTail(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (isEmpty()) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    private void unlink(Node<T> node) {
        if (node == null) {
            return;
        }
        Node<T> prev = node.prev;
        Node<T> next = node.next;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
        }
        size--;
    }

    private static class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }
    }
}
