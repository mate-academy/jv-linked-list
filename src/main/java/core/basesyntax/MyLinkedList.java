package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int listSize;

    public static class Node<T> {
        private T element;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T lement, Node<T> next) {
            this.element = lement;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        if (isEmpty()) {
            linkFirstElement(value);
        } else {
            linkLastElement(value);
        }
    }

    @Override
    public void add(T value, int index) {
        checkIndexToAdd(index);
        if (index == 0) {
            linkFirstElement(value);
        } else if (index == listSize) {
            linkLastElement(value);
        } else {
            linkBeforeElement(value, getNodeByIndex(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T node : list) {
            add(node);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).element;
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
        checkIndex(index);
        Node<T> nodeByIndex = getNodeByIndex(index);
        final T removedElement = nodeByIndex.element;
        if (nodeByIndex == head) {
            head = head.next;
        } else {
            nodeByIndex.prev.next = nodeByIndex.next;
        }
        if (nodeByIndex == tail) {
            tail = tail.prev;
        } else {
            nodeByIndex.next.prev = nodeByIndex.prev;
        }
        listSize--;
        return removedElement;
    }

    @Override
    public boolean remove(T object) {
        int index = getCorrectIndex(object);
        if (index != -1) {
            remove(index);
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return listSize;
    }

    @Override
    public boolean isEmpty() {
        return listSize == 0;
    }

    private void linkFirstElement(T value) {
        Node<T> newNode = new Node<>(null, value, head);
        head = newNode;
        tail = newNode;
        listSize++;
    }

    private void linkLastElement(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        tail.next = newNode;
        tail = newNode;
        listSize++;
    }

    private void linkBeforeElement(T value, Node<T> node) {
        Node<T> prevNode = node.prev;
        Node<T> newNode = new Node<>(prevNode, value, node);
        prevNode.next = newNode;
        node.prev = newNode;
        listSize++;
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= listSize) {
            throw new IndexOutOfBoundsException("Index out of range: " + index);
        }
    }

    private void checkIndexToAdd(int index) {
        if (index < 0 || index > listSize) {
            throw new IndexOutOfBoundsException("Index out of range: " + index);
        }
    }

    private int getCorrectIndex(T element) {
        for (int i = 0; i < listSize; i++) {
            Node<T> node = getNodeByIndex(i);
            if (node.element == element || (node.element != null
                    && node.element.equals(element))) {
                return i;
            }
        }
        return -1;
    }
}
