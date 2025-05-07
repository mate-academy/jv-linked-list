package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    public MyLinkedList() {
    }

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            linkLast(value);
            return;
        }
        if (index == 0) {
            linkFirst(value);
            return;
        }
        Node<T> newNode = new Node<>(null, value, null);
        Node<T> previous = getNodeByIndex(index - 1);
        Node<T> next = previous.next;
        newNode.prev = previous;
        newNode.next = next;
        previous.next = newNode;
        next.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> nodeForSet = getNodeByIndex(index);
        T item = nodeForSet.item;
        nodeForSet.item = value;
        return item;
    }

    @Override
    public T remove(int index) {
        checkForIndexOutOfBounds(index);
        Node<T> forRemoval;
        if (index == 0) {
            forRemoval = first;
            first = first.next;
            if (first != null) {
                first.prev = null;
            }
            size--;
            return forRemoval.item;
        }
        if (index == size - 1) {
            forRemoval = last;
            last = last.prev;
            if (last != null) {
                last.next = null;
            }
            size--;
            return forRemoval.item;
        }
        forRemoval = getNodeByIndex(index);
        Node<T> previous = forRemoval.prev;
        Node<T> next = forRemoval.next;
        previous.next = next;
        next.prev = previous;
        size--;
        return forRemoval.item;
    }

    @Override
    public boolean remove(T object) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            Node<T> node = getNodeByIndex(i);
            if (node.item == object || object != null && object.equals(node.item)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            return false;
        }
        remove(index);
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

    private void linkFirst(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        first.prev = newNode;
        newNode.next = first;
        first = newNode;
        size++;
    }

    private void linkLast(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (isEmpty()) {
            first = newNode;
            last = newNode;
            size++;
            return;
        }
        newNode.prev = last;
        last.next = newNode;
        last = newNode;
        size++;
    }

    private Node<T> getNodeByIndex(int index) {
        checkForIndexOutOfBounds(index);
        int middle = size / 2;
        return index < middle ? iterateFromHead(index) : iterateFromTail(index);
    }

    private Node<T> iterateFromHead(int index) {
        Node<T> result = first;
        for (int i = 1; i <= index; i++) {
            result = result.next;
        }
        return result;
    }

    private Node<T> iterateFromTail(int index) {
        Node<T> result = last;
        for (int i = size - 1; i > index; i--) {
            result = result.prev;
        }
        return result;
    }

    private void checkForIndexOutOfBounds(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds");
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
}
