package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        linkTail(value);
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            linkTail(value);
            return;
        }

        Node<T> tempNode = getNodeByIndex(index);
        Node<T> newNode = new Node<>(tempNode.prev, value, tempNode);

        if (tempNode.prev == null) {
            head = newNode;
        } else {
            tempNode.prev.next = newNode;
        }
        tempNode.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> changedNode = getNodeByIndex(index);
        T removedItem = changedNode.item;
        changedNode.item = value;
        return removedItem;
    }

    @Override
    public T remove(int index) {
        Node<T> removeNode = getNodeByIndex(index);
        T removeItems = removeNode.item;
        unlink(removeNode);
        return removeItems;
    }

    @Override
    public boolean remove(T object) {
        Node<T> serchedNode = head;
        for (int i = 0; i < size; i++) {
            if ((serchedNode.item != null && serchedNode.item.equals(object))
                    || serchedNode.item == object) {
                unlink(serchedNode);
                return true;
            }
            serchedNode = serchedNode.next;
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

    private void unlink(Node<T> element) {
        if (element == head) {
            head = element.next;
        } else if (element == tail) {
            tail = element.prev;
        } else {
            element.prev.next = element.next;
            element.next.prev = element.prev;
        }

        element.item = null;
        size--;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index" + index
                    + " out of bound. List size = " + size);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        if (index <= (size >> 1)) {
            Node<T> tempNode = head;
            for (int i = 0; i < index; i++) {
                tempNode = tempNode.next;
            }
            return tempNode;
        }

        Node<T> tempNode = tail;
        for (int i = size - 1; i > index; i--) {
            tempNode = tempNode.prev;
        }
        return tempNode;
    }

    private void linkTail(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}
