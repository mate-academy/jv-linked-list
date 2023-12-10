package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            Node<T> newNode = new Node<>(null, value, null);
            head = newNode;
            tail = newNode;
        } else {
            Node<T> retainTail = tail;
            tail = new Node<>(retainTail, value, null);
            retainTail.nextNode = tail;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else if (index == 0 && size > 0) {
            Node<T> currentHead = head;
            head = new Node<>(null, value, currentHead);
            currentHead.prevNode = head;
            size++;
        } else {
            checkIndex(index);
            Node<T> currentIndexNode = node(index);
            Node<T> currentIndexPrevNode = currentIndexNode.prevNode;
            Node<T> newNode = new Node<>(currentIndexPrevNode, value, currentIndexNode);
            currentIndexNode.prevNode = newNode;
            if (currentIndexPrevNode == null) {
                head = newNode;
            } else {
                currentIndexPrevNode.nextNode = newNode;
            }
            size++;
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
        checkIndex(index);
        return node(index).nodeItem;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        final T currentIndexNodeItem = node(index).nodeItem;
        Node<T> currentIndexNode = node(index);
        currentIndexNode.nodeItem = value;
        if (index == 0 && size == 1) {
            head = currentIndexNode;
        } else if (index == 0 && size > 1) {
            currentIndexNode.nextNode.prevNode = currentIndexNode;
            head = currentIndexNode;
        } else if (index < size - 1) {
            currentIndexNode.nextNode.prevNode = currentIndexNode;
            currentIndexNode.prevNode.nextNode = currentIndexNode;
        } else if (index == size - 1) {
            currentIndexNode.prevNode.nextNode = currentIndexNode;
            tail = currentIndexNode;
        }
        return currentIndexNodeItem;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> currentIndexNode = node(index);
        final T currentIndexNodeItem = currentIndexNode.nodeItem;
        final Node<T> currentIndexNextNode = currentIndexNode.nextNode;
        final Node<T> currentIndexPrevNode = currentIndexNode.prevNode;
        if (currentIndexPrevNode == null) {
            head = currentIndexNextNode;
        } else {
            currentIndexPrevNode.nextNode = currentIndexNextNode;
            currentIndexNode.prevNode = null;
        }
        if (currentIndexNextNode == null) {
            tail = currentIndexPrevNode;
        } else {
            currentIndexNextNode.prevNode = currentIndexPrevNode;
            currentIndexNode.nextNode = null;
        }
        currentIndexNode.nodeItem = null;
        size--;
        return currentIndexNodeItem;
    }

    @Override
    public boolean remove(T object) {
        int index = 0;
        for (Node<T> someNode = head; someNode != null; someNode = someNode.nextNode) {
            if (object == someNode.nodeItem
                    || ((object != null) && object.equals(someNode.nodeItem))) {
                remove(index);
                return true;
            }
            index++;
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index " + index + " is Out of bounds for this ArrayList!");
        }
    }

    private Node<T> node(int index) {
        Node<T> someNode;
        if (index < (size / 2)) {
            someNode = head;
            for (int i = 0; i < index; i++) {
                someNode = someNode.nextNode;
            }
        } else {
            someNode = tail;
            for (int i = size - 1; i > index; i--) {
                someNode = someNode.prevNode;
            }
        }
        return someNode;
    }

    private static class Node<T> {
        private T nodeItem;
        private Node<T> nextNode;
        private Node<T> prevNode;

        Node(Node<T> prev, T insertedElement, Node<T> next) {
            this.nodeItem = insertedElement;
            this.nextNode = next;
            this.prevNode = prev;
        }
    }
}
