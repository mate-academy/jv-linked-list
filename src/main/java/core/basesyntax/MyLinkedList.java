package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> head;
    private Node<T> tail;

    public MyLinkedList() {
    }

    @Override
    public void add(T value) {
        add(value,size);
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (size == 0) {
            Node<T> temporaryHead = head;
            Node<T> firstNodeToAdd = new Node<>(null, value, temporaryHead);
            tail = firstNodeToAdd;
            head = firstNodeToAdd;
            size++;
        } else if (size > 0 && index == 0) {
            Node<T> newHead = new Node<>(null, value, head);
            newHead.next.prev = newHead;
            head = newHead;
            size++;
        } else if (size > 0 && index == size) {
            Node<T> newTail = new Node<T>(tail, value, null);
            newTail.prev.next = newTail;
            tail = newTail;
            size++;
        } else {
            Node<T> valueToAdd = new Node<>(elementFinder(index).prev, value, elementFinder(index));
            valueToAdd.prev.next = valueToAdd;
            valueToAdd.next.prev = valueToAdd;
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
        checkIndex(index);
        return elementFinder(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T oldValue = elementFinder(index).item;
        elementFinder(index).item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T removedValue = elementFinder(index).item;
        unlink(elementFinder(index));
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> i = head; i != null; i = i.next) {
                if (object == i.item) {
                    unlink(i);
                    return true;
                }
            }
            return false;
        } else {
            for (Node<T> i = head; i != null; i = i.next) {
                if (object.equals(i.item)) {
                    unlink(i);
                    return true;
                }
            }
            return false;
        }
    }

    private T unlink(Node<T> nodeToUnlink) {
        final T unlinkedElement = nodeToUnlink.item;
        Node<T> previos = nodeToUnlink.prev;
        Node<T> next = nodeToUnlink.next;
        if (previos == null) {
            head = next;
        } else {
            previos.next = next;
            nodeToUnlink.prev = null;
        }
        if (next == null) {
            tail = previos;
        } else {
            next.prev = previos;
            nodeToUnlink.next = null;
        }
        nodeToUnlink.item = null;
        size--;
        return unlinkedElement;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("can not reach element with this index");
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("can not reach element with this index");
        }
    }

    private Node<T> elementFinder(int index) {
        Node<T> placeFinder;
        if ((size / 2) > index) {
            placeFinder = head;
            for (int i = 0; i < index; i++) {
                placeFinder = placeFinder.next;
            }
            return placeFinder;
        } else {
            placeFinder = tail;
            for (int i = size - 1; i > index; i--) {
                placeFinder = placeFinder.prev;
            }
            return placeFinder;
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}
