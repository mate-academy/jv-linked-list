package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int currentSize;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        if (currentSize == 0) {
            addFirstNode(value);
        } else {
            addNodeToTailPosition(value);
        }
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > currentSize) {
            throw new IndexOutOfBoundsException("Input index : " + index
                    + " out of bonds: [0 - " + currentSize + "]");
        } else if (currentSize == index) {
            add(value);
        } else if (index == 0) {
            addNodeToHeadPosition(value);
        } else {
            addNodeToAnyPosition(value, index);
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
        checkIndexBounds(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndexBounds(index);
        Node<T> currentNode = getNodeByIndex(index);
        T temp = currentNode.value;
        currentNode.value = value;
        return temp;
    }

    @Override
    public T remove(int index) {
        checkIndexBounds(index);
        Node<T> currentNode = getNodeByIndex(index);
        T tempToReturn = currentNode.value;
        unlink(currentNode);
        return tempToReturn;
    }

    @Override
    public boolean remove(T value) {
        boolean isRemoteSuccessful = false;
        if (isValuesEquals(head.value, value)) {
            isRemoteSuccessful = unlink(head);
        } else {
            isRemoteSuccessful = unlink(new Node<>(null, value, null));
        }
        return isRemoteSuccessful;
    }

    private boolean unlink(Node<T> node) {
        boolean isUnlinkSuccessful = false;
        if (isValuesEquals(head.value, node.value) && currentSize == 1) {
            tail.next = null;
            tail.prev = null;
            tail.value = null;
            head = tail;
            isUnlinkSuccessful = true;
            currentSize--;
        } else if (isValuesEquals(head.value, node.value)) {
            head.next.prev = null;
            head = head.next;
            isUnlinkSuccessful = true;
            currentSize--;
        } else {
            Node<T> currentNode = head;
            while (currentNode != null) {
                if (isValuesEquals(currentNode.value, node.value)) {
                    currentNode.prev.next = currentNode.next;
                    currentNode.next.prev = currentNode.prev;
                    isUnlinkSuccessful = true;
                    currentSize--;
                    break;
                }
                currentNode = currentNode.next;
            }
        }
        return isUnlinkSuccessful;
    }

    @Override
    public int size() {
        return currentSize;
    }

    @Override
    public boolean isEmpty() {
        return currentSize == 0;
    }

    private void checkIndexBounds(int index) {
        if (index < 0 || index >= currentSize) {
            throw new IndexOutOfBoundsException("Input index : " + index
                    + " out of bonds: [0 - " + currentSize + "]");
        }
    }

    private void addFirstNode(T value) {
        Node<T> node = new Node<>(null, value, null);
        head = node;
        tail = node;
        currentSize++;
    }

    private void addNodeToHeadPosition(T value) {
        Node<T> node = new Node<>(null, value, head);
        head.prev = node;
        head = node;
        currentSize++;
    }

    private void addNodeToTailPosition(T value) {
        Node<T> node = new Node<>(tail, value, null);
        tail.next = node;
        tail = node;
        currentSize++;
    }

    private void addNodeToAnyPosition(T value, int index) {
        Node<T> currentNode = getNodeByIndex(index);
        Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
        currentNode.prev.next = newNode;
        currentNode.prev = newNode;
        currentSize++;
    }

    private boolean unlinkNodeInDifferentPosition(T value) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (isValuesEquals(currentNode.value, value)) {
                currentNode.prev.next = currentNode.next;
                currentNode.next.prev = currentNode.prev;
                currentSize--;
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    private boolean unlinkSingleNode() {
        tail.next = null;
        tail.prev = null;
        tail.value = null;
        head = tail;
        currentSize--;
        return true;
    }

    private boolean unlinkHeadNode() {
        head.next.prev = null;
        head = head.next;
        currentSize--;
        return true;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> currentNode = currentSize / 2 < index ? head : tail;
        if (currentNode == head) {
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            for (int i = currentSize - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private boolean isValuesEquals(T value, T object) {
        return Objects.equals(value, object);
    }

    private static class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
