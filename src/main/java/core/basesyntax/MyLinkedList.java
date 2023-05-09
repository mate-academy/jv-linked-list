package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> tailNode = tail;
        Node<T> newNode = new Node<>(tailNode, value, null);
        tail = newNode;
        if (size == 0) {
            head = newNode;
        } else {
            tailNode.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> currentNode = getNode(index);
        Node<T> newNode = new Node<T>(currentNode.prev, value, currentNode);
        if (currentNode.prev == null) {
            head = newNode;
        } else {
            currentNode.prev.next = newNode;
        }
        currentNode.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T numbers : list) {
            add(numbers);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> setNode = getNode(index);
        T setResult = setNode.item;
        setNode.item = value;
        return setResult;
    }

    @Override
    public T remove(int index) {
        Node<T> removeNode = getNode(index);
        T removeValue = removeNode.item;
        unlink(removeNode);
        return removeValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> thisNode = head;
        while (thisNode != null) {
            if (thisNode.item == object || object != null && object.equals(thisNode.item)) {
                unlink(thisNode);
                return true;
            }
            thisNode = thisNode.next;
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
            throw new IndexOutOfBoundsException("Something was wrong.");
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> newlingNode;
        if (index < (size << 1)) {
            newlingNode = head;
            for (int i = 0; i < index; i++) {
                newlingNode = newlingNode.next;
            }
        } else {
            newlingNode = tail;
            for (int i = 0; i < index; i--) {
                newlingNode = newlingNode.prev;
            }
        }
        return newlingNode;
    }

    private void unlink(Node<T> removeNode) {
        if (removeNode == tail) {
            tail = removeNode.prev;
        } else if (removeNode == head) {
            head = removeNode.next;
        } else {
            removeNode.next.prev = removeNode.prev;
            removeNode.prev.next = removeNode.next;
        }
        size--;
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
}
