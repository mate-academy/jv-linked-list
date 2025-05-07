package core.basesyntax;

import java.util.List;
import java.util.NoSuchElementException;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(null, null, value);
        if (head == null) {
            head = tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index isn`t fit for adding");
        }

        if (index == size) {
            add(value);
            return;
        }

        Node<T> foundNode = getElementByIndex(index);
        Node<T> newNode = new Node<>(foundNode.prev, foundNode, value);

        if (foundNode.prev == null) {
            head = newNode;
        } else {
            foundNode.prev.next = newNode;
        }

        foundNode.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> foundNode = getElementByIndex(index);
        return foundNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> foundNode = getElementByIndex(index);
        T oldValue = foundNode.value;
        foundNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> foundNode = getElementByIndex(index);
        T value = foundNode.value;
        unlink(foundNode);
        return value;
    }

    @Override
    public boolean remove(T object) {

        Node<T> foundNode = head;
        while (foundNode != null) {
            if (valuesAreEqual(foundNode.value, object)) {
                unlink(foundNode);
                return true;
            }
            foundNode = foundNode.next;
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of the bounds");
        }
    }

    private Node<T> getElementByIndex(int index) {
        boolean descendingOrder = index > (size / 2);

        Node<T> node = descendingOrder ? tail : head;
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

    private boolean valuesAreEqual(T firstValue, T secondValue) {
        return firstValue == secondValue || (firstValue != null && firstValue.equals(secondValue));
    }

    private void unlink(Node<T> foundNode) {
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

    private static class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T value;

        public Node(Node<T> prev, Node<T> next, T value) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }
    }
}
