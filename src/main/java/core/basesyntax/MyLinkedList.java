package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.item = element;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (size == index) {
            linkLast(value);
        } else {
            Node<T> nextNode = searchNode(index);
            linkBefore(value, nextNode);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            linkLast(element);
        }
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        Node<T> targetNode = searchNode(index);
        return targetNode.item;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> nodeOnIndex = searchNode(index);
        T previousItem = nodeOnIndex.item;
        nodeOnIndex.item = value;
        return previousItem;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        Node<T> targetNode = searchNode(index);
        T previousItem = targetNode.item;
        unlink(targetNode);
        return previousItem;
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> i = head; i != null; i = i.next) {
                if (i.item == null) {
                    unlink(i);
                    return true;
                }
            }
        }
        for (Node<T> i = head; i != null; i = i.next) {
            if (Objects.equals(object, i.item)) {
                unlink(i);
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

    private void linkLast(T value) {
        final MyLinkedList.Node<T> tailNode = tail;
        final MyLinkedList.Node<T> newNode = new MyLinkedList.Node<>(tailNode, value, null);
        tail = newNode;
        if (tailNode == null) {
            head = newNode;
        } else {
            tailNode.next = newNode;
        }
        size++;
    }

    private void linkBefore(T value, Node<T> node) {
        final Node<T> previous = node.prev;
        final Node<T> newNode = new Node<>(previous, value, node);
        node.prev = newNode;
        if (previous == null) {
            head = newNode;
        } else {
            previous.next = newNode;
        }
        size++;
    }

    private void unlink(Node<T> value) {
        final Node<T> next = value.next;
        final Node<T> prev = value.prev;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            value.prev = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            value.next = null;
        }
        value.item = null;
        size--;
    }

    private Node<T> searchNode(int index) {
        if (index < (size / 2)) {
            Node<T> search = head;
            for (int i = 0; i < index; i++) {
                search = search.next;
            }
            return search;
        } else {
            Node<T> search = tail;
            for (int i = size - 1; i > index; i--) {
                search = search.prev;
            }
            return search;
        }
    }

    private void checkElementIndex(int index) {
        boolean isElementIndex = index >= 0 && index < size;
        if (!isElementIndex) {
            throw new IndexOutOfBoundsException("Wrong index " + index
                    + " for size " + size + " of LinkedList");
        }
    }

    private void checkPositionIndex(int index) {
        boolean isPositionIndex = index >= 0 && index <= size;
        if (!isPositionIndex) {
            throw new IndexOutOfBoundsException("Wrong index " + index
                    + " for size " + size + " of LinkedList");
        }
    }
}
