package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private Node<T> head = null;
    private Node<T> tail = null;

    private int size = 0;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (tail == null) {
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> currNodeAtIndex = getNode(index);
        Node<T> newNode = new Node<>(null, value, currNodeAtIndex);
        if (currNodeAtIndex.prev != null) {
            currNodeAtIndex.prev.next = newNode;
        } else {
            head = newNode;
        }
        currNodeAtIndex.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        T oldValue = getNode(index).value;
        getNode(index).value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> currNode = getNode(index);
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            if (currNode.prev == null) {
                currNode.next.prev = currNode.prev;
                head = currNode.next;
            } else if (currNode.next == null) {
                currNode.prev.next = currNode.next;
                tail = currNode.prev;
            } else {
                currNode.next.prev = currNode.prev;
                currNode.prev.next = currNode.next;
            }
        }
        size--;
        return (T) currNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currNode = head;
        while (currNode != null) {
            if (Objects.equals(currNode.value, object)) {
                if (currNode.prev != null) {
                    currNode.prev.next = currNode.next;
                } else {
                    head = currNode.next;
                }
                if (currNode.next != null) {
                    currNode.next.prev = currNode.prev;
                } else {
                    tail = currNode.prev;
                }
                size--;
                return true;
            }
            currNode = currNode.next;
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

    private Node<T> getNode(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Getting index is out of bounds");
        }
        Node<T> currNode = head;
        for (int i = 1; i <= index; i++) {
            if (currNode != null && currNode.next != null) {
                currNode = currNode.next;
            }
        }
        return currNode;
    }
}
