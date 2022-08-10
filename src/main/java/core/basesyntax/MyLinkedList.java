package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String WRONG_INDEX = "The index is out off list.";
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(tail, value, null);
        if (size == 0) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);

        Node<T> currentNode = findNodeByIndex(index);
        if (index == 0) {
            Node<T> insertedNode = insertBefore(null, value, currentNode);
            head = insertedNode;
        } else {
            Node<T> prevNode = currentNode.prev;
            Node<T> insertedNode = insertBefore(prevNode, value, currentNode);
            prevNode.next = insertedNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> changedNode = findNodeByIndex(index);
        T oldValue = changedNode.value;
        changedNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> deletedNode = findNodeByIndex(index);
        unlinkNode(index, deletedNode);
        return deletedNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (object == currentNode.value || object != null && object.equals(currentNode.value)) {
                unlinkNode(i, currentNode);
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

    private Node<T> findNodeByIndex(int index) {
        Node<T> currentNode;
        if (index < 0.5 * size) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(WRONG_INDEX + index);
        }
    }

    private void unlinkNode(int index, Node<T> deletedNode) {
        if (size == 1) {
            head = null;
            tail = null;
            size = 0;
            return;
        }

        if (index == 0) {
            deletedNode.next.prev = null;
            head = deletedNode.next;
        } else if (index == size - 1) {
            deletedNode.prev.next = null;
            tail = deletedNode.prev;
        } else {
            deletedNode.prev.next = deletedNode.next;
            deletedNode.next.prev = deletedNode.prev;
        }
        size--;
    }

    private Node<T> insertBefore(Node<T> prevNode, T value, Node<T> currentNode) {
        Node<T> insertedNode = new Node<>(prevNode, value, currentNode);
        currentNode.prev = insertedNode;
        return insertedNode;
    }

    private static class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T value;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }
    }
}
