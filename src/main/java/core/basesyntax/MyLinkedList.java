package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        final Node<T> newNode;
        if (tail == null) {
            newNode = new Node<>(null, value, null);
            head = newNode;
        } else {
            newNode = new Node<>(tail, value, null);
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            validateIndex(index);
            Node<T> nodeByIndex = findNodeByIndex(index);
            Node<T> prevNodeOfFound = nodeByIndex.prev;
            Node<T> nodeToAdd = new Node<>(prevNodeOfFound, value, nodeByIndex);
            nodeByIndex.prev = nodeToAdd;
            if (prevNodeOfFound == null) {
                head = nodeToAdd;
            } else {
                prevNodeOfFound.next = nodeToAdd;
            }
            size++;
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
        return findNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = findNodeByIndex(index);
        T oldValue = currentNode.element;
        currentNode.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = findNodeByIndex(index);
        return unlinkNode(currentNode);
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> node = head; node != null; node = node.next) {
            if (Objects.equals(object, node.element)) {
                unlinkNode(node);
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

    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index out of range,"
                    + " shoult be  0<index<=size," + " but was " + index);
        }

    }

    private Node<T> findNodeByIndex(int index) {
        validateIndex(index);
        Node<T> node;
        if (index < size / 2) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = 0; i < size - index - 1; i++) {
                node = node.prev;
            }
        }
        return node;
    }

    private T unlinkNode(Node<T> currentNode) {
        if (size == 1) {
            head = null;
            tail = null;
        } else if (currentNode.prev == null) {
            head = currentNode.next;
            head.prev = null;
        } else if (currentNode.next == null) {
            tail = currentNode.prev;
            tail.next = null;
        } else {
            currentNode.next.prev = currentNode.prev;
            currentNode.prev.next = currentNode.next;
        }
        size--;
        return currentNode.element;
    }

    private static class Node<T> {
        private Node<T> next;
        private Node<T> prev;
        private T element;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.next = next;
            this.prev = prev;
            this.element = element;
        }
    }
}
