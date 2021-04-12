package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public boolean add(T value) {
        Node<T> node = new Node<>(tail, value, null);
        addHeadOrTail(node);
        return true;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, size);
        if (index == size) {
            add(value);
        } else {
            addByIndex(value, index);
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index, size - 1);
        return findNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, size - 1);
        Node<T> removeNode = findNode(index);
        T removeValue = removeNode.item;
        removeNode.item = value;
        return removeValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, size - 1);
        return unlink(index);
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (object == currentNode.item || (object != null && object.equals(currentNode.item))) {
                unlink(i);
                return true;
            } else {
                currentNode = currentNode.next;
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
        return size == 0;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private void addHeadOrTail(Node node) {
        if (head == null) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
        size++;
    }

    private void addByIndex(T value, int index) {
        Node<T> nodeUnderIndex = findNode(index);
        Node<T> newNode = new Node<>(nodeUnderIndex.prev, value, nodeUnderIndex);
        nodeUnderIndex.prev = newNode;
        if (newNode.prev == null) {
            head = newNode;
        } else {
            newNode.prev.next = newNode;
        }
        size++;
    }

    public T unlink(int index) {
        Node<T> removeNode = findNode(index);
        Node<T> nextNode = removeNode.next;
        Node<T> prevNode = removeNode.prev;
        if (removeNode == head && removeNode == tail) {
            head = null;
            tail = null;
        } else if (removeNode == tail) {
            prevNode.next = null;
            tail = prevNode;
        } else if (removeNode == head) {
            nextNode.prev = prevNode;
            head = nextNode;
        } else {
            nextNode.prev = prevNode;
            prevNode.next = nextNode;
        }
        removeNode.next = null;
        removeNode.prev = null;
        T removeItem = removeNode.item;
        removeNode.item = null;
        size--;
        return removeItem;
    }

    private void checkIndex(int index, int lastIndex) {
        if (index < 0 || index > lastIndex) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node<T> findNode(int index) {
        Node<T> currentNode;
        if (index <= size / 2) {
            currentNode = head;
            for (int i = 0; i != index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            int newIndexFromTail = size - index - 1;
            for (int i = 0; i != newIndexFromTail; i++) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }
}
