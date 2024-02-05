package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private Node<T> first;
    private Node<T> last;
    private int size = ZERO;

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(T value) {
            this.value = value;
        }

        public Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> node;
        if (isEmpty()) {
            node = new Node<>(value);
            first = node;
        } else {
            node = new Node<>(value, last, null);
            last.next = node;
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
        checkIndex(index, "Cannot add value by index: ");
        Node<T> node;
        if (index == ZERO) {
            node = new Node<>(value, null, first);
            first.prev = node;
            first = node;
        } else {
            Node<T> nodeAtIndex = findNodeByIndex(index);
            node = new Node<>(value, nodeAtIndex.prev, nodeAtIndex);
            nodeAtIndex.prev.next = node;
            nodeAtIndex.prev = node;
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
        checkIndex(index, "Cannot get the value by index: ");
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, "Cannot set value by index: ");
        Node<T> nodeAtIndex = findNodeByIndex(index);
        T replacedValue = nodeAtIndex.value;
        nodeAtIndex.value = value;
        return replacedValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, "Cannot remove value by index: ");
        Node<T> node = first;
        if (index == ZERO) {
            removeFirst();
            return node.value;
        }
        if (index == size - ONE) {
            node = last;
            removeLast();
            return node.value;
        }
        node = findNodeByIndex(index);
        unlink(node);
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> nodeToRemove = findNodeByObject(object);
        if (nodeToRemove == null) {
            return false;
        }
        if (nodeToRemove.prev == null) {
            removeFirst();
            return true;
        }
        if (nodeToRemove.next == null) {
            removeLast();
            return true;
        }
        unlink(nodeToRemove);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == ZERO;
    }

    private void checkIndex(int index, String message) {
        if (index >= size || index < ZERO) {
            throw new IndexOutOfBoundsException(message
                    + "Index is out of bounds");
        }
    }

    private Node<T> findNodeByIndex(int index) {
        int listHalf = size / TWO;
        if (index < listHalf) {
            Node<T> node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        }
        Node<T> node = last;
        for (int i = size - ONE; i > index; i--) {
            node = node.prev;
        }
        return node;

    }

    private Node<T> findNodeByObject(T object) {
        Node<T> node = first;
        for (int i = 0; i < size; i++) {
            if (node.value != null && node.value.equals(object)
                    || node.value == object) {
                return node;
            }
            node = node.next;
        }
        return null;
    }

    private void removeIfOneElementInList() {
        first = null;
        last = null;
        size--;
    }

    private void removeFirst() {
        if (size == ONE) {
            removeIfOneElementInList();
        } else {
            first.next.prev = null;
            first = first.next;
            size--;
        }
    }

    private void removeLast() {
        last.prev.next = null;
        last = last.prev;
        size--;
    }

    private void unlink(Node<T> nodeToRemove) {
        nodeToRemove.next.prev = nodeToRemove.prev;
        nodeToRemove.prev.next = nodeToRemove.next;
        size--;
    }
}
