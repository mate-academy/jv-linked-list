package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> tempNode = new Node<>(null, value, null);
        if (size == 0) {
            head = tempNode;
        } else {
            tail.next = tempNode;
            tempNode.prev = tail;
        }
        tail = tempNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == 0) {
            if (size == 0) {
                Node<T> current = new Node<>(null, value, null);
                head = current;
                tail = current;
            } else {
                Node<T> newHead = new Node<>(null, value, head);
                newHead.next = head;
                head.prev = newHead;
                head = newHead;
            }
        } else if (index == size) {
            Node<T> newTail = new Node<>(tail, value, null);
            newTail.prev = tail;
            tail.next = newTail;
            tail = newTail;
        } else {
            Node<T> nodeByIndex = getNode(index);
            Node<T> middleCurrent = new Node<>(nodeByIndex.prev, value, nodeByIndex);
            nodeByIndex.prev.next = middleCurrent;
            middleCurrent.prev = nodeByIndex.prev;
            nodeByIndex.prev = middleCurrent;
            middleCurrent.next = nodeByIndex;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t:list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        if (index == 0) {
            final T oldValue = head.item;
            Node<T> newHead = new Node<>(null, value, head.next);
            newHead.next = head.next;
            head.next.prev = newHead;
            head = newHead;
            return oldValue;
        }
        Node<T> nodeToChange = getNode(index);
        Node<T> node = new Node<>(nodeToChange.prev, value, nodeToChange.next);
        nodeToChange.prev.next = node;
        nodeToChange.next.prev = node;
        return nodeToChange.item;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNode(index);
        T result = node.item;
        unlink(node);
        return result;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            Node<T> node = getNode(i);
            if (node.item == object || node.item != null && node.item.equals(object)) {
                unlink(node);
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

    public void unlink(Node<T> node) {
        if (node == head) {
            head = node.next;
            if (head != null) {
                head.prev = null;
            } else {
                tail = null;
            }
        } else if (node == tail) {
            tail = node.prev;
            if (tail != null) {
                tail.next = null;
            } else {
                head = null;
            }
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
        size--;
    }

    private static class Node<E> {
        private final E item;
        private Node<E> next;
        private Node<E> prev;

        private Node(Node<E> prev, E element, Node<E> next) {
            this.prev = prev;
            this.item = element;
            this.next = next;
        }
    }

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        if (index < (size / 2)) {
            Node<T> firstNode = head;
            for (int i = 0; i < index; i++) {
                firstNode = firstNode.next;
            }
            return firstNode;
        } else {
            Node<T> lastNode = tail;
            for (int i = size - 1; i > index; i--) {
                lastNode = lastNode.prev;
            }
            return lastNode;
        }
    }
}
