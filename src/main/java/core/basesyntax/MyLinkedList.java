package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (isEmpty()) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexWithBound(index, size);
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            addToHead(value);
            return;
        }
        Node<T> targetNode = findNode(index);
        Node<T> newNode = new Node<>(targetNode.prev,
                value,
                targetNode);
        targetNode.prev.next = newNode;
        targetNode.prev = newNode;
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
        return findNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> targetNode = findNode(index);
        T tempValue = targetNode.item;
        targetNode.item = value;
        return tempValue;
    }

    @Override
    public T remove(int index) {
        Node<T> targetNode = findNode(index);
        removeNode(targetNode);
        return targetNode.item;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> node = head; node != null; node = node.next) {
            if (compareItems(node.item, object)) {
                removeNode(node);
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
        return head == null;
    }

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    private boolean isTail(Node<T> node) {
        return node.next == null;
    }

    private boolean isHead(Node<T> node) {
        return node.prev == null
                    && size > 1;
    }

    private boolean isSingleElement() {
        return size == 1;
    }

    private boolean compareItems(T firstItem, T secondItem) {
        return firstItem == secondItem
                || (firstItem != null
                && firstItem.equals(secondItem));
    }

    private void checkIndexWithBound(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index doesn't exist. "
                    + "You should enter a number between 0 and "
                    + (size - 1));
        }
    }

    private void checkIndexWithBound(int index, int bound) {
        if (index < 0
                || (index > (bound)
                && index != 0)) {
            throw new IndexOutOfBoundsException("Index doesn't exist. "
                    + "You should enter a number between 0 and "
                    + bound);
        }
    }

    private Node<T> findNode(int index) {
        checkIndexWithBound(index);
        Node<T> targetNode;
        int nodeIndex;
        if (index < size / 2) {
            targetNode = head;
            nodeIndex = 0;
            while (nodeIndex != index) {
                targetNode = targetNode.next;
                nodeIndex++;
            }
        } else {
            targetNode = tail;
            nodeIndex = size - 1;
            while (nodeIndex != index) {
                targetNode = targetNode.prev;
                nodeIndex--;
            }
        }
        return targetNode;
    }

    private void addToHead(T value) {
        Node<T> newNode = new Node<>(null,
                value,
                head);
        head.prev = newNode;
        head = newNode;
        size++;
    }

    private void removeHead() {
        head = head.next;
        head.prev = null;
        size--;
    }

    private void removeTail() {
        tail = tail.prev;
        tail.next = null;
        size--;
    }

    private void removeSingleElement() {
        head = null;
        tail = null;
        size--;
    }

    private void removeFromMiddle(Node<T> targetNode) {
        targetNode.next.prev = targetNode.prev;
        targetNode.prev.next = targetNode.next;
        size--;
    }

    private void removeNode(Node<T> targetNode) {
        if (isHead(targetNode)) {
            removeHead();
        } else if (isSingleElement()) {
            removeSingleElement();
        } else if (isTail(targetNode)) {
            removeTail();
        } else {
            removeFromMiddle(targetNode);
        }
    }
}
