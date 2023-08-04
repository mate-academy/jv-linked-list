package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        if (first == null) {
            first = new Node<T>(null, value, null);
            last = first;
        } else {
            Node<T> secondLast = last;
            last = new Node<T>(secondLast, value, null);
            secondLast.next = last;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);

        if (index == size) {
            add(value);
            return;
        }
        Node<T> nodeNext = getNode(index);
        Node<T> nodePrevious = nodeNext.prev;
        Node<T> newNode = new Node<T>(nodePrevious, value, nodeNext);

        if (nodePrevious == null) {
            first = newNode;
        } else {
            nodePrevious.next = newNode;
        }
        nodeNext.prev = newNode;

        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T record: list) {
            add(record);
        }
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return (T) getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> node = getNode(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return (T) unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = first;
        for (int i = 0; i < size; i++) {
            if (node.item == object || (node.item != null && node.item.equals(object))) {
                unlink(node);
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

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("invalid index:" + index);
        }
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("invalid index:" + index);
        }
    }

    private Node getNode(int index) {
        Node node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }

        return node;
    }

    private T unlink(Node<T> currentNode) {
        final T value = currentNode.item;
        Node<T> nodePrevious = currentNode.prev;
        Node<T> nodeNext = currentNode.next;

        if (nodePrevious == null) {
            first = nodeNext;
        } else {
            nodePrevious.next = nodeNext;
        }

        if (nodeNext == null) {
            last = nodePrevious;
        } else {
            nodeNext.prev = nodePrevious;
        }

        size--;
        return value;
    }

    private static class Node<T> {
        private Node<T> prev;
        private T item;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
