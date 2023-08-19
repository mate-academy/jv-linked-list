package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, this.tail, null);

        if (this.size == 0) {
            this.head = newNode;
        } else {
            this.tail.next = newNode;
        }

        this.tail = newNode;
        this.size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == this.size) {
            add(value);
            return;
        }

        Node<T> node = getNodeByIndex(index);
        Node<T> newNode = new Node<>(value, node.prev, node);

        if (node.prev != null) {
            node.prev.next = newNode;
        }

        node.prev = newNode;

        if (index == 0) {
            head = newNode;
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
        Node<T> node = getNodeByIndex(index);

        return node.value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNodeByIndex(index);
        T oldValue;

        oldValue = node.value;
        node.value = value;

        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNodeByIndex(index);
        T oldValue;

        oldValue = node.value;
        updateHeadAndTail(node, index);
        unlink(node);

        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        int currentIndex = 0;
        Node<T> currentNode = this.head;

        if (this.size == 0) {
            return false;
        }

        do {
            if ((object == null)
                    ? (currentNode.value == null)
                    : (object.equals(currentNode.value))) {
                updateHeadAndTail(currentNode, currentIndex);
                unlink(currentNode);
                return true;
            }

            currentIndex++;
            currentNode = currentNode.next;
        } while (currentNode != null);

        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    private void unlink(Node<T> node) {

        if (node.prev != null) {
            node.prev.next = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        }

        this.size--;
    }

    private Node<T> getNodeByIndex(int index) {
        boolean headOrTail;

        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("Invalid element index!");
        }

        headOrTail = index <= size / 2;

        int currentIndex = (headOrTail) ? (0) : (size - 1);
        Node<T> result = (headOrTail) ? (head) : (tail);

        while (currentIndex != index) {
            result = (headOrTail) ? (result.next) : (result.prev);
            currentIndex = (headOrTail) ? (currentIndex + 1) : (currentIndex - 1);
        }

        return result;
    }

    private void updateHeadAndTail(Node<T> node, int index) {
        if (index == this.size - 1) {
            tail = node.prev;
        }

        if (index == 0) {
            head = node.next;
        }
    }

    private class Node<E> {
        private E value;
        private Node<E> prev;
        private Node<E> next;

        public Node(E value, Node<E> prev, Node<E> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
