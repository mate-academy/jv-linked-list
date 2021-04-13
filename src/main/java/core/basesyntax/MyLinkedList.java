package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public boolean add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }

        tail = newNode;
        size++;

        return true;
    }

    @Override
    public void add(T value, int index) {
        checkIsIndexExist(index, size, "Invalid index");

        if (index == size) {
            add(value);
            return;
        }

        if (index == 0) {
            addAtBeginning(value);
        } else {
            addByIndex(value, index);
        }
    }

    private Node<T> findNodeByIndex(int index) {
        if (index < size() / 2) {
            return findNodeFromStart(index);
        } else {
            return findNodeFromEnd(index);
        }
    }

    private Node<T> findNodeFromStart(int index) {
        int count = 0;
        Node<T> searchNode = head;
        while (count < index) {
            searchNode = searchNode.next;
            count++;
        }

        return searchNode;
    }

    private Node<T> findNodeFromEnd(int index) {
        int count = size() - 1;
        Node<T> searchNode = tail;
        while (index < count) {
            searchNode = searchNode.prev;
            count--;
        }

        return searchNode;
    }

    private void addByIndex(T value, int index) {
        Node<T> nodeByIndex = findNodeByIndex(index);
        Node<T> newNode = new Node<>(nodeByIndex.prev, value, nodeByIndex);
        nodeByIndex.prev.next = newNode;
        nodeByIndex.prev = newNode;
        size++;
    }

    private void addAtBeginning(T value) {
        Node<T> tempNode = new Node<>(null, value, head);
        head.prev = tempNode;
        head = tempNode;
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T listItem: list) {
            add(listItem);
        }

        return true;
    }

    @Override
    public T get(int index) {
        checkIsIndexExist(index, size - 1, "Invalid index");

        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIsIndexExist(index, size - 1, "Invalid index");

        Node<T> nodeByIndex = findNodeByIndex(index);
        T valueBeforeSet = nodeByIndex.item;
        nodeByIndex.item = value;

        return valueBeforeSet;
    }

    @Override
    public T remove(int index) {
        checkIsIndexExist(index, size - 1, "Invalid index");
        return unlink(findNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> headNode = head;
        if (Objects.equals(head.item, object)) {
            removeFirstElement();
            return true;
        }

        while (headNode != null) {
            if (headNode.item == null || headNode.item.equals(object)) {
                removeFromMiddle(headNode);
                return true;
            }
            headNode = headNode.next;
        }

        return false;
    }

    private void removeFirstElement() {
        Node<T> newHead = head.next;
        if (newHead != null) {
            newHead.prev = null;
            head = newHead;
        } else {
            head = null;
        }
        size--;
    }

    private void removeFromMiddle(Node<T> nodeByIndex) {
        Node<T> previous = nodeByIndex.prev;
        Node<T> next = nodeByIndex.next;
        previous.next = next;
        next.prev = previous;
        size--;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private T unlink(Node<T> targetNode) {
        final T element = targetNode.item;
        Node<T> prev = targetNode.prev;
        Node<T> next = targetNode.next;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            targetNode.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            targetNode.next = null;
        }

        size--;
        return element;
    }

    private void checkIsIndexExist(int index, int size, String message) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(message);
        }
    }
}
