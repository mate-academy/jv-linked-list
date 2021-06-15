package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node;
        if (isEmpty()) {
            node = new Node<>(null, value, null);
            head = node;
        } else {
            node = new Node<>(tail, value, null);
            node.prev.next = node;
        }
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            Node<T> afterNode = findNode(index);
            Node<T> beforeNode = afterNode.prev;
            Node<T> newNode = new Node<>(beforeNode, value, afterNode);

            linkNodes(beforeNode, newNode, afterNode);
            head = index == 0 ? newNode : head;
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
        return findNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> oldNode = findNode(index);
        T oldValue = oldNode.value;
        oldNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> removed = findNode(index);
        unlink(removed);
        return removed.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> removed = findNode(object);
        if (removed != null) {
            unlink(removed);
            return true;
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

    private static class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        public Node(Node<T> prev, T value,Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }

    }

    private void unlink(Node<T> unlinked) {
        Node<T> nodeBefore = unlinked.prev;
        Node<T> nodeAfter = unlinked.next;
        if (size == 1) {
            head = null;
            tail = null;
        } else if (nodeBefore == null) {
            nodeAfter.prev = null;
            head = nodeAfter;
        } else if (nodeAfter == null) {
            nodeBefore.next = null;
            tail = nodeBefore;
        } else {
            nodeAfter.prev = nodeBefore;
            nodeBefore.next = nodeAfter;
        }
        size--;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is not valid!");
        }
    }

    private void linkNodes(Node<T> beforeNode, Node<T> newNode, Node<T> afterNode) {
        if (beforeNode != null) {
            beforeNode.next = newNode;
        }
        if (afterNode != null) {
            afterNode.prev = newNode;
        }
    }

    private Node<T> findNode(int index) {
        checkIndex(index);
        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private Node<T> findNode(T neededValue) {
        Node<T> current = head;

        for (int i = 0; i < size; i++) {
            if (current.value == neededValue
                    || current.value != null && current.value.equals(neededValue)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }
}
