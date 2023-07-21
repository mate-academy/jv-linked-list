package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        addLastEntry(value);
    }

    @Override
    public void add(T value, int index) {
        if (index == 0) {
            addFirstEntry(value);
        } else if (index == size) {
            addLastEntry(value);
        } else {
            addByIndexEntry(value, index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            addLastEntry(element);
        }
    }

    @Override
    public T get(int index) {
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> setNode = findNodeByIndex(index);
        T element = setNode.item;
        setNode.item = value;
        return element;
    }

    @Override
    public T remove(int index) {
        Node<T> removeNode = findNodeByIndex(index);
        unlink(removeNode);
        return removeNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> removeNode = head;
        for (int i = 0; i < size; i++) {
            if (removeNode.item == object || removeNode.item != null
                    && removeNode.item.equals(object)) {
                unlink(removeNode);
                return true;
            }
            removeNode = removeNode.next;
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index:" + index + ", Size:"
                                                + size);
        }
    }

    private void unlink(Node<T> node) {
        if (node.previous != null) {
            node.previous.next = node.next;
        } else {
            head = node.next;
        }

        if (node.next != null) {
            node.next.previous = node.previous;
        } else {
            tail = node.previous;
        }
        size--;
    }

    private void addLastEntry(T element) {
        Node<T> newLastNode = new Node<>(tail, element, null);
        if (tail != null) {
            tail.next = newLastNode;
        }
        if (head == null) {
            head = newLastNode;
        }
        tail = newLastNode;
        size++;
    }

    private void addFirstEntry(T element) {
        Node<T> newFirstNode = new Node<>(null, element, head);
        if (head != null) {
            head.previous = newFirstNode;
        }
        if (tail == null) {
            tail = newFirstNode;
        }
        head = newFirstNode;
        size++;
    }

    private void addByIndexEntry(T element, int index) {
        Node<T> nodeRight = findNodeByIndex(index);
        Node<T> nodeLeft = new Node<T>(nodeRight.previous, element, nodeRight.next);
        nodeRight.previous.next = nodeLeft;
        nodeRight.previous = nodeLeft;
        size++;
    }

    private Node<T> findNodeByIndex(int index) {
        checkIndex(index);
        if (index < size / 2) {
            return scrollFirstPart(head, index);
        }
        return scrollSecondPart(tail, size - (++index));
    }

    private Node<T> scrollFirstPart(Node<T> first, int stepOnIndex) {
        if (stepOnIndex == 0) {
            return first;
        }
        stepOnIndex--;
        return scrollFirstPart(first.next, stepOnIndex);
    }

    private Node<T> scrollSecondPart(Node<T> last, int stepOnIndex) {
        if (stepOnIndex == 0) {
            return last;
        }
        stepOnIndex--;
        return scrollSecondPart(last.previous, stepOnIndex);
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> previous;

        Node(Node<T> prev, T element, Node<T> next) {
            this.previous = prev;
            this.item = element;
            this.next = next;
        }
    }
}
