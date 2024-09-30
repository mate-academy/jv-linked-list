package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (tail == null) {
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        Node<T> newNode = new Node<>(null, value, null);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else if (index == size) {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else if (index > 0 && index < size) {
            Node<T> current = findByIndex(index);
            current.prev.next = newNode;
            newNode.next = current;
            newNode.prev = current.prev;
            current.prev = newNode;
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
        checkIndex(index);
        return findByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = findByIndex(index);
        T changedValue = currentNode.item;
        currentNode.item = value;
        return changedValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> currentNode = findByIndex(index);
        if (currentNode.prev != null && currentNode.next != null) {
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
        } else if (currentNode == head && currentNode.next != null) {
            currentNode.next.prev = null;
            head = currentNode.next;
        } else if (currentNode == tail && currentNode.prev != null) {
            currentNode.prev.next = null;
            tail = currentNode.prev;
        }
        size--;
        return currentNode.item;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            Node<T> nodeToRemove = findByIndex(i);
            if ((object == null && nodeToRemove.item == null)
                    || (object != null && object.equals(nodeToRemove.item))) {
                if (nodeToRemove.prev != null && nodeToRemove.next != null) {
                    nodeToRemove.prev.next = nodeToRemove.next;
                    nodeToRemove.next.prev = nodeToRemove.prev;
                } else if (nodeToRemove == head && nodeToRemove.next != null) {
                    nodeToRemove.next.prev = null;
                    head = nodeToRemove.next;
                } else if (nodeToRemove == tail && nodeToRemove.prev != null) {
                    nodeToRemove.prev.next = null;
                    tail = nodeToRemove.prev;
                }
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size <= 0;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds");
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds");
        }
    }

    private Node<T> findByIndex(int index) {
        Node<T> currentByIndex;
        if (index < size / 2) {
            currentByIndex = head;
            for (int i = 0; i < index; i++) {
                currentByIndex = currentByIndex.next;
            }
        } else {
            currentByIndex = tail;
            for (int i = size - 1; i > index; i--) {
                currentByIndex = currentByIndex.prev;
            }
        }
        return currentByIndex;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.item = element;
            this.next = next;
        }
    }
}
