package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        if (size != 0) {
            getInLast(last, value);
        } else {
            Node<T> newNode = new Node<>(null, value, null);
            size++;
            last = newNode;
            first = newNode;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (0 <= index && index < size) {
            Node<T> item = getNodeByIndex(index);
            if (item == first) {
                getInFirst(item, value);
                return;
            }
            getInMiddle(item, value);
            return;
        }
        throw new IndexOutOfBoundsException("You can not insert in "
                + index + " only 0 - " + (size - 1));
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        isElementIndex(index);
        Node<T> item = getNodeByIndex(index);
        return item.item;
    }

    @Override
    public T set(T value, int index) {
        isElementIndex(index);
        MyLinkedList.Node<T> item = getNodeByIndex(index);
        T oldValue = item.item;
        item.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeToDelete = getNodeByIndex(index);
        T removeItemValue = nodeToDelete.item;
        getOutNode(nodeToDelete);
        return removeItemValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> item = first;
        for (int i = 0; i < size; i++) {
            if (object == item.item || item.item != null && item.item.equals(object)) {
                getOutNode(item);
                return true;
            }
            item = item.next;
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

    private boolean isElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return true;
    }

    private Node<T> getNodeByIndex(int index) {
        isElementIndex(index);
        if (index < (size >> 1)) {
            Node<T> x = first;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        } else {
            Node<T> x = last;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }

    }

    private void getInLast(Node<T> last, T value) {
        Node<T> newNode = new Node<>(last, value, null);
        last.next = newNode;
        this.last = newNode;
        size++;
    }

    private void getInMiddle(Node<T> item, T value) {
        Node<T> newNode = new Node<>(item.prev, value, item);
        newNode.prev.next = newNode;
        item.prev = newNode;
        size++;
    }

    private void getInFirst(Node<T> first, T value) {
        Node<T> newNode = new Node<>(null, value, first);
        first.prev = newNode;
        this.first = newNode;
        size++;
    }

    private void getOutNode(Node<T> nodeToDelete) {
        size--;
        if (size == 0) {
            first = null;
            last = null;
            return;
        }
        if (nodeToDelete == first) {
            first = nodeToDelete.next;
            first.prev = null;
            return;
        }
        if (nodeToDelete == last) {
            last = nodeToDelete.prev;
            last.next = null;
            return;
        }
        nodeToDelete.next.prev = nodeToDelete.prev;
        nodeToDelete.prev.next = nodeToDelete.next;
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
