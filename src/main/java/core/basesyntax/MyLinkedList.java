package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> addNode = new Node<>(tail, value, null);
        if (isEmpty()) {
            head = tail = addNode;
        }
        tail.next = addNode;
        tail = addNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        if (index == 0) {
            Node<T> addNode = new Node<T>(null, value, head);
            head = addNode;
        } else {
            Node<T> prevNode = searchByIndex(index).prev;
            Node<T> nextNode = prevNode.next;
            Node<T> addNode = new Node<>(prevNode, value, nextNode);
            nextNode.prev = addNode;
            prevNode.next = addNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return searchByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> nodeToChange = searchByIndex(index);
        T oldValue = nodeToChange.value;
        nodeToChange.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(searchByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> nodeToRemove = head;
        for (int i = 0; i < size; i++) {
            if (nodeToRemove.value == object || (nodeToRemove.value != null
                    && nodeToRemove.value.equals(object))) {
                unlink(nodeToRemove);
                return true;
            }
            nodeToRemove = nodeToRemove.next;
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

    private T unlink(Node<T> node) {
        Node<T> previous;
        Node<T> next;
        if (node.next == null) {
            tail = node.prev;
            tail.next = null;
        } else if (node.prev == null) {
            head = node.next;
            head.prev = null;
        } else {
            previous = node.prev;
            next = node.next;
            previous.next = next;
            next.prev = previous;
        }
        size--;
        return node.value;
    }

    public void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("This index isn't valid");
        }
    }

    private Node<T> searchByIndex(int index) {
        Node<T> searchNode;
        int count;
        if (index > size / 2) {
            count = size - 1;
            searchNode = tail;
            while (index < count) {
                searchNode = searchNode.prev;
                count--;
            }
        } else {
            count = 0;
            searchNode = head;
            while (count < index) {
                searchNode = searchNode.next;
                count++;
            }
        }
        return searchNode;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
