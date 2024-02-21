package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int TAIL_ITERATION_CORRECTION = 1;
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    private class Node<T> {
        private Node<T> previous;
        private T value;
        private Node<T> next;

        public Node(Node<T> previous, T value, Node<T> next) {
            this.previous = previous;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (createHeadOrTailIfNeed(value)) {
            return;
        }
        updateTail(value);
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, true);
        if (createHeadOrTailIfNeed(value)) {
            return;
        }
        insertNodeAtIndex(value, index);
    }

    @Override
    public void addAll(List<T> list) {
        for (T valueOfNode : list) {
            add(valueOfNode);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index, false);
        Node<T> foundedNode = foundedNode(index);
        return index == size ? tail.value : foundedNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, false);
        Node<T> foundedNode = foundedNode(index);
        T oldValue = foundedNode.value;
        foundedNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, false);
        Node<T> foundedNode = foundedNode(index);
        T oldValue = foundedNode.value;
        unlink(foundedNode);
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (currentNode.value == object || (currentNode.value != null
                    && currentNode.value.equals(object))) {
                unlink(currentNode);
                return true;
            }
            currentNode = hasNext(currentNode);
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

    private Node<T> foundedNode(int index) {
        if (index == size) {
            return null;
        }
        int distanceFromEnd = size - index;
        Node<T> nodeFromHead = head;
        Node<T> nodeFromTail = tail;
        for (int i = 0; i < (distanceFromEnd >= index ? index : distanceFromEnd
                - TAIL_ITERATION_CORRECTION); i++) {
            if (distanceFromEnd >= index) {
                nodeFromHead = hasNext(nodeFromHead);
            } else {
                nodeFromTail = hasPrevious(nodeFromTail);
            }
        }
        return distanceFromEnd >= index ? nodeFromHead : nodeFromTail;
    }

    private Node<T> hasPrevious(Node<T> node) {
        return node.previous != null ? node.previous : null;
    }

    private Node<T> hasNext(Node<T> node) {
        return node.next != null ? node.next : null;
    }

    private void checkIndex(int index, boolean forAdd) {
        if (index < 0 || index > size || (!forAdd && index == size)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private boolean createTailIfDoesNotExist(T value) {
        if (size == 1) {
            tail = new Node<>(head, value, null);
            head.next = tail;
            size++;
            return true;
        }
        return false;
    }

    private boolean createHeadIfDoesNotExist(T value) {
        if (head == null) {
            head = new Node<>(null, value, null);
            size++;
            return true;
        }
        return false;
    }

    private void updateTail(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        tail.next = newNode;
        tail = newNode;
        size++;
    }

    private void insertNodeAtIndex(T value, int index) {
        Node<T> foundedNode = foundedNode(index);
        Node<T> newNode = new Node<>(null, value, null);
        updateRelationship(foundedNode, newNode);
        size++;
    }

    private void updateRelationship(Node<T> foundedNode, Node<T> newNode) {
        if (foundedNode == null) {
            updateTail(newNode.value);
            size--;
        } else if (foundedNode.equals(head)) {
            updateHeadWithAddNode(newNode);
        } else if (foundedNode.equals(tail)) {
            updateTailWithAddNode(tail, newNode);
        } else {
            updateTailWithAddNode(foundedNode, newNode);
        }
    }

    private void updateTailWithAddNode(Node<T> tail, Node<T> newNode) {
        tail.previous.next = newNode;
        newNode.previous = tail.previous;
        tail.previous = newNode;
        newNode.next = tail;
    }

    private void updateHeadWithAddNode(Node<T> newNode) {
        head.previous = newNode;
        newNode.next = head;
        head = newNode;
    }

    private void unlink(Node<T> foundedNode) {
        if (foundedNode.equals(head)) {
            updateHeadWithDeleteNode();
        } else if (foundedNode.equals(tail)) {
            updateTailWithDeleteNode();
        } else {
            updateChainOfNodeWithDeleteNode(foundedNode);
        }
    }

    private void updateChainOfNodeWithDeleteNode(Node<T> foundedNode) {
        foundedNode.previous.next = foundedNode.next;
        foundedNode.next.previous = foundedNode.previous;
        size--;
    }

    private void updateTailWithDeleteNode() {
        tail.previous.next = null;
        tail = tail.previous;
        size--;
    }

    private void updateHeadWithDeleteNode() {
        head = head.next;
        size--;
    }

    private boolean createHeadOrTailIfNeed(T value) {
        if (createHeadIfDoesNotExist(value)) {
            return true;
        }
        return createTailIfDoesNotExist(value);
    }
}
