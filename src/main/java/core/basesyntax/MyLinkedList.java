package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int INVALID_INDEX = -1;
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("This index '" + index + "' are wrong!");
        }
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, takeNode(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T item: list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return takeNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T oldValue = takeNode(index).item;
        takeNode(index).item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T oldValue = get(index);
        unlink(index);
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        int objectIndex = takeNodeIndexByObject(object);
        if (objectIndex != INVALID_INDEX) {
            unlink(objectIndex);
            return true;
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

    private Node<T> takeNode(int index) {
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    private int takeNodeIndexByObject(T object) {
        for (int i = 0; i < size; i++) {
            T nodeValue = takeNode(i).item;
            if (nodeValue == null && nodeValue == object
                    || nodeValue != null && nodeValue.equals(object)) {
                return i;
            }
        }
        return INVALID_INDEX;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("This index '" + index + "' are wrong!");
        }
    }

    private void linkLast(T element) {
        Node<T> prevNode = tail;
        Node<T> newNode = new Node<T>(prevNode, element, null);
        tail = newNode;
        if (prevNode == null) {
            head = newNode;
        } else {
            prevNode.next = newNode;
        }
        size++;
    }

    private void linkBefore(T element, Node<T> actualNode) {
        Node<T> prevNode = actualNode.previous;
        Node<T> newNode = new Node<>(prevNode, element, actualNode);
        actualNode.previous = newNode;
        if (prevNode == null) {
            head = newNode;
        } else {
            prevNode.next = newNode;
        }
        size++;
    }

    private void unlink(int index) {
        if (index == 0 && size > 1) {
            Node<T> newHead = head.next;
            newHead.previous = head.previous;
            head = newHead;
        } else if (index == 0 && size == 1) {
            head.item = null;
            head = null;
            tail = null;
        } else if (index == size - 1) {
            Node<T> last = takeNode(index).previous;
            last.next = takeNode(index).next;
            tail = last;
        } else {
            Node<T> prevNode = takeNode(index).previous;
            Node<T> nextNode = takeNode(index).next;
            prevNode.next = nextNode;
            nextNode.previous = prevNode;
        }
        size--;
    }

    private static class Node<E> {
        private E item;
        private Node<E> previous;
        private Node<E> next;

        public Node(Node<E> previous, E item, Node<E> next) {
            this.item = item;
            this.previous = previous;
            this.next = next;
        }
    }
}
