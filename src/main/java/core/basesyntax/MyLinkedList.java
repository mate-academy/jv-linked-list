package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int HEAD_INDEX = 0;
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (isEmpty()) {
            head = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, size);
        if (isEmpty()
                || index == size) {
            add(value);
            return;
        }
        if (index == HEAD_INDEX) {
            addToHead(new Node<>(value));
            return;
        }
        addToIndex(new Node<>(value), findNode(index));
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
        Node<T> targetNode = findNode(index);
        return targetNode.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> targetNode = findNode(index);
        T tempValue = targetNode.item;
        targetNode.item = value;
        return tempValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> targetNode = findNode(index);
        removeNode(targetNode);
        return targetNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> targetNode = findNode(object);
        if (isTail(targetNode)) {
            return false;
        }
        removeNode(targetNode);
        return true;
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

        public Node(T item) {
            this.item = item;
        }

        public Node() {

        }

        @Override
        public boolean equals(Object node) {
            if (node == this) {
                return true;
            }
            if (node == null) {
                return false;
            }
            if (node.getClass().equals(Node.class)) {
                Node<T> current = (Node<T>) node;
                return ((this.item == current.item
                        || (this.item != null
                        && this.item.equals(current.item)))
                        && (this.prev == current.prev
                        || (this.prev != null
                        && this.prev.equals(current.prev)))
                        && ((this.next == current.next
                        || (this.next != null
                        && this.next.equals(current.next)))));
            }
            return false;
        }
    }

    private boolean isTail(Node<T> node) {
        return tail.equals(node)
                && size > 1;
    }

    private boolean isHead(Node<T> node) {
        return head.equals(node)
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

    private void checkIndex(int index) {
        if (index < 0
                || (index > (size - 1)
                && index != 0)) {
            throw new IndexOutOfBoundsException("Index doesn't exist. "
                    + "You should enter a number between 0 and "
                    + (size - 1));
        }
    }

    private void checkIndex(int index, int bound) {
        if (index < 0
                || (index > (bound)
                && index != 0)) {
            throw new IndexOutOfBoundsException("Index doesn't exist. "
                    + "You should enter a number between 0 and "
                    + bound);
        }
    }

    private Node<T> findNode(int index) {
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

    private Node<T> findNode(T object) {
        Node<T> targetNode = head;
        while (!compareItems(targetNode.item, object)) {
            targetNode = targetNode.next;
            if (isTail(targetNode)) {
                return tail;
            }
        }
        return targetNode;
    }

    private void addToHead(Node<T> newNode) {
        newNode.next = head;
        head.prev = newNode;
        head = newNode;
        size++;
    }

    private void addToIndex(Node<T> newNode, Node<T> targetNode) {
        newNode.prev = targetNode.prev;
        newNode.next = targetNode;
        targetNode.prev.next = newNode;
        targetNode.prev = newNode;
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
