package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;
    @Override
    public void add(T value) {
        if (head == null) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            tail.next = new Node<>(tail, value, null);
            tail = tail.next;
        }
        size++;
    }

    @Override
    public void add(T value, int index) { // 4 2 8 3 5 . insert 1 at pos2 .  2 5
        checkIndex(index, true);
        if (index == size) {
            add(value);
            return;
        }
        Node<T> currentNode = findNodeByIndex(index);
        Node<T> insertNode;
        if (currentNode == head) {
            insertNode = new Node<>(null, value, currentNode);
            currentNode.prev = insertNode;
            head = insertNode;
            size++;
        } else {
            insertNode = new Node<>(currentNode.prev, value, currentNode);
            currentNode.prev.next = insertNode;
            currentNode.prev = insertNode;
            size++;
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
        checkIndex(index, false);
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, false);
        Node<T> result = findNodeByIndex(index);
        T oldValue = result.value;
        result.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, false);
        Node<T> currentNode = findNodeByIndex(index);
        return unlink(currentNode);
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if (current.value == null ? object == null : current.value.equals(object)) {
                unlink(current);
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

    private void checkIndex(int index, boolean inclusive) {
        if (inclusive) {
            if (index < 0 || index > size) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
            }
        } else {
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
            }
        }
    }

    private Node<T> findNodeByIndex(int index) {
        int currentIndex;
        Node<T> currentNode;
        if (index < size / 2) {
            currentIndex = 0;
            currentNode = head;
            while (currentNode != null && currentIndex != index) {
                currentNode = currentNode.next;
                currentIndex++;
            }
        } else {
            currentIndex = size - 1;
            currentNode = tail;
            while (currentNode != null && currentIndex != index) {
                currentNode = currentNode.prev;
                currentIndex--;
            }
        }
        return currentNode;
    }

    private T unlink(Node<T> node) {
        final T oldValue = node.value;
        if (size == 1) {
            head = null;
            tail = null;
        } else if (node == head) {
            head = node.next;
            head.prev = null;
        } else if (node == tail) {
            tail = node.prev;
            tail.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        node.next = null;
        node.value = null;
        node.prev = null;
        size--;
        return oldValue;
    }

    private class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
