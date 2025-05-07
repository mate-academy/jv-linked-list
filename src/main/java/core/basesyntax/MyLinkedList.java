package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        head = new Node<>(null, null, null);
        tail = new Node<>(head, null, null);
        head.next = tail;
    }

    private static class Node<T> {
        private T element;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T element, Node<T> next) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail.prev, value, tail);
        tail.prev.next = newNode;
        tail.prev = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> previousNode = findPreviousNode(index);
        Node<T> newNode = new Node<>(previousNode, value, previousNode.next);
        previousNode.next.prev = newNode;
        previousNode.next = newNode;
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
        return getNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        Node<T> targetNode = getNodeByIndex(index);
        T oldValue = targetNode.element;
        targetNode.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> targetNode = getNodeByIndex(index);
        targetNode.next.prev = targetNode.prev;
        targetNode.prev.next = targetNode.next;
        size--;
        return targetNode.element;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            if (isEqual(get(i), object)) {
                remove(i);
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
        return size == 0;
    }

    private Node<T> findPreviousNode(int index) {
        checkIndexForAdd(index);
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + " Size: " + size);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        return index < size / 2 ? getNodeFromHead(index) : getNodeFromTail(index);
    }

    private Node<T> getNodeFromHead(int index) {
        checkIndex(index);
        Node<T> currentNode = head;
        for (int i = 0; i <= index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private Node<T> getNodeFromTail(int index) {
        checkIndex(index);
        Node<T> currentNode = tail;
        for (int i = 0; i < size - index; i++) {
            currentNode = currentNode.prev;
        }
        return currentNode;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + " Size: " + size);
        }
    }

    private boolean isEqual(T firstElement, T secondElement) {
        return firstElement == secondElement
                || firstElement != null && firstElement.equals(secondElement);
    }
}
