package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        if (first == null) {
            first = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }

        Node<T> tempNode = getNodeByIndex(index);
        Node<T> newNode = new Node<>(tempNode.prev, value, tempNode);

        if (tempNode.prev == null) {
            first = newNode;
        } else {
            tempNode.prev.next = newNode;
        }
        tempNode.prev = newNode;
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
        checkIndex(index);
        Node<T> changedNode = getNodeByIndex(index);
        T removedItem = changedNode.item;
        changedNode.item = value;
        return removedItem;
    }

    @Override
    public T remove(int index) {
        T removeNode = getNodeByIndex(index).item;
        unlink(getNodeByIndex(index));
        return removeNode;
    }

    @Override
    public boolean remove(T object) {
        Node<T> serchedNode = first;
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
        if (element == first) {
            first = element.next;
        } else if (element == last) {
            last = element.prev;
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
            Node<T> tempNode = first;
            for (int i = 0; i < index; i++) {
                tempNode = tempNode.next;
            }
            return tempNode;
        }

        if (index >= (size >> 1)) {
            Node<T> tempNode = last;
            for (int i = size - 1; i > index; i--) {
                tempNode = tempNode.prev;
            }
            return tempNode;
        }
        return null;
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
