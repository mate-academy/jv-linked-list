package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(null, value, null);
        if (isEmpty()) {
            first = node;
        } else {
            last.next = node;
            node.prev = last;
        }
        last = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        verifyIndexIsValid(index);
        Node<T> node = new Node<>(null, value, null);
        if (index == 0) {
            node.next = first;
            first.prev = node;
            first = node;
            size++;
            return;
        }
        Node<T> nextNode = getNode(index);
        nextNode.prev.next = node;
        node.prev = nextNode.prev;
        nextNode.prev = node;
        node.next = nextNode;
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
        verifyIndexIsValid(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        verifyIndexIsValid(index);
        Node<T> node = getNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        verifyIndexIsValid(index);
        Node<T> nodeToRemove = getNode(index);
        unlink(nodeToRemove);
        size--;
        return nodeToRemove.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        while (currentNode != null) {
            if (elementsAreEqual(currentNode.value, object)) {
                unlink(currentNode);
                size--;
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

    private Node<T> getNode(int index) {
        verifyIndexIsValid(index);
        return index <= size / 2
                ? getNodeByIteratingFromBeginning(index) : getNodeByIteratingFromEnd(index);
    }

    private Node<T> getNodeByIteratingFromBeginning(int index) {
        int counter = 0;
        Node<T> currentNode = first;
        while (counter != index) {
            currentNode = currentNode.next;
            counter++;
        }
        return currentNode;
    }

    private Node<T> getNodeByIteratingFromEnd(int index) {
        int counter = size - 1;
        Node<T> currentNode = last;
        while (counter != index) {
            currentNode = currentNode.prev;
            counter--;
        }
        return currentNode;
    }

    private void unlink(Node<T> node) {
        if (first == node && last == node) {
            first = null;
            last = null;
            return;
        }
        if (node.prev == null) {
            first = node.next;
            first.prev = null;
        } else if (node.next == null) {
            last = node.prev;
            last.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    private boolean verifyIndexIsValid(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    String.format("Invalid index passed: %d for size: %d", index, size));
        }
        return true;
    }

    private boolean elementsAreEqual(T first, T last) {
        return (first == last) || ((first != null) && (first.equals(last)));
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }
}
