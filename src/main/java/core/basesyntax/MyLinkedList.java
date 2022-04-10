package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        Node<T> nodeToAdd = new Node<>(null, value, null);
        if (size == 0) {
            tail = nodeToAdd;
            head = tail;
            tail.prev = null;
            tail.next = null;
            size++;
            return;
        }
        nodeToAdd.prev = tail;
        tail.next = nodeToAdd;
        tail = nodeToAdd;
        size++;
        return;
    }

    @Override
    public void add(T value, int index) {
        Node<T> nodeToAddByIndex = new Node<>(null, value, null);
        if (index == size) {
            add(value);
            return;
        } else if (index == 0) {
            nodeToAddByIndex.next = head;
            head = nodeToAddByIndex;
            head.next.prev = head;
            size++;
            return;
        } else {
            Node<T> currentNode = getNodeByIndex(index);
            nodeToAddByIndex.prev = currentNode.prev;
            currentNode.prev.next = nodeToAddByIndex;
            nodeToAddByIndex.next = currentNode;
            currentNode.prev = nodeToAddByIndex;
            size++;
            return;
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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        Node<T> nodeAtIndex = null;
        if (index < (int) size / 2) {
            nodeAtIndex = head;
            for (int i = 0; i < index; i++) {
                nodeAtIndex = nodeAtIndex.next;
            }
            return nodeAtIndex.value;
        } else {
            nodeAtIndex = tail;
            for (int i = size - 1; i > index; i--) {
                nodeAtIndex = nodeAtIndex.prev;
            }
            return nodeAtIndex.value;
        }
    }

    @Override
    public T set(T value, int index) {
        T currentValue;
        if (index >= 0 && index < size) {
            currentValue = getNodeByIndex(index).value;
            getNodeByIndex(index).value = value;
            return currentValue;
        }
        throw new IndexOutOfBoundsException("Index is out of bounds");
    }

    @Override
    public T remove(int index) {
        if (index >= 0 && index < size) {
            T valueToRemove = get(index);
            unlink(getNodeByIndex(index));
            return valueToRemove;
        }
        throw new IndexOutOfBoundsException("Index is out of bounds");
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (currentNode.value == object
                    || currentNode.value != null
                    && currentNode.value.equals(object)) {
                unlink(currentNode);
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

    private Node<T> getNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        Node<T> nodeAtIndex = null;
        if (index < (int)(size / 2)) {
            nodeAtIndex = head;
            for (int i = 0; i < index; i++) {
                nodeAtIndex = nodeAtIndex.next;
            }
            return nodeAtIndex;
        } else {
            nodeAtIndex = tail;
            for (int i = size - 1; i > index; i--) {
                nodeAtIndex = nodeAtIndex.prev;
            }
            return nodeAtIndex;
        }
    }

    private void unlink(Node<T> nodeToUnlink) {
        Node<T> previousNode = nodeToUnlink.prev;
        Node<T> nextNode = nodeToUnlink.next;
        if (previousNode == null) {
            head = nextNode;

        } else if (nextNode == null) {
            tail = previousNode;

        } else {
            nextNode.prev = previousNode;
            previousNode.next = nextNode;
        }
        size--;
    }
}
