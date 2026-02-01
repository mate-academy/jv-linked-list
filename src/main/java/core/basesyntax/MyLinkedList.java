package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> tail;
    private Node<T> head;

    @Override
    public void add(T value) {
        if (size == 0) {
            Node<T> node = new Node<>(null, value, null);
            tail = node;
            head = node;
            size++;
        } else {
            Node<T> node = new Node<>(tail, value, null);
            tail.next = node;
            tail = node;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Can't add value with index: " + index);
        }

        if (index == size) {
            add(value);
            return;
        }

        Node<T> currentNode = findNodeByIndex(index);
        Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);

        if (currentNode.prev == null) {
            head = newNode;
        } else {
            currentNode.prev.next = newNode;
        }

        currentNode.prev = newNode;
        size++;
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
        Node<T> currentNode = findNodeByIndex(index);
        T removerValue = currentNode.value;
        currentNode.value = value;
        return removerValue;
    }

    @Override
    public T remove(int index) {
        validateIndex(index);

        Node<T> currentNode = findNodeByIndex(index);
        T removedValue = currentNode.value;

        checkPlaceAndRemoveNode(currentNode);

        size--;
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if ((object == null && currentNode.value == null)
                    || (object != null && object.equals(currentNode.value))) {
                checkPlaceAndRemoveNode(currentNode);
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

    private void checkPlaceAndRemoveNode(Node<T> currentNode) {
        if (currentNode.prev == null) {
            head = currentNode.next;
            if (head != null) {
                head.prev = null;
            }

        } else if (currentNode.next == null) {
            tail = currentNode.prev;
            if (tail != null) {
                tail.next = null;
            }
        } else {
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
        }
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> currentNode = head;
        validateIndex(index);
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private void validateIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Incottect index: " + index);
        }
    }

    private static class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T value;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }
    }
}
