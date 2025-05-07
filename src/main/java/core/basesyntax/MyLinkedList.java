package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (head == null) {
            addHead(value);
        } else {
            addTail(value);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            Node<T> currentNode = getNodeByIndex(index);
            Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
            if (currentNode.prev != null) {
                currentNode.prev.next = newNode;
            } else {
                head = newNode;
            }
            currentNode.prev = newNode;
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
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNodeByIndex(index);
        return unlink(node);
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> removedNode = head; removedNode != null; removedNode = removedNode.next) {
            if (areValuesEqual(removedNode.value, object)) {
                unlink(removedNode);
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
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
    }

    private boolean areValuesEqual(T first, T second) {
        return first == second || first != null && first.equals(second);
    }

    private void addHead(T value) {
        Node<T> newNode = new Node<>(value);
        head = newNode;
        tail = newNode;
    }

    private void addTail(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        tail.next = newNode;
        tail = newNode;
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        Node<T> node;
        int pointerIndex;
        if (index < size / 2) {
            node = head;
            pointerIndex = 0;
            while (pointerIndex < index) {
                node = node.next;
                pointerIndex++;
            }
        } else {
            node = tail;
            pointerIndex = size - 1;
            while (pointerIndex > index) {
                node = node.prev;
                pointerIndex--;
            }
        }
        return node;
    }

    private T unlink(Node<T> node) {
        final T value = node.value;
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
        return value;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }

        public Node(T value) {
            this.value = value;
        }
    }
}
