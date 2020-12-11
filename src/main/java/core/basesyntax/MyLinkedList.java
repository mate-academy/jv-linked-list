package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    private static class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private boolean isInvalidIndex(int index) {
        return index < 0 || index >= size;
    }

    private void linkToNode(T elementToInsert, Node<T> successor) {
        Node<T> predecessor = successor.prev;
        Node<T> newNode = new Node<>(predecessor, elementToInsert, successor);
        successor.prev = newNode;
        if (predecessor == null) {
            head = newNode;
        } else {
            predecessor.next = newNode;
        }
        size++;
    }

    private void linkToTail(T elementToInsert) {
        Node<T> predecessor = this.tail;
        Node<T> newNode = new Node<>(predecessor, elementToInsert, null);
        this.tail = newNode;
        if (predecessor == null) {
            head = newNode;
        } else {
            predecessor.next = newNode;
        }
        size++;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> targetNode = head;
        for (int i = 0; i < index; i++) {
            targetNode = targetNode.next;
        }
        return targetNode;
    }

    private T removeNode(Node<T> nodeToRemove) {
        Node<T> beforeTarget = nodeToRemove.prev;
        Node<T> afterTarget = nodeToRemove.next;
        if (afterTarget == null) {
            tail = beforeTarget;
            nodeToRemove.prev = null;
        } else if (beforeTarget == null) {
            head = afterTarget;
            nodeToRemove.next = null;
        } else {
            beforeTarget.next = afterTarget;
            afterTarget.prev = beforeTarget;
        }
        size--;
        return nodeToRemove.value;
    }

    @Override
    public boolean add(T value) {
        linkToTail(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            linkToTail(value);
        } else if (index < size && index >= 0) {
            linkToNode(value, getNodeByIndex(index));
        } else {
            throw new IndexOutOfBoundsException(index + " index is out of bounds");
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T item: list) {
            add(item);
        }
        return true;
    }

    @Override
    public T get(int index) {
        if (isInvalidIndex(index)) {
            throw new IndexOutOfBoundsException(index + " index is out of bounds");
        }
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        if (isInvalidIndex(index)) {
            throw new IndexOutOfBoundsException(index + " index is out of bounds");
        }
        Node<T> oldElement = getNodeByIndex(index);
        T oldNodeValue = oldElement.value;
        oldElement.value = value;
        return oldNodeValue;
    }

    @Override
    public T remove(int index) {
        if (isInvalidIndex(index)) {
            throw new IndexOutOfBoundsException(index + " index is out of bounds");
        }
        return removeNode(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            T targetValue = getNodeByIndex(i).value;
            if (targetValue == object || targetValue != null && targetValue.equals(object)) {
                remove(i);
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
        return size == 0;
    }
}
