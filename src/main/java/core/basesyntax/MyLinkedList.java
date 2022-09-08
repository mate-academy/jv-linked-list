package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }

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
            Node<T> nodeOnIndex = findNodeOnIndex(index);
            Node<T> prevNodeOfFound = nodeOnIndex.prev;
            Node<T> nodeToAdd = new Node<>(prevNodeOfFound, value, nodeOnIndex);
            nodeOnIndex.prev = nodeToAdd;
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
        Node<T> currentNode = findNodeOnIndex(index);
        return currentNode.element;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = findNodeOnIndex(index);
        T oldValue = currentNode.element;
        currentNode.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = findNodeOnIndex(index);
        return unlinkNode(currentNode);
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> node = head; node != null; node = node.next) {
            if (object == null && node.element == null
                    || object != null && object.equals(node.element)) {
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

    private Node<T> findNodeOnIndex(int index) {
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

    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range");
        }
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
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
        }
        size--;
        return currentNode.element;
    }
}
