package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T element;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }
    }

    @Override
    public String toString() {
        Node<T> node = head;
        System.out.print("[");
        do {
            System.out.print(node.element + " ");
            node = node.next;
        } while (node != tail);
        System.out.println(tail.element + "]");
        return null;
    }

    @Override
    public void add(T value) {
        if (isEmpty()) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            tail = new Node<>(tail, value, null);
            tail.prev.next = tail;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode;
        Node<T> oldNode;
        if ((index > size) || (index < 0)) {
            throw new IndexOutOfBoundsException("Can't add element - index bigger than size");
        }
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
            size++;
        }
        if (index == size - 1) {
            newNode = new Node<>(tail.prev, value, tail);
            tail.prev.next = newNode;
            tail.prev = newNode;
            size++;
        }
        if ((index > size / 2)) {
            oldNode = iterateFromTail(size - index);
            addLink(oldNode, value);
            size++;
        } else if ((index < size / 2) && (index > 0)) {
            oldNode = iterateFromHead(index).prev;
            addLink(oldNode, value);
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T listElement: list) {
            add(listElement);
        }
    }

    @Override
    public T get(int index) {
        if ((index >= size) || (index < 0)) {
            throw new IndexOutOfBoundsException("Can't get element - index bigger than size");
        }
        if (index == 0) {
            return head.element;
        }
        if (index == size - 1) {
            return tail.element;
        }
        if ((index > size / 2) && (index < size - 1)) {
            return iterateFromTail(size - index).next.element;
        }
        return iterateFromHead(index).element;
    }

    @Override
    public T set(T value, int index) {
        T element;
        if ((index >= size) || (index < 0)) {
            throw new IndexOutOfBoundsException("Can't set element - index bigger than size");
        }
        if (index == 0) {
            element = head.element;
            head.element = value;
            return element;
        }
        if (index == size - 1) {
            element = tail.element;
            tail.element = value;
            return element;
        }
        if ((index > size / 2) && (index < size - 1)) {
            element = iterateFromTail(size - index).next.element;
            iterateFromTail(size - index).next.element = value;
            return element;
        }
        element = iterateFromHead(index).element;
        iterateFromHead(index).element = value;
        return element;
    }

    @Override
    public T remove(int index) {
        Node<T> oldNode;
        T element;
        if ((index >= size) || (index < 0)) {
            throw new IndexOutOfBoundsException("Can't remove element - index bigger than size");
        }
        if ((index == 0) && (size == 1)) {
            head = null;
            tail = null;
            size--;
            return null;
        }
        if (index == 0) {
            element = head.element;
            head = head.next;
            head.prev = null;
            size--;
            return element;
        }
        if (index == size - 1) {
            element = tail.element;
            tail = tail.prev;
            tail.next = null;
            size--;
            return element;
        }
        if (index > size / 2) {
            oldNode = iterateFromTail(size - index).next;
            element = oldNode.element;
            removeLink(oldNode);
            size--;
            return element;
        }
        oldNode = iterateFromHead(index);
        element = oldNode.element;
        removeLink(oldNode);
        size--;
        return element;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        boolean equal = (head.element == null) ? (object == null) : (head.element.equals(object));
        if ((size == 1) && equal) {
            head = null;
            tail = null;
            size--;
            return true;
        }
        if (equal) {
            head.next.prev = null;
            head = head.next;
            size--;
            return true;
        }
        for (int i = 1; i < size; i++) {
            node = node.next;
            equal = (node.element == null) ? (object == null) : (node.element.equals(object));
            if (equal) {
                removeLink(node);
                size--;
                return true;
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
        return (size == 0);
    }

    private Node<T> iterateFromHead(int index) {
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    private Node<T> iterateFromTail(int index) {
        Node<T> node = tail;
        for (int i = 0; i < index; i++) {
            node = node.prev;
        }
        return node;
    }

    private void addLink(Node<T> oldNode, T value) {
        Node<T> newNode = new Node<>(oldNode, value, oldNode.next);
        oldNode.next = newNode;
        newNode.next.prev = newNode;
    }

    private void removeLink(Node<T> oldNode) {
        if (oldNode.next == null) { // oldNode == tail
            oldNode.prev.next = null;
            tail = oldNode.prev;
        }
        if (size > 1) {
            oldNode.prev.next = oldNode.next;
            oldNode.next.prev = oldNode.prev;
        }
        if (size == 1) {
            head = null;
            tail = null;
        }
    }
}
