package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        private T value;
        private Node<T> previous;
        private Node<T> next;

        public Node(T value, Node<T> previous, Node<T> next) {
            this.value = value;
            this.previous = previous;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (size < 1) {
            head = new Node<>(value, null, null);
            tail = head;
        } else {
            Node<T> nodeToAdd = new Node<>(value, tail, null);
            tail.next = nodeToAdd;
            tail = nodeToAdd;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIfIndexSuitableForAdding(index);
        if (index == size) {
            add(value);
        } else {
            Node<T> nodeOnGivenIndex = getNode(index);
            if (isHead(nodeOnGivenIndex)) {
                Node<T> nodeToAdd = new Node<>(value, null, nodeOnGivenIndex);
                nodeOnGivenIndex.previous = nodeToAdd;
                head = nodeToAdd;
            } else {
                Node<T> previousNode = nodeOnGivenIndex.previous;
                Node<T> nodeToAdd = new Node<>(value, previousNode, nodeOnGivenIndex);
                previousNode.next = nodeToAdd;
                nodeOnGivenIndex.previous = nodeToAdd;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeToRemove = getNode(index);
        unlink(nodeToRemove);
        return nodeToRemove.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;

        boolean removed = false;
        do {
            if (valuesAreEqual(object, current)) {
                unlink(current);
                removed = true;
                break;
            }
            current = current.next;
        } while (current != tail);

        return removed;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size < 1;
    }

    private void unlink(Node<T> nodeToRemove) {
        if (size == 1) {
            head = null;
            tail = null;
        } else if (isHead(nodeToRemove)) {
            removeHead(nodeToRemove);
        } else if (isTail(nodeToRemove)) {
            removeTail(nodeToRemove);
        } else {
            removeNodeInTheMiddle(nodeToRemove);
        }
        size--;
    }

    private Node<T> getNode(int index) {
        checkIfElementWithIndexExists(index);
        Node<T> node;
        if (indexInFirstHalf(index)) {
            node = getNodeFromHead(index);
        } else {
            node = getNodeFromTail(index);
        }
        return node;
    }

    private boolean indexInFirstHalf(int index) {
        return index <= size / 2;
    }

    private Node<T> getNodeFromHead(int index) {
        Node<T> node;
        node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    private Node<T> getNodeFromTail(int index) {
        Node<T> node;
        node = tail;
        for (int i = size - 1; i > index; i--) {
            node = node.previous;
        }
        return node;
    }

    private void checkIfElementWithIndexExists(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    String.format("Index %d is out of bounds for size %d", index, size));
        }
    }

    private void checkIfIndexSuitableForAdding(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    String.format("Cannot add element on index %d for size %d", index, size));
        }
    }

    private boolean valuesAreEqual(T object, Node<T> current) {
        return current.value == null && object == null
                || current.value != null && current.value.equals(object);
    }

    private void removeNodeInTheMiddle(Node<T> nodeToRemove) {
        Node<T> previousNode = nodeToRemove.previous;
        Node<T> nextNode = nodeToRemove.next;
        previousNode.next = nextNode;
        nextNode.previous = previousNode;
    }

    private void removeTail(Node<T> nodeToRemove) {
        Node<T> previousNode = nodeToRemove.previous;
        previousNode.next = null;
        tail = previousNode;
    }

    private void removeHead(Node<T> nodeToRemove) {
        Node<T> nextNode = nodeToRemove.next;
        nextNode.previous = null;
        head = nextNode;
    }

    private boolean isHead(Node<T> node) {
        return node.equals(head);
    }

    private boolean isTail(Node<T> node) {
        return node.equals(tail);
    }
}
