package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int START_HEAD_INDEX = 0;
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (isEmpty()) {
            head = new Node<>(value, null, null);
            tail = head;
        } else if (index == START_HEAD_INDEX) {
            addBeforeHead(value);
        } else if (index == size) {
            addAfterTail(value);
        } else {
            addByIndexAndValue(index, value);
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
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> setNode = getNodeByIndex(index);
        T previousValue = setNode.value;
        setNode.value = value;
        return previousValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> removeNode = getNodeByIndex(index);
        return unlinkNode(removeNode);
    }

    @Override
    public boolean remove(T object) {
        Node<T> removeNode = head;
        for (int i = START_HEAD_INDEX; i < size; i++) {
            if (removeNode.value == object
                    || removeNode.value != null
                    && removeNode.value.equals(object)) {
                unlinkNode(removeNode);
                return true;
            }
            removeNode = removeNode.next;
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

    private void checkIndex(int incomingIndex) {
        if (incomingIndex < START_HEAD_INDEX || incomingIndex >= size) {
            throw new IndexOutOfBoundsException("Can not find index : " + incomingIndex);
        }
    }

    private void checkIndexForAdd(int incomingIndex) {
        if (incomingIndex < START_HEAD_INDEX || incomingIndex > size) {
            throw new IndexOutOfBoundsException("Can not find index : " + incomingIndex);
        }
    }

    private static class Node<E> {
        private E value;
        private Node<E> next;
        private Node<E> prev;

        Node(E incomingElementValue, Node<E> incomingPrev, Node<E> incomingNext) {
            value = incomingElementValue;
            prev = incomingPrev;
            next = incomingNext;
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> exitNode;
        if (index < (size >> 1)) {
            exitNode = head;
            for (int i = START_HEAD_INDEX; i < index; i++) {
                exitNode = exitNode.next;
            }
        } else {
            exitNode = tail;
            for (int i = size - 1; i > index; i--) {
                exitNode = exitNode.prev;
            }
        }
        return exitNode;
    }

    private void addByIndexAndValue(int index, T value) {
        Node<T> addNode = getNodeByIndex(index);
        Node<T> beforeAddingNode = addNode.prev;
        Node<T> afterAddingNode = beforeAddingNode.next;
        Node<T> addingNode = new Node<>(value, beforeAddingNode, afterAddingNode);
        beforeAddingNode.next = addingNode;
        afterAddingNode.prev = addingNode;
    }

    private void addBeforeHead(T value) {
        Node<T> addingNode = head;
        head = new Node<>(value, null, addingNode);
        addingNode.prev = head;
    }

    private void addAfterTail(T value) {
        Node<T> beforeAddingNode = tail;
        tail = new Node<>(value, beforeAddingNode, null);
        beforeAddingNode.next = tail;
    }

    private T unlinkNode(Node<T> incomingNode) {
        final T element = incomingNode.value;
        Node<T> next = incomingNode.next;
        Node<T> prev = incomingNode.prev;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            incomingNode.prev = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            incomingNode.next = null;
        }
        incomingNode.value = null;
        size--;
        return element;
    }
}
