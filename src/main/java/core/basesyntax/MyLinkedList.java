package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    private void indexRangeValid(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node<T> getNodeOfIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        int middleList = size / 2;
        Node<T> iterator = head;
        if (middleList >= index) {
            for (int i = 0; i <= middleList; ++i) {
                if (i == index) {
                    break;
                }
                iterator = iterator.next;
            }
        } else if (middleList < index) {
            iterator = tail;
            for (int i = size - 1; i >= 0; --i) {
                if (i == index) {
                    break;
                }
                iterator = iterator.prev;
            }
        }
        return iterator;
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        }
        ++size;
    }

    @Override
    public void add(T value, int index) {
        indexRangeValid(index);
        if (index == 0 && size > 0) {
            Node<T> oldHeadNode = head;
            head = new Node<>(null, value, oldHeadNode);
            oldHeadNode.prev = head;
        } else if (index == size) {
            add(value);
            return;
        } else {
            Node<T> nodeOfIndex = getNodeOfIndex(index);
            Node<T> newNode = new Node<>(nodeOfIndex.prev, value, nodeOfIndex);
            nodeOfIndex.prev.next = newNode;
            nodeOfIndex.prev = newNode;
        }
        ++size;
    }

    @Override
    public void addAll(List<T> list) {
        for (T elem : list) {
            add(elem);
        }
    }

    @Override
    public T get(int index) {
        Node<T> nodeOfIndex = getNodeOfIndex(index);
        return nodeOfIndex.item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> nodeOfIndex = getNodeOfIndex(index);
        T oldValue = nodeOfIndex.item;
        nodeOfIndex.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeOfIndex = getNodeOfIndex(index);
        if (head.equals(nodeOfIndex)) {
            head = nodeOfIndex.next;
        } else if (tail.equals(nodeOfIndex)) {
            tail = nodeOfIndex.prev;
        } else {
            Node<T> prevNode = nodeOfIndex.prev;
            Node<T> nextNode = nodeOfIndex.next;
            if (prevNode != null) {
                prevNode.next = nextNode;
            }
            nextNode.prev = prevNode;
        }
        --size;
        return nodeOfIndex.item;
    }

    @Override
    public boolean remove(T object) {
        if (head == null) {
            return false;
        }

        if (head.item.equals(object)) {
            head = head.next;
            --size;
            return true;
        }

        Node<T> currentNode = head;
        while (currentNode.next != null) {
            if (object == currentNode.next.item || object != null
                    && object.equals(currentNode.next.item)) {
                currentNode.next = currentNode.next.next;
                --size;
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
}
