package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            linkFirstElement(value);
        } else {
            linkLastElement(value);
        }
    }

    @Override
    public void add(T value, int index) {
        checkIndexToAdd(index);
        if (index == 0) {
            linkFirstElement(value);
        } else if (index == size) {
            linkLastElement(value);
        } else {
            linkBeforeElement(value, getNodeByIndex(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T node : list) {
            add(node);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.element;
        node.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> nodeByIndex = getNodeByIndex(index);
        final T removedElement = nodeByIndex.element;
        unlink(nodeByIndex);
        return removedElement;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = getNodeByValue(object);
        if (node != null) {
            unlink(node);
            return true;
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

    private void linkFirstElement(T value) {
        Node<T> newNode = new Node<>(null, value, head);
        head = newNode;
        tail = newNode;
        size++;
    }

    private void linkLastElement(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        tail.next = newNode;
        tail = newNode;
        size++;
    }

    private void linkBeforeElement(T value, Node<T> node) {
        Node<T> prevNode = node.prev;
        Node<T> newNode = new Node<>(prevNode, value, node);
        prevNode.next = newNode;
        node.prev = newNode;
        size++;
    }

    private Node<T> getNodeByValue(T value) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (checkValue(value, currentNode)) {
                return currentNode;
            }
            currentNode = currentNode.next;
        }
        return null;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> currentNode = head;
        int middleListSizePosition = size / 2;
        checkIndex(index);
        if (index <= middleListSizePosition) {
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        }
        if (index > middleListSizePosition) {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range: " + index);
        }
    }

    private void checkIndexToAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of range: " + index);
        }
    }

    private boolean checkValue(T element, Node<T> node) {
        return (node.element == element || (node.element != null
                && node.element.equals(element)));
    }

    private void unlink(Node<T> nodeByIndex) {
        if (nodeByIndex == head) {
            head = head.next;
        } else {
            nodeByIndex.prev.next = nodeByIndex.next;
        }
        if (nodeByIndex == tail) {
            tail = tail.prev;
        } else {
            nodeByIndex.next.prev = nodeByIndex.prev;
        }
        size--;
    }

    private class Node<T> {
        private T element;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
