package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> lastNode = last;
        Node<T> newNode = new Node<>(lastNode,value,null);
        last = newNode;
        if (lastNode == null) {
            first = newNode;
        } else {
            lastNode.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index,size + 1);
        if (index == size) {
            add(value);
        } else {
            Node<T> currentNode = findByIndex(index);
            Node<T> previousNode = currentNode.prev;
            Node<T> newNode = new Node<>(previousNode, value, currentNode);
            currentNode.prev = newNode;
            if (previousNode == null) {
                first = newNode;
            } else {
                previousNode.next = newNode;
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
        checkIndex(index,size);
        return findByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index,size);
        Node<T> newNode = findByIndex(index);
        T oldVal = newNode.item;
        newNode.item = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        checkIndex(index,size);
        return unlink(findByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        while (currentNode != null) {
            if (currentNode.item == null || currentNode.item != null
                    && currentNode.item.equals(object)) {
                unlink(currentNode);
                return true;
            }
            currentNode = currentNode.next;
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

    private void checkIndex(int index, int size) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " out of range.");
        }
    }

    private T unlink(Node<T> deletedNode) {
        final T element = deletedNode.item;
        final Node<T> next = deletedNode.next;
        final Node<T> prev = deletedNode.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            deletedNode.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            deletedNode.next = null;
        }

        size--;
        return element;
    }

    private Node<T> findByIndex(int index) {
        Node<T> wantedNode;
        if (index < (size / 2)) {
            wantedNode = first;
            for (int i = 0; i < index; i++) {
                wantedNode = wantedNode.next;
            }
            return wantedNode;
        }
        wantedNode = last;
        for (int i = size - 1; i > index; i--) {
            wantedNode = wantedNode.prev;
        }
        return wantedNode;
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
}
