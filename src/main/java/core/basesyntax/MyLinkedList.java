package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> firstNode;
    private Node<T> lastNode;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(value);
        if (size == 0) {
            firstNode = node;
        } else {
            lastNode.next = node;
            node.previous = lastNode;
        }
        lastNode = node;
        size += 1;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        Node<T> node = new Node<>(value);
        Node<T> oldNode = getNodeByIndex(index);
        if (oldNode.previous != null) {
            node.previous = oldNode.previous;
            oldNode.previous.next = node;
        } else {
            firstNode = node;
        }
        node.next = oldNode;
        oldNode.previous = node;
        size += 1;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        Node<T> currentNode = getNodeByIndex(index);
        return (T) currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = getNodeByIndex(index);
        T currentValue = (T) currentNode.value;
        currentNode.value = value;
        return currentValue;
    }

    @Override
    public T remove(int index) {
        Node<T> deletedNode = getNodeByIndex(index);
        unlink(deletedNode, index);
        return (T) deletedNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> deletedNode = firstNode;
        for (int i = 0; i < size; i++) {
            if (object == deletedNode.value || object != null && object.equals(deletedNode.value)) {
                remove(i);
                return true;
            }
            deletedNode = deletedNode.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    private void checkIndex(int index) {
        if (index < size && index >= 0) {
            return;
        }
        throw new IndexOutOfBoundsException("Index out of bounds of MyLinkedList.");
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        if (index == 0) {
            return firstNode;
        }
        Node<T> currentNode;
        int currentIndex;
        if (index < size / 2) {
            currentIndex = 0;
            currentNode = firstNode;
            while (currentIndex < index) {
                currentNode = currentNode.next;
                currentIndex += 1;
            }
        } else {
            currentIndex = size - 1;
            currentNode = lastNode;
            while (currentIndex > index) {
                currentNode = currentNode.previous;
                currentIndex -= 1;
            }
        }
        return currentNode;
    }

    private void unlink(Node<T> node, int index) {
        if (node.previous != null) {
            node.previous.next = node.next;
        } else {
            firstNode = node.next;
        }
        if (node.next != null) {
            node.next.previous = node.previous;
        } else {
            lastNode = node.previous;
        }
        size -= 1;
    }

    private class Node<T> {
        private T value;
        private Node previous;
        private Node next;

        private Node(T value) {
            this.value = value;
        }
    }
}
