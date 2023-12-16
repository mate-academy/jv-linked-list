package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        tail = new Node<>(tail, value, null);
        if (head == null) {
            head = tail;
        } else {
            tail.prev.next = tail;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        exceptionCheck(index);
        if (index < size) {
            Node<T> nodeByIndex = getNodeByIndex(index);
            Node<T> newNode = new Node<>(nodeByIndex.prev, value, nodeByIndex);
            if (nodeByIndex.prev == null) {
                head = newNode;
            } else {
                nodeByIndex.prev.next = newNode;
            }
            nodeByIndex.prev = newNode;
            size++;
        } else {
            add(value);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> nodeToSet = getNodeByIndex(index);
        T valueToSet = nodeToSet.value;
        nodeToSet.value = value;
        return valueToSet;
    }

    @Override
    public T remove(int index) {
        Node<T> oldNode = getNodeByIndex(index);
        removeNode(oldNode);
        return oldNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if (object == current.value || object != null && object.equals(current.value)) {
                removeNode(current);
                return true;
            }
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

    private static class Node<E> {
        private E value;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prev, E value, Node<E> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<T> getNodeByIndex(int index) {
        exceptionCheckIncludeIndex(index);
        Node<T> current = head;
        if (index < size / 2) {
            current = head;
            while (index > 0) {
                current = current.next;
                index--;
            }
        } else {
            current = tail;
            while (index < size - 1) {
                current = current.prev;
                index++;
            }
        }
        return current;
    }

    private void removeNode(Node<T> node) {
        size--;
        if (size == 0) {
            head = tail = null;
        } else {
            if (node == head) {
                head = node.next;
                head.prev = null;
            } else if (node == tail) {
                tail = node.prev;
                tail.next = null;
            } else {
                node.next.prev = node.prev;
                node.prev.next = node.next;
            }
        }
    }

    private void exceptionCheck(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + " out of bounds " + size);
        }
    }

    private void exceptionCheckIncludeIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + " out of bounds " + size);
        }
    }
}
