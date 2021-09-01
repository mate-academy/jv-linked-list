package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
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
        } else {
            Node<T> nodeInIndex = findNodeByIndex(index);
            Node<T> valueToAdd;
            if (nodeInIndex.next == null && index == size) {
                valueToAdd = new Node<>(tail, value, null);
                valueToAdd.prev.next = valueToAdd;
                tail = valueToAdd;
            } else {
                valueToAdd = new Node<>(nodeInIndex.prev, value, nodeInIndex);
                if (nodeInIndex.prev == null && size > 0) {
                    head = valueToAdd;
                } else {
                    valueToAdd.prev.next = valueToAdd;
                }
                valueToAdd.next.prev = valueToAdd;
            }
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
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T oldValue = findNodeByIndex(index).item;
        findNodeByIndex(index).item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T removedValue = findNodeByIndex(index).item;
        unlink(findNodeByIndex(index));
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> i = head; i != null; i = i.next) {
            if (object == null) {
                if (object == i.item) {
                    unlink(i);
                    return true;
                }
            } else {
                if (object.equals(i.item)) {
                    unlink(i);
                    return true;
                }
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

    private Node<T> findNodeByIndex(int index) {
        Node<T> placeFinder;
        if ((size / 2) > index) {
            placeFinder = head;
            for (int i = 0; i < index; i++) {
                placeFinder = placeFinder.next;
            }
            return placeFinder;
        }
        placeFinder = tail;
        for (int i = size - 1; i > index; i--) {
            placeFinder = placeFinder.prev;
        }
        return placeFinder;
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
