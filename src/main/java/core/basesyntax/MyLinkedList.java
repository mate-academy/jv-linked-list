package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        validateIndexForAdding(index);
        Node<T> newNode = new Node<>(null, value, null);

        if (isEmpty()) {
            addNodeToEmptyList(newNode);
        } else if (index == 0) {
            addNodeAtBeginning(newNode);
        } else if (index == size) {
            addNodeAtEnd(newNode);
        } else {
            addNodeAtIndex(newNode, index);
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T listNodeValue : list) {
            add(listNodeValue);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).data;
    }

    @Override
    public T set(T value, int index) {
        Node<T> nodeToUpdate = getNode(index);
        T oldValue = nodeToUpdate.data;
        nodeToUpdate.data = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeToRemove = getNode(index);
        return unlink(nodeToRemove).data;
    }

    @Override
    public boolean remove(T object) {
        int index = getIndexOfNode(object);
        if (index == -1) {
            return false;
        }
        remove(index);
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

    private void validateIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Wrong index provided");
        }
    }

    private void validateIndexForAdding(int index) {
        // Same as function above, but index == size is OK (only used in adding elements)
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Wrong index provided");
        }
    }

    private Node<T> getNode(int index) {
        validateIndex(index);
        Node<T> currentNode;
        boolean isRightSide = index <= size / 2;
        if (isRightSide) {
            int currentIndex = 0;
            currentNode = head;
            while (currentIndex != index) {
                currentNode = currentNode.next;
                currentIndex++;
            }
        } else {
            int currentIndex = size - 1;
            currentNode = tail;
            while (currentIndex != index) {
                currentNode = currentNode.prev;
                currentIndex--;
            }
        }
        return currentNode;
    }

    private boolean equals(T a, T b) {
        return (a == b) || (a != null && a.equals(b));
    }

    private int getIndexOfNode(T value) {
        for (int i = 0; i < size; i++) {
            if (equals(value, getNode(i).data)) {
                return i;
            }
        }
        return -1;
    }

    private Node<T> unlink(Node<T> node) {
        if (node == tail) {
            tail = node.prev;
        } else if (node == head) {
            head = node.next;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
        return node;
    }

    private void addNodeToEmptyList(Node<T> newNode) {
        head = newNode;
        tail = newNode;
    }

    private void addNodeAtBeginning(Node<T> newNode) {
        newNode.next = head;
        head.prev = newNode;
        head = newNode;
    }

    private void addNodeAtEnd(Node<T> newNode) {
        newNode.prev = tail;
        tail.next = newNode;
        tail = newNode;
    }

    private void addNodeAtIndex(Node<T> newNode, int index) {
        Node<T> nodeToInsertBefore = getNode(index);
        newNode.prev = nodeToInsertBefore.prev;
        newNode.next = nodeToInsertBefore;
        nodeToInsertBefore.prev.next = newNode;
        nodeToInsertBefore.prev = newNode;
    }

    private static class Node<E> {
        private E data;
        private Node<E> next;
        private Node<E> prev;

        private Node(Node<E> prev, E data, Node<E> next) {
            this.prev = prev;
            this.data = data;
            this.next = next;
        }
    }
}
