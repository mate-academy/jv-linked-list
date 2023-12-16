package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> temporaryNode = new Node<>(tail, value, null);
        if (size == 0) {
            head = tail = temporaryNode;
        } else {
            tail = tail.next = temporaryNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
            initializeList(newNode);
        } else if (index == 0) {
            addToBeginning(newNode);
        } else if (size == index) {
            appendToList(newNode);
        } else {
            Node<T> tempNode = getNode(index - 1);
            insertNode(newNode, tempNode);
        }
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
        checkIndex(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> nodeToRemove = getNode(index);
        removeNode(nodeToRemove);
        return nodeToRemove.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        while (node != null) {
            if (equals(object, node.value)) {
                removeNode(node);
                return true;
            }
            node = node.next;
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

    private void initializeList(Node<T> newNode) {
        head = tail = newNode;
    }

    private void appendToList(Node<T> newNode) {
        tail.next = newNode;
        newNode.prev = tail;
        tail = newNode;
    }

    private void addToBeginning(Node<T> newNode) {
        head.prev = newNode;
        newNode.next = head;
        head = newNode;
    }

    private void insertNode(Node<T> newNode, Node<T> afterThis) {
        newNode.prev = afterThis;
        newNode.next = afterThis.next;
        afterThis.next = newNode;
        newNode.next.prev = newNode;
    }

    private Node<T> getNode(int index) {
        if (index <= size / 2) {
            Node<T> tempNode = head;
            for (int i = 0; i < index; i++) {
                tempNode = tempNode.next;
            }
            return tempNode;
        } else {
            Node<T> tempNode = tail;
            for (int i = size - 1; i > index; i--) {
                tempNode = tempNode.prev;
            }
            return tempNode;
        }
    }

    private void removeNode(Node<T> node) {
        if (node.prev == null) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node.next == null) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        size--;
    }

    private boolean equals(T object1, T object2) {
        return object1 == null ? object2 == null : object1.equals(object2);
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index " + index
                    + " for size: " + size);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index " + index + " for size: " + size);
        }
    }
}
