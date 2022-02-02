package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.prev = prev;
            this.next = next;
        }
    }

    private void addFirst(T t) {
        Node<T> newNode;
        if (first == null) {
            newNode = new Node<>(null, t, null);
            last = newNode;
        } else {
            newNode = new Node<>(null, t, first);
            first.prev = newNode;
        }
        first = newNode;
        size++;
    }

    private void addLast(T t) {
        Node<T> newNode;
        if (last == null) {
            newNode = new Node<>(null, t, null);
            first = newNode;
        } else {
            newNode = new Node<>(last, t, null);
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    private void addInTheMiddle(T t, int index) {
        Node<T> newNode;
        Node<T> currentNode = getNodeByIndex(index);
        newNode = new Node<>(currentNode.prev, t, currentNode);
        currentNode.prev.next = newNode;
        currentNode.prev = newNode;
        size++;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> currentNode = first;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private void checkIndex(int index, boolean isStrict) {
        if (index < 0 || index > size || !isStrict && index == size) {
            throw new IndexOutOfBoundsException("IndexOutOfBoundsException " + index);
        }
    }

    private void checkItems() {
        if (first == null && last == null) {
            throw new IndexOutOfBoundsException("There is no items in list");
        }
    }

    private T unlink(Node<T> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
        return node.item;
    }

    @Override
    public void add(T value) {
        addLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, true);
        if (index == size) {
            addLast(value);
        } else if (index == 0) {
            addFirst(value);
        } else {
            addInTheMiddle(value, index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element: list) {
            addLast(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index, false);
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        T prev;
        checkIndex(index, false);
        Node<T> node = getNodeByIndex(index);
        prev = node.item;
        node.item = value;
        return prev;
    }

    private T removeFirst() {
        checkItems();
        T t = first.item;
        if (size == 1) {
            first = null;
            last = null;
        } else {
            first.next.prev = null;
            first = first.next;
        }
        size--;
        return t;
    }

    private T removeLast() {
        checkItems();
        T t = last.item;
        if (size == 1) {
            first = null;
            last = null;
        } else {
            last.prev.next = null;
        }
        size--;
        return t;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, false);
        if (index == 0) {
            return removeFirst();
        } else if (index == size - 1) {
            return removeLast();
        } else {
            return unlink(getNodeByIndex(index));
        }
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        for (int i = 0; i < size; i++) {
            if (object == currentNode.item
                    || object != null && currentNode.item.equals(object)) {
                remove(i);
                return true;
            } else {
                currentNode = currentNode.next;
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
        return size == 0;
    }
}
