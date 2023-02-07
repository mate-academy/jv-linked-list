package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    private static class Node<E> {
        E value;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.value = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        Node<T> lastNode = last;
        Node<T> newNode = new Node<>(last, value, null);
        last = newNode;
        if (first == null) {
            first = newNode;
        }
        else {
            lastNode.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        if (index == 0) {
            Node<T> newNode = new Node<>(null, value, first);
            first.prev = newNode;
            first = newNode;
            size++;
            return;
        }
        Node<T> elementByIndex = findElementByIndex(index);
        Node<T> newNode = new Node<>(elementByIndex.prev, value, elementByIndex);
        elementByIndex.prev.next = newNode;
        elementByIndex.prev = newNode;
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
        checkIndex(index);
        return findElementByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T oldValue = findElementByIndex(index).value;
        findElementByIndex(index).value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> elementByIndex = findElementByIndex(index);
        if (elementByIndex.prev == null && elementByIndex.next == null) {
            first = null;
            last = null;
            size--;
            unlink(elementByIndex);
            return elementByIndex.value;
        }
        if (index == 0) {
            first = elementByIndex.next;
            size--;
            unlink(elementByIndex);
            return elementByIndex.value;
        }
        if (elementByIndex.next == null) {
            last = elementByIndex.prev;
            elementByIndex.prev.next = null;
            size--;
            unlink(elementByIndex);
            return elementByIndex.value;
        }
        elementByIndex.prev.next = elementByIndex.next;
        elementByIndex.next.prev = elementByIndex.prev;
        size--;
        unlink(elementByIndex);
        return elementByIndex.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = first;
        for (int i = 0; i < size; i++) {
            if ((node.value != null && node.value.equals(object)) || node.value == object) {
                remove(i);
                unlink(node);
                return true;
            }
            node = node.next;
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

    private void unlink(Node<T> node) {
        node.next = null;
        node.prev = null;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " " + "is not valid.");
        }
    }
    private Node<T> findElementByIndex(int index) {
        Node<T> node = null;
        if (index <= (size / 2)) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        }
        if (index >= size / 2) {
            node = last;
            for (int i = size - 1; i > index ; i--) {
                node = node.prev;
            }
        }
        return node;
    }
}
