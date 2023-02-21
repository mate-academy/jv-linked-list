package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node head;
    private Node tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(tail, value, null);
        if (head == null) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Can't add element on index " + index);
        }
        Node<T> addedNode = findElementByIndex(index);
        Node<T> node = new Node<>(addedNode.prev, value, addedNode);
        if (addedNode.prev == null) {
            head = node;
        } else {
            addedNode.prev.next = node;
        }
        addedNode.prev = node;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T addedElement : list) {
            add(addedElement);
        }
    }

    @Override
    public T get(int index) {
        checkElementByIndex(index);
        return findElementByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkElementByIndex(index);
        Node<T> replacedNode = findElementByIndex(index);
        T valueReturned = replacedNode.value;
        replacedNode.value = value;
        return valueReturned;
    }

    @Override
    public T remove(int index) {
        checkElementByIndex(index);
        Node<T> removedNode = findElementByIndex(index);
        unlink(removedNode);
        return removedNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        while (node != null) {
            if (node.value == object
                    || node.value != null
                    && node.value.equals(object)) {
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

    private Node<T> findElementByIndex(int index) {
        Node<T> nodeValue = null;
        int counter = 0;
        if (index < size / 2) {
            nodeValue = head;
            while (counter != index) {
                nodeValue = nodeValue.next;
                counter++;
            }
        } else {
            nodeValue = tail;
            counter = size - 1;
            while (counter != index) {
                nodeValue = nodeValue.prev;
                counter--;
            }
        }
        return nodeValue;
    }

    private void unlink(Node<T> node) {
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
        size--;
    }

    private void checkElementByIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Incorrect index " + index);
        }
    }

    private class Node<E> {
        private E value;
        private Node<E> next;
        private Node<E> prev;

        public Node(Node<E> prev, E value, Node<E> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
