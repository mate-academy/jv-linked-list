package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(MyLinkedList.Node<T> prev, T element, MyLinkedList.Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        areIndexInSizeRange(index);
        linkReassignmentForAdd(value, index);
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        areIndexInRange(index);
        return node(index).item;
    }

    @Override
    public T set(T value, int index) {
        areIndexInRange(index);
        final T removedItem = node(index).item;
        node(index).item = value;
        return removedItem;
    }

    @Override
    public T remove(int index) {
        areIndexInRange(index);
        final T removedItem = node(index).item;
        linkReassignmentForRemove(index);
        return removedItem;
    }

    @Override
    public boolean remove(T object) {
        int index = findIndex(object);
        if (index == -1) {
            return false;
        }
        linkReassignmentForRemove(index);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void areIndexInSizeRange(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + " doesn't exist");
        }
    }

    private void areIndexInRange(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + " doesn't exist");
        }
    }

    private int findIndex(T object) {
        Node<T> node = first;
        for (int i = 0; i < size; i++) {
            T currentObject = node.item;
            if (areItemsEqual(currentObject, object)) {
                return i;
            } else {
                node = node.next;
            }
        }
        return -1;
    }

    Node<T> node(int index) {
        Node<T> wantedNode;
        if (index < (size >> 1)) {
            wantedNode = first;
            for (int i = 0; i < index; i++) {
                wantedNode = wantedNode.next;
            }
        } else {
            wantedNode = last;
            for (int i = size - 1; i > index; i--) {
                wantedNode = wantedNode.prev;
            }
        }
        return wantedNode;
    }

    private void linkReassignmentForAdd(T value, int index) {
        if (index == size) {
            linkLast(value);
            return;
        }
        Node<T> indexNode = node(index);
        if (indexNode.prev == null) {
            linkFirst(value, indexNode);
        } else {
            linkBefore(value, indexNode);
        }
    }

    private void linkReassignmentForRemove(int index) {
        Node<T> node = node(index);
        if (index == 0) {
            unlinkFirst(node);
        } else if (index == size - 1) {
            unlinkLast(node);
        } else {
            unlinkBefore(node);
        }
        size--;
    }

    private void unlinkFirst(Node<T> node) {
        Node<T> nextNode = node.next;
        if (nextNode != null) {
            nextNode.prev = null;
            node.next = null;
            first = nextNode;
        } else {
            first = null;
            last = null;
        }
    }

    private void unlinkLast(Node<T> node) {
        Node<T> previousNode = node.prev;
        previousNode.next = null;
        node.prev = null;
        last = previousNode;
    }

    private void unlinkBefore(Node<T> node) {
        Node<T> previousNode = node.prev;
        Node<T> nextNode = node.next;
        previousNode.next = nextNode;
        nextNode.prev = previousNode;
    }

    private void linkFirst(T value, Node<T> node) {
        Node<T> newNode = new Node<>(null, value, node);
        node.prev = newNode;
        first = newNode;
        size++;
    }

    private void linkLast(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        if (last == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    private void linkBefore(T value, Node<T> indexNode) {
        Node<T> newNode = new Node<>(indexNode.prev, value, indexNode);
        indexNode.prev.next = newNode;
        indexNode.prev = newNode;
        size++;
    }

    private boolean areItemsEqual(T currentObject, T object) {
        return (currentObject == object || currentObject != null && currentObject.equals(object));
    }
}
