package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(last, null, value);
        if (last != null) {
            last.right = newNode;
        }
        size++;
        updateFirstLastNode(newNode);
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> currentNode = getNode(index);
        Node<T> newNode = new Node<>(currentNode.left, currentNode, value);
        if (currentNode.left != null) {
            currentNode.left.right = newNode;
        }
        currentNode.left = newNode;
        size++;
        updateFirstLastNode(newNode);
    }

    @Override
    public void addAll(List<T> list) {
        for (T value: list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        Node<T> currentNode = getNode(index);
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = getNode(index);
        T result = currentNode.value;
        currentNode.value = value;
        return result;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode = getNode(index);
        return deleteNode(currentNode);
    }

    @Override
    public boolean remove(T value) {
        Node<T> currentNode = findNode(value);
        if (currentNode == null) {
            return false;
        }
        deleteNode(currentNode);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void updateFirstLastNode(Node<T> changetNode) {
        if (changetNode.left == null) {
            first = changetNode;
        }
        if (changetNode.right == null) {
            last = changetNode;
        }
    }

    private Node<T> getNode(int index) {
        checkBounds(index);
        Node<T> currentNode;
        if (index >= (size >> 1)) {
            currentNode = last;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.left;
            }
        } else {
            currentNode = first;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.right;
            }
        }
        return currentNode;
    }

    private Node<T> findNode(T value) {
        Node<T> currentNode = first;
        if (currentNode == null) {
            return null;
        }
        do {
            if (value == currentNode.value || value != null && value.equals(currentNode.value)) {
                return currentNode;
            }
            currentNode = currentNode.right;
        } while (currentNode != null);
        return null;
    }

    private void checkBounds(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " out of bounds, available list size: " + size);
        }
    }

    private T deleteNode(Node<T> node) {
        if (node.equals(first)) {
            first = node.right;
        }
        if (node.equals(last)) {
            last = node.left;
        }
        if (node.left != null) {
            node.left.right = node.right;
        }
        if (node.right != null) {
            node.right.left = node.left;
        }
        T result = node.value;
        node.value = null;
        size--;
        return result;
    }

    public static class Node<T> {
        private Node<T> left;
        private Node<T> right;
        private T value;

        public Node(Node<T> left, Node<T> right, T item) {
            this.left = left;
            this.right = right;
            this.value = item;
        }
    }
}
