package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> firstElement;
    private Node<T> lastElement;

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(T element, Node<T> prev, Node<T> next) {
            this.value = element;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, null, null);
        if (isEmpty()) {
            firstElement = newNode;
            lastElement = newNode;
        } else {
            lastElement.next = newNode;
            newNode.prev = lastElement;
            lastElement = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexRange(index, true);
        Node<T> newNode = new Node<>(value, null, null);
        if (isEmpty()) {
            firstElement = newNode;
            lastElement = newNode;
        } else if (index == 0) {
            newNode.next = firstElement;
            firstElement.prev = newNode;
            firstElement = newNode;
        } else if (index == size) {
            lastElement.next = newNode;
            newNode.prev = lastElement;
            lastElement = newNode;
        } else {
            Node<T> currentNode = getNodeByIndex(index);
            Node<T> prevNode = currentNode.prev;
            prevNode.next = newNode;
            newNode.prev = prevNode;
            newNode.next = currentNode;
            currentNode.prev = newNode;
        }
        size++;
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
        checkIndexRange(index, false);
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexRange(index, false);
        Node<T> nodeToRemove = getNodeByIndex(index);
        T element = nodeToRemove.value;
        unlink(nodeToRemove);
        return element;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = firstElement;
        while (currentNode != null) {
            if (currentNode.value == null) {
                if (object == null) {
                    unlink(currentNode);
                    return true;
                }
            } else if (currentNode.value.equals(object)) {
                unlink(currentNode);
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

    private void checkIndexRange(int index, boolean isAddition) {
        if (isAddition) {
            if (index < 0 || index > size) {
                throw new IndexOutOfBoundsException(index
                        + " is out of bounds for adding to the list");
            }
        } else {
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException(index + " is out of bounds");
            }
        }
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndexRange(index, false);
        if (index == 0) {
            return firstElement;
        }
        Node<T> currentNode = firstElement;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private void unlink(Node<T> node) {
        Node<T> next = node.next;
        Node<T> prev = node.prev;

        if (prev == null) {
            firstElement = next;
        } else {
            prev.next = next;
        }
        if (next == null) {
            lastElement = prev;
        } else {
            next.prev = prev;
        }
        size--;
    }
}
