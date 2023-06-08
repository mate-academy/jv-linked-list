package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> firstNode;
    private Node<T> lastNode;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
            firstNode = newNode;
        } else {
            newNode.prev = lastNode;
            lastNode.next = newNode;
        }
        lastNode = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, firstNode);
            firstNode.prev = newNode;
            firstNode = newNode;
            size++;
        } else {
            addNodeInside(value, index);
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
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> current = findNodeByIndex(index);
        T old = current.value;
        current.value = value;
        return old;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeToRemove = findNodeByIndex(index);
        unlink(nodeToRemove);

        return nodeToRemove.value;
    }

    @Override
    public boolean remove(T object) {
        if (findByElement(object) !=null) {
            unlink(findByElement(object));
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    private void addNodeInside(T value, int index) {
        Node<T> oldNode = findNodeByIndex(index);
        Node<T> newNode = new Node<>(oldNode.prev, value, oldNode);
        oldNode.prev.next = newNode;
        oldNode.prev = newNode;
        size++;
    }

    private Node<T> findNodeByIndex(int index) {
        checkIndex(index);
        Node<T> current;
        if (index < size / 2) {
            current = firstNode;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = lastNode;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private void unlink(Node<T> node) {
        if (node == null) {
            return;
        }
        if (node == firstNode) {
            firstNode = firstNode.next;
        } else if (node == lastNode) {
            lastNode = lastNode.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }

    private Node<T> findByElement(T element) {
        Node<T> current = firstNode;
        while (current != null) {
            if ((current.value != null && current.value.equals(element))
                    || current.value == element) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
