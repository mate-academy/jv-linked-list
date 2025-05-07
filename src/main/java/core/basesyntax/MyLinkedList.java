package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        if (head == null) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            Node<T> element = new Node<>(tail, value, null);
            tail.next = element;
            tail = element;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> nodeByIndex;
        if (index == size) {
            add(value);
            return;
        } else {
            nodeByIndex = getNodeAndCheckIndex(index);
        }
        if (index == 0) {
            head = new Node<>(null, value, nodeByIndex);
            nodeByIndex.prev = head;
        } else {
            Node<T> previous = nodeByIndex.prev;
            Node<T> newNode = new Node<>(previous, value, nodeByIndex);
            previous.next = newNode;
            nodeByIndex.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element: list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        Node<T> nodeByIndex = getNodeAndCheckIndex(index);
        return nodeByIndex.item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> nodeByIndex = getNodeAndCheckIndex(index);
        T oldValue = nodeByIndex.item;
        nodeByIndex.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> removedNote = getNodeAndCheckIndex(index);
        unlink(removedNote);
        size--;
        return removedNote.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> target = head;
        while (target != null) {
            if (target.item == object
                    || target.item != null && target.item.equals(object)) {
                unlink(target);
                size--;
                return true;
            }
            target = target.next;
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

    private void checkIndexOfNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
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

    private Node<T> getNodeAndCheckIndex(int index) {
        checkIndexOfNode(index);
        Node<T> target = null;
        int counter;

        if (index <= (size / 2)) {
            target = head;
            counter = 0;
            while (counter < index) {
                target = target.next;
                counter++;
            }
        } else if (index > (size / 2)) {
            target = tail;
            counter = size - 1;
            while (counter > index) {
                target = target.prev;
                counter--;
            }
        }

        return target;
    }

    private void unlink(Node<T> removedNote) {
        if (removedNote.prev == null && removedNote.next == null) {
            head = null;
            tail = null;
        } else if (removedNote.prev == null) {
            head = head.next;
            head.prev = null;
        } else if (removedNote.next == null) {
            tail = tail.prev;
            tail.next = null;
        } else {
            Node<T> previousNode = removedNote.prev;
            Node<T> nextNode = removedNote.next;
            removedNote.next = null;
            removedNote.prev = null;
            previousNode.next = nextNode;
            nextNode.prev = previousNode;
        }
    }
}
