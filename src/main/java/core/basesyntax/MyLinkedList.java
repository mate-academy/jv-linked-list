package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, null,tail);
        if (size == 0) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        validateIndex(index);
        Node<T> prevNode = getNodeByIndex(index);
        Node<T> newNode = new Node<>(value, prevNode, prevNode.prev);
        if (index == 0) {
            head = newNode;
        } else {
            prevNode.prev.next = newNode;
        }
        prevNode.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T listElement : list) {
            add(listElement);
        }
    }

    @Override
    public T get(int index) {
        validateIndex(index);
        return getNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index);
        Node<T> node = getNodeByIndex(index);
        T oldNode = node.element;
        node.element = value;
        return oldNode;
    }

    @Override
    public T remove(int index) {
        validateIndex(index);
        Node<T> removedElement = getNodeByIndex(index);
        unlink(removedElement);
        return removedElement.element;
    }

    @Override
    public boolean remove(T object) {
        Node<T> remove = head;
        int rem = 0;
        do {
            if (object == remove.element
                    || remove.element != null && remove.element.equals(object)) {
                remove(rem);
                return true;
            }
            rem++;
            remove = remove.next;
        } while (remove.next != null);
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    private static class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> prev;

        public Node(T element, Node<T> next, Node<T> prev) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("Index out of bound: " + index);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private void unlink(Node<T> node) {
        if (node.prev == null) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node.next == null) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        size--;
    }
}
