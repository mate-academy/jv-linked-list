package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> oldLast = last;
        Node<T> newNode = new Node<>(last, value, null);
        last = newNode;
        if (first == null) {
            first = newNode;
        } else {
            oldLast.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
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
        return findElementByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> element = findElementByIndex(index);
        T oldValue = element.value;
        element.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> elementByIndex = findElementByIndex(index);
        unlink(elementByIndex);
        return elementByIndex.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = first;
        for (int i = 0; i < size; i++) {
            if ((node.value != null && node.value.equals(object)) || node.value == object) {
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
        if (size == 1) {
            first = null;
            last = null;
            size--;
            return;
        }
        if (node == first) {
            first = node.next;
            size--;
            return;
        }
        if (node == last) {
            last = node.prev;
            size--;
            return;
        }
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
    }

    private Node<T> findElementByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " "
                    + "is not valid for size: " + size);
        }
        Node<T> node = null;
        if (index <= (size / 2)) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        }
        if (index >= size / 2) {
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private static class Node<E> {
        private E value;
        private Node<E> next;
        private Node<E> prev;

        public Node(Node<E> prev, E element, Node<E> next) {
            this.value = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
