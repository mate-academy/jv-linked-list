package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> firstNode;
    private Node<T> lastNode;

    public MyLinkedList() {
        this.size = 0;
        this.firstNode = null;
        this.lastNode = null;
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, lastNode, null);
        if (isEmpty()) {
            firstNode = newNode;
        } else {
            lastNode.next = newNode;
        }
        lastNode = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) { // add to end of list or to empty list (index == size == 0)
            add(value);
            return;
        }
        checkIndex(index);
        Node<T> nextNode = findNodeByIndex(index);
        Node<T> newNode = new Node<>(value, nextNode.prev, nextNode);
        if (index == 0) {
            firstNode = newNode;
        } else {
            newNode.prev.next = newNode;
        }
        nextNode.prev = newNode;
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
        checkIndex(index);
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> findedNode = findNodeByIndex(index);
        T oldValue = findedNode.item;
        findedNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> findedNode = findNodeByIndex(index);
        T oldValue = findedNode.item;
        unlinkNode(findedNode);
        size--;
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> currentNode = firstNode;
                     currentNode != null;
                     currentNode = currentNode.next) {
            if ((object == currentNode.item)
                    || ((object != null) && object.equals(currentNode.item))) {
                unlinkNode(currentNode);
                size--;
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
        return (size == 0);
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index Out Of Bounds: index: " + index + ", Bounds: [0 - " + size + "]");
        }
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> currentNode;
        if (index <= (size >> 1)) {
            currentNode = firstNode;
            for (int i = 0; i != index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = lastNode;
            for (int i = size - 1; i != index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private void unlinkNode(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            firstNode = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            lastNode = node.prev;
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(T item, Node<T> prev, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}
