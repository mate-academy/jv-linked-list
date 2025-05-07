package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (size == 0) {
            setFirstItem(newNode);
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        isInRangeExcludive(index);
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
            add(value);
            return;
        } else if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else if (index == size) {
            add(value);
            return;
        } else {
            insertNode(findNode(index), newNode);
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        return findNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        T oldIem = findNode(index).value;
        findNode(index).value = value;
        return oldIem;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode;
        currentNode = findNode(index);
        T removedItem = currentNode.value;
        unlinkNode(currentNode);
        size--;
        return removedItem;
    }

    @Override
    public boolean remove(T object) {
        boolean isItemRemoved = false;
        Node<T> currentNode;
        for (int i = 0; i < size; i++) {
            currentNode = findNode(i);
            if (currentNode.value == object
                    || currentNode.value != null && currentNode.value.equals(object)) {
                remove(i);
                isItemRemoved = true;
                break;
            }
        }
        return isItemRemoved;
    }

    @Override
    public int size() {
        return size;
    }

    private static class Node<E> {
        private E value;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prev, E value, Node<E> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<T> findNode(int index) {
        isInRangeIncludive(index);
        Node<T> currentNode;
        if (index > (size / 2)) {
            currentNode = tail;
            int desiredIndex = size - 1;
            while (index != desiredIndex) {
                currentNode = currentNode.prev;
                desiredIndex--;
            }
        } else {
            currentNode = head;
            int desiredIndex = 0;
            while (index != desiredIndex) {
                currentNode = currentNode.next;
                desiredIndex++;
            }
        }
        return currentNode;
    }

    private void isInRangeIncludive(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("Invalid index");
        }
    }

    private void isInRangeExcludive(int index) {
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException("Invalid index");
        }
    }

    private void setFirstItem(Node<T> newNode) {
        head = newNode;
        tail = newNode;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void unlinkNode(Node<T> node) {
        if (node == head) {
            head = head.next;
        } else if (node == tail) {
            tail = tail.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    private void insertNode(Node<T> currentNode, Node<T> newNode) {
        newNode.next = currentNode;
        newNode.prev = currentNode.prev;
        currentNode.prev = newNode;
        currentNode.prev.prev.next = newNode;
    }
}
