package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    static class Node<E> {
        private Node<E> prev;
        private E value;
        private Node<E> next;

        public Node(Node<E> prev, E value, Node<E> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    private void checkIndex(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException(
                    "Index must be: 0 <= index < size for size = " + size
            );
        }
    }

    private Node<T> getNodeAtIndex(int index) {
        checkIndex(index);
        Node<T> currentNode = head;
        int currentIndex = 0;
        while (currentIndex < index) {
            currentNode = currentNode.next;
            currentIndex++;
        }
        return currentNode;
    }

    private void unlink(Node<T> node) {
        node.next.prev = node.prev;
        node.prev.next = node.next;
    }

    private void addToHead(T value) {
        Node<T> newNode = new Node<>(null, value, head);
        head.prev = newNode;
        head = newNode;
        size++;
    }

    private void addToMiddle(T value, int index) {
        Node<T> nodeAtIndex = getNodeAtIndex(index - 1);
        Node<T> insertNode = new Node<>(nodeAtIndex, value, nodeAtIndex.next);
        nodeAtIndex.next.prev = insertNode;
        nodeAtIndex.next = insertNode;
        size++;
    }

    @Override
    public void add(T value) {
        if (!isEmpty()) {
            Node<T> tempNext = new Node<>(tail, value, null);
            tail.next = tempNext;
            tail = tempNext;
            size++;
        }
        if (isEmpty()) {
            head = new Node<>(null, value, null);
            tail = head;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        if (size == 0 && index == 0) {
            add(value);
        } else if (index == 0) {
            addToHead(value);
        } else if (index == size) {
            add(value);
        } else {
            addToMiddle(value, index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        return getNodeAtIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        T oldValue;
        Node<T> nodeAtIndex = getNodeAtIndex(index);
        oldValue = nodeAtIndex.value;
        nodeAtIndex.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeAtIndex = getNodeAtIndex(index);
        if (index == 0) {
            head = head.next;
        }
        if (index > 0 && index < size - 1) {
            unlink(nodeAtIndex);
        } else {
            tail = tail.prev;
        }
        size--;
        return nodeAtIndex.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (object == currentNode.value
                    || object != null && currentNode.value.equals(object)) {
                remove(i);
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }
}

