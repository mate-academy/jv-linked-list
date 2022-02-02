package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    public class Node<T> {
        private T value;
        private Node<T> previous;
        private Node<T> next;

        public Node(Node<T> previous, Node<T> next, T value) {
            this.previous = previous;
            this.next = next;
            this.value = value;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, null, value);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkException(index);
        Node<T> currentNode;
        Node<T> nextNode = findByIndex(index);
        if (index == 0) {
            currentNode = new Node<>(null, nextNode, value);
            nextNode.previous = currentNode;
            head = currentNode;
            size++;
        } else {
            currentNode = new Node<>(nextNode.previous, nextNode, value);
            nextNode.previous.next = currentNode;
            nextNode.previous = currentNode;
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
        checkException(index);
        return findByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkException(index);
        Node<T> newNode = findByIndex(index);
        T oldVal = newNode.value;
        newNode.value = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        checkException(index);
        Node<T> removable = findByIndex(index);
        unlink(removable);
        size--;
        return removable.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> removable = head;
        for (int i = 0; i < size; i++) {
            if (object == removable.value
                    || (object != null && object.equals(removable.value))) {
                remove(i);
                return true;
            } else {
                removable = removable.next;
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
        return size == 0;
    }

    public void unlink(Node<T> node) {
        Node<T> previousNode = node.previous;
        Node<T> nextNode = node.next;
        if (previousNode == null && nextNode != null) {
            nextNode.previous = null;
            head = nextNode;
        }
        if (nextNode == null && previousNode != null) {
            previousNode.next = null;
            tail = previousNode;
        }
        if (previousNode != null && nextNode != null) {
            previousNode.next = nextNode;
            nextNode.previous = previousNode;
        }
    }

    private Node<T> findByIndex(int index) {
        checkException(index);
        Node<T> indexedNode;
        if (index < size / 2) {
            indexedNode = head;
            for (int i = 0; i < index; i++) {
                indexedNode = indexedNode.next;
            }
        } else {
            indexedNode = tail;
            for (int i = size - 1; i > index; i--) {
                indexedNode = indexedNode.previous;
            }
        }
        return indexedNode;
    }

    private void checkException(int index) {
        if (index >= size
                || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds");
        }
    }
}
