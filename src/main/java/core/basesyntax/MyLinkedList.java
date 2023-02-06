package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;
    private Node<T> element;

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
        Node<T> newNode = new Node<>(last, value, null);
        last = newNode;
        if (isEmpty()) {
            first = newNode;
            element = newNode;
            size++;
            return;
        }
        element.next = newNode;
        element = newNode;
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
        if (index == 0) {
            T returnValue = first.value;
            first = first.next;
            if (first == null) {
                element = null;
                last = null;
                size--;
                return returnValue;
            }
            first.prev = null;
            size--;
            return returnValue;
        }
        checkIndex(index);
        if (index > 1 && index == size - 1) {
            Node<T> elementByIndex = findElementByIndex(index);

            last = elementByIndex.prev;
            elementByIndex.prev.next = null;
            size--;
            return elementByIndex.value;
        }
        Node<T> elementByIndex = findElementByIndex(index);
        elementByIndex.prev.next = elementByIndex.next;
        elementByIndex.next.prev = elementByIndex.prev;
        size--;
        return elementByIndex.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = first;
        for (int i = 0; i < size; i++) {
            if ((node.value != null && node.value.equals(object)) || node.value == null && object == null) {
                remove(i);
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
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " " + "is not valid.");
        }
    }
    private Node<T> findElementByIndex(int index) {
        Node<T> node = first;
        if (index == 0) {
            return node;
        }
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }
}
