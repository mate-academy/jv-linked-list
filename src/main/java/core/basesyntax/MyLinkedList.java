package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            addFirst(value);
        } else {
            addAfter(value);
        }
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else if (index == 0) {
            addBefore(value);
        } else {
            link(nodeOf(index), value);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        return nodeOf(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> element = nodeOf(index);
        T oldValue = element.item;
        element.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> element = nodeOf(index);
        T oldValue = element.item;
        unlink(element);
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        if (isEmpty()) {
            return false;
        }
        Node<T> element = head;
        while (element != null) {
            if (objectsAreEqual(object, element.item)) {
                unlink(element);
                return true;
            }
            element = element.next;
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

    private void verifyIndexIsInRange(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
    }

    private Node<T> nodeOf(int index) {
        verifyIndexIsInRange(index);
        Node<T> node;
        if (index < size >> 1) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private void addFirst(T value) {
        head = new Node<>(null, value, null);
        tail = head;
        size++;
    }

    private void addBefore(T value) {
        head.prev = new Node<>(null, value, head);
        head = head.prev;
        size++;
    }

    private void addAfter(T value) {
        tail.next = new Node<>(tail, value, null);
        tail = tail.next;
        size++;
    }

    private void link(Node<T> nodeAtIndex, T value) {
        nodeAtIndex.prev.next = new Node<>(nodeAtIndex.prev, value, nodeAtIndex);
        nodeAtIndex.prev = nodeAtIndex.prev.next;
        size++;
    }

    private void unlink(Node<T> element) {
        if (element == head) {
            unlinkFirst(element);
        } else if (element == tail) {
            unlinkLast(element);
        } else {
            unlinkMiddle(element);
        }
        size--;
    }

    private void unlinkFirst(Node<T> firstElement) {
        firstElement.item = null;
        if (firstElement.next != null) {
            head = firstElement.next;
            firstElement.next.prev = null;
        }
    }

    private void unlinkLast(Node<T> lastElement) {
        lastElement.item = null;
        if (lastElement.prev != null) {
            tail = lastElement.prev;
            lastElement.prev.next = null;
        }
    }

    private void unlinkMiddle(Node<T> element) {
        element.prev.next = element.next;
        element.next.prev = element.prev;
    }

    private boolean objectsAreEqual(T a, T b) {
        return a == b || a != null && a.equals(b);
    }

    private static class Node<T> {
        private Node<T> prev;
        private T item;
        private Node<T> next;

        private Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
