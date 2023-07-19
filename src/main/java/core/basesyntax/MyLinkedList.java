package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> firstNode;
    private Node<T> lastNode;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<T>(lastNode, value, null);
        if (firstNode == null) {
            firstNode = node;
            lastNode = node;
            size++;
            return;
        }
        if (firstNode.next == null) {
            firstNode.next = node;
        }
        lastNode.next = node;
        lastNode = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndexInBounds(index);
        Node<T> nextNode = findByIndex(index);
        Node<T> node = new Node<T>(nextNode.prev, value, nextNode);
        if (index == 0) {
            firstNode = node;
        } else {
            nextNode.prev.next = node;
        }
        nextNode.prev = node;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkIndexInBounds(index);
        return findByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndexInBounds(index);
        Node<T> desiredNode = findByIndex(index);
        T oldValue = desiredNode.value;
        desiredNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexInBounds(index);
        Node<T> desiredNode = findByIndex(index);
        unlink(desiredNode);
        T oldValue = desiredNode.value;
        size--;
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = firstNode;
        int i = 0;
        while (currentNode != null) {
            if (currentNode.value != null && currentNode.value.equals(object)
                    || currentNode.value == object) {
                unlink(currentNode);
                size--;
                return true;
            }
            currentNode = currentNode.next;
            i++;
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

    private void checkIndexInBounds(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds " + size);
        }
    }

    private void unlink(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            firstNode = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            lastNode = node.prev;
        }
    }

    private Node<T> findByIndex(int index) {
        if (index <= (size - 1) / 2) {
            Node<T> currentNode = firstNode;
            for (int j = 0; j <= (size - 1) / 2; j++) {
                if (j == index) {
                    return currentNode;
                }
                currentNode = currentNode.next;
            }
        } else {
            Node<T> currentNode = lastNode;
            for (int j = size - 1; j >= (size - 1) / 2; j--) {
                if (j == index) {
                    return currentNode;
                }
                currentNode = currentNode.prev;
            }
        }
        return null;
    }

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }
}
