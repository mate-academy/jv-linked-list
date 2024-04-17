package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> node;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        if (head == null) {
            addHeadNode(value);
        } else {
            addNextNode(value);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException("Index is not valid: " + index);
        }
        if (index == 0) {
            if (head == null) {
                addHeadNode(value);
            } else {
                node = new Node<>(null, value, head);
                head.prev = node;
                head = node;
            }
            size++;
        } else if (size == index) {
            addNextNode(value);
            size++;
        } else {
            addNodeInside(index, value);
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
        checkElementIndex(index);
        return getNodeOnIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> nodeOnIndex = getNodeOnIndex(index);
        T changedValue = nodeOnIndex.item;
        nodeOnIndex.item = value;
        return changedValue;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unLink(index);
    }

    @Override
    public boolean remove(T object) {
        int index = findIndexByValue(object);
        if (index == -1) {
            return false;
        } else {
            unLink(index);
        }
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("Index is not valid: " + index);
        }
    }

    private int findIndexByValue(T value) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (value == node.item || value != null && value.equals(node.item)) {
                return i;
            }
            node = node.next;
        }
        return -1;
    }

    private Node<T> getNodeOnIndex(int index) {
        Node<T> nodeOnIndex;
        if (index < (size >> 1)) {
            nodeOnIndex = head;
            for (int i = 0; i < index; i++) {
                nodeOnIndex = nodeOnIndex.next;
            }
        } else {
            nodeOnIndex = tail;
            for (int i = size - 1; i > index; i--) {
                nodeOnIndex = nodeOnIndex.prev;
            }
        }
        return nodeOnIndex;
    }

    private void addHeadNode(T value) {
        node = new Node<>(null, value, null);
        head = node;
        tail = node;
    }

    private void addNextNode(T value) {
        if (head.equals(tail)) {
            node = new Node<>(node.prev, value, node.next);
            node.next = null;
            node.prev = head;
            head.next = node;
            head.prev = null;
            tail = node;
        } else {
            node = new Node<>(node.prev, value, node.next);
            node.prev = tail;
            tail.next = node;
            node.next = null;
            tail = node;
        }
    }

    private void addNodeInside(int index, T value) {
        Node<T> nextNode = getNodeOnIndex(index);
        node = new Node<>(nextNode.prev, value, nextNode);
        nextNode.prev = node;
        node.prev.next = node;
        size++;
    }

    private T unLink(int index) {
        Node<T> removedNode = getNodeOnIndex(index);
        T removedValue = removedNode.item;
        if (head.equals(tail)) {
            size--;
            head = null;
            return null;
        } else if (removedNode.prev == null) {
            head = head.next;
            head.prev = null;
            size--;
        } else if (removedNode == tail) {
            tail = tail.prev;
            tail.next = null;
            size--;
        } else {
            removedNode.prev.next = removedNode.next;
            removedNode.next.prev = removedNode.prev;
            size--;
        }
        return removedValue;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
