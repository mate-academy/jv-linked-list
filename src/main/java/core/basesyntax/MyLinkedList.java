package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    @Override
    public boolean add(T value) {
        lastAddByLink(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            lastAddByLink(value);
        } else {
            checkPositionIndex(index);
            addAtIndex(value, findNode(index));
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T element : list) {
            add(link);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkPositionIndex(index);
        return findNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkPositionIndex(index);
        Node<T> setNode = findNode(index);
        T valueSated = setNode.item;
        setNode.item = value;
        return valueSated;
    }

    @Override
    public T remove(int index) {
        checkPositionIndex(index);
        return removeLink(findNode(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> value = head; value != null; value = value.next) {
            if (object == value.item
                    || object != null && object.equals(value.item)) {
                removeLink(value);
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

    public void checkPositionIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds Exception " + index + ".");
        }
    }

    public void lastAddByLink(T value) {
        Node<T> last = tail;
        Node<T> temp = new Node<>(last, value, null);
        tail = temp;
        if (head == null) {
            head = temp;
        } else {
            last.next = temp;
        }
        size++;
    }

    public Node<T> findNode(int index) {
        if (index < (size / 2)) {
            Node<T> tempNodeHead = head;
            for (int i = 0; i < index; i++) {
                tempNodeHead = tempNodeHead.next;
            }
            return tempNodeHead;
        } else {
            Node<T> tempNodeTail = tail;
            for (int i = size - 1; i > index; i--) {
                tempNodeTail = tempNodeTail.previous;
            }
            return tempNodeTail;
        }
    }

    public void addAtIndex(T value, Node<T> tempNode) {
        Node<T> linkedBefore = tempNode.previous;
        Node<T> newNode = new Node<>(linkedBefore, value, tempNode);
        tempNode.previous = newNode;
        if (linkedBefore == null) {
            head = newNode;
        } else {
            linkedBefore.next = newNode;
        }
        size++;
    }

    public T removeLink(Node<T> value) {
        Node<T> nextItem = value.next;
        Node<T> previousItem = value.previous;

        if (previousItem == null) {
            head = nextItem;
        } else {
            previousItem.next = nextItem;
        }

        if (nextItem == null) {
            tail = previousItem;
        } else {
            nextItem.previous = previousItem;
        }
        T element = value.item;
        value.item = null;
        size--;
        return element;
    }

    private static class Node<T> {
        private Node<T> previous;
        private T item;
        private Node<T> next;

        public Node(Node<T> previous, T item, Node<T> next) {
            this.previous = previous;
            this.item = item;
            this.next = next;
        }
    }
}
