package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {

    }

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(null, value, null);
        if (head == null) {
            head = node;
            tail = head;
            size++;
        } else {
            node = new Node<>(tail, value, null);
            node.prev.next = node;
            tail = node;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        checkIndexRangeInAdd(index);
        if (size == index) {
            add(value);
        } else {
            Node<T> node = findNodeByIndex(index);
            Node<T> newNode = new Node<>(node.prev, value, node);
            if (node.prev == null) {
                head = newNode;
            } else {
                node.prev.next = newNode;
            }
            node.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkIndexRange(index);
        return findNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkIndexRange(index);
        Node<T> foundNodeByIndex = findNodeByIndex(index);
        T oldElement = foundNodeByIndex.element;
        foundNodeByIndex.element = value;
        return oldElement;
    }

    @Override
    public T remove(int index) {
        checkIndexRange(index);
        Node<T> removedNode = findNodeByIndex(index);
        unlink(removedNode);
        size--;
        return removedNode.element;
    }

    @Override
    public boolean remove(T object) {
        Node<T> removedNodeByValue = findNodeByValue(object);
        if (removedNodeByValue == null) {
            return false;
        } else {
            unlink(removedNodeByValue);
            size--;
            return true;
        }
    }

    private Node<T> findNodeByValue(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(currentNode.element, object)) {
                return currentNode;
            }
            currentNode = currentNode.next;
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void checkIndexRange(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: "
                                                        + index
                                                        + " out of bounds "
                                                        + "for size "
                                                        + size);
        }
    }

    private void checkIndexRangeInAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: "
                                                        + index
                                                        + " out of bounds "
                                                        + "for size "
                                                        + size);
        }
    }

    private Node<T> findNodeByIndex(int index) {
        if (index < size / 2) {
            Node<T> foundNodeByIndexFromHead = head;
            for (int i = 0; i < index; i++) {
                foundNodeByIndexFromHead = foundNodeByIndexFromHead.next;
            }
            return foundNodeByIndexFromHead;
        } else {
            Node<T> foundNodeByIndexFromTail = tail;
            for (int i = size - 1; i > index; i--) {
                foundNodeByIndexFromTail = foundNodeByIndexFromTail.prev;
            }
            return foundNodeByIndexFromTail;
        }
    }

    private void unlink(Node<T> node) {
        if (node.prev == null) {
            head = head.next;
        } else if (node.next == null) {
            tail = tail.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    private class Node<E> {
        private E element;
        private Node<E> prev;
        private Node<E> next;

        private Node(Node<E> prev, E element, Node<E> next) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
