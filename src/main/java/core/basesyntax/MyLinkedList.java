package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> lastNode = last;
        Node<T> newNode = new Node<>(lastNode, value, null);
        last = newNode;
        if (first == null) {
            first = newNode;
        } else {
            lastNode.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkForIndexEqualSize(index);
        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> actual = getNode(index);
            Node<T> newNode = new Node<>(null, value, actual);
            actual.prev = newNode;
            first = newNode;
            size++;
        } else {
            Node<T> actual = getNode(index);
            Node<T> newNode = new Node<>(actual.prev, value, actual.next);
            newNode.prev = actual.prev;
            actual.prev.next = newNode;
            actual.prev = newNode;
            newNode.next = actual;
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
        checkForIndexBound(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkForIndexBound(index);
        Node<T> currentNode = getNode(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkForIndexBound(index);
        Node<T> nodeForRemove = getNode(index);
        return unlinkNode(nodeForRemove);
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        while (currentNode != null) {
            if (object == currentNode.value || object != null
                    && object.equals(currentNode.value)) {
                unlinkNode(currentNode);
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
        return size == 0;
    }

    private Node<T> getNode(int index) {
        Node<T> indexNode;
        if (index < size / 2) {
            indexNode = first;
            for (int i = 0; i < index; i++) {
                indexNode = indexNode.next;
            }
        } else {
            indexNode = last;
            for (int i = size - 1; i > index; i--) {
                indexNode = indexNode.prev;
            }
        }
        return indexNode;
    }

    private T unlinkNode(Node<T> node) {
        if (node == first) {
            first = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node == last) {
            last = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        size--;
        return node.value;
    }

    private void checkForIndexBound(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + ", size " + size);
        }
    }

    private void checkForIndexEqualSize(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + ", size " + size);
        }
    }

    private class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }
}
