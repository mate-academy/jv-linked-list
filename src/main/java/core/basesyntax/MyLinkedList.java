package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> firstNode;
    private Node<T> lastNode;

    class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(lastNode, value, null);
        if (lastNode == null) {
            firstNode = newNode;
        } else {
            lastNode.next = newNode;
        }
        lastNode = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            checkRange(index);
            Node<T> currentNode = getNode(index);
            Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
            Node<T> prev = currentNode.prev;
            newNode.prev = prev;
            newNode.next = currentNode;
            currentNode.prev = newNode;
            if (prev == null) {
                firstNode = newNode;
            } else {
                prev.next = newNode;
            }
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
        checkRange(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkRange(index);
        Node<T> currentNode = getNode(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkRange(index);
        Node<T> currentNode = getNode(index);
        if (currentNode.prev == null) {
            firstNode = currentNode.next;
        } else {
            currentNode.prev.next = currentNode.next;
        }
        if (currentNode.next == null) {
            lastNode = currentNode.prev;
        } else {
            currentNode.next.prev = currentNode.prev;
        }
        size--;
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = firstNode;
        while (currentNode != null) {
            if (elementsAreEqual(object, currentNode)) {
                if (currentNode.prev == null) {
                    firstNode = currentNode.next;
                } else {
                    currentNode.prev.next = currentNode.next;
                }
                if (currentNode.next == null) {
                    lastNode = currentNode.prev;
                } else {
                    currentNode.next.prev = currentNode.prev;
                }
                size--;
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

    private void checkRange(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("No such index: "
                    + index + " for size: " + size);
        }
    }

    private void addFirst(T value) {
        Node<T> newNode = new Node<>(null, value, firstNode);
        if (firstNode == null) {
            lastNode = newNode;
        } else {
            firstNode.prev = newNode;
        }
        firstNode = newNode;
        size++;
    }

    private void addLast(T value) {
        Node<T> newNode = new Node<>(lastNode, value, null);
        if (lastNode == null) {
            firstNode = newNode;
        } else {
            lastNode.next = newNode;
        }
        lastNode = newNode;
        size++;
    }

    private Node<T> getNode(int index) {
        Node<T> current;
        if (index < (size / 2)) {
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

    private boolean elementsAreEqual(T object, Node<T> currentNode) {
        return currentNode.value == object
                || currentNode.value != null && currentNode.value.equals(object);
    }
}
