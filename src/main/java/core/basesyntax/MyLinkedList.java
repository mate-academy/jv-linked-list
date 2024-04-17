package core.basesyntax;

import java.util.List;
import java.util.NoSuchElementException;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node head;
    private Node tail;
    private int size;

    @Override
    public void add(T value) {
        link(tail, value, null);
    }

    @Override
    public void add(T value, int index) {
        if (size == index) {
            add(value);
            return;
        }
        checkIndex(index);
        Node foundNode = getElementByIndex(index);
        link(foundNode.prev, value, foundNode);
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
        Node foundNode = getElementByIndex(index);
        return foundNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node foundNode = getElementByIndex(index);
        T oldValue = foundNode.value;
        foundNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node foundNode = getElementByIndex(index);
        T value = foundNode.value;
        unlink(foundNode);
        return value;
    }

    @Override
    public boolean remove(T object) {
        Node foundNode = getElementByValue(object);
        if (foundNode == null) {
            return false;
        }
        unlink(foundNode);
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of the bounds");
        }
    }

    private Node getElementByIndex(int index) {
        boolean descendingOrder = index > (size / 2);

        Node node = descendingOrder ? tail : head;
        int currentIndex = descendingOrder ? size - 1 : 0;
        while (node != null) {
            if (index == currentIndex) {
                return node;
            }
            node = descendingOrder ? node.prev : node.next;
            currentIndex = descendingOrder ? currentIndex - 1 : currentIndex + 1;
        }
        throw new NoSuchElementException("There is no such element");
    }

    private Node getElementByValue(T value) {
        Node node = head;
        while (node != null) {
            if (valuesAreEqual(node.value, value)) {
                break;
            }
            node = node.next;
        }
        return node;
    }

    private boolean valuesAreEqual(T firstValue, T secondValue) {
        return firstValue == secondValue || (firstValue != null && firstValue.equals(secondValue));
    }

    private void link(Node prev, T value, Node next) {
        Node newNode = new Node(prev, next, value);

        if (next == null) {
            tail = newNode;
        } else {
            next.prev = newNode;
        }

        if (prev == null) {
            head = newNode;
        } else {
            prev.next = newNode;
        }
        size++;
    }

    private void unlink(Node foundNode) {
        if (foundNode.prev == null) {
            head = foundNode.next;
        } else {
            foundNode.prev.next = foundNode.next;
        }

        if (foundNode.next == null) {
            tail = foundNode.prev;
        } else {
            foundNode.next.prev = foundNode.prev;
        }
        size--;
    }

    private class Node {
        private Node prev;
        private Node next;
        private T value;

        public Node(Node prev, Node next, T value) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }
    }
}
