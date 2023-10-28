package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private Node<T> previous;
        private T element;
        private Node<T> next;

        public Node(Node<T> previous, T element, Node<T> next) {
            this.previous = previous;
            this.element = element;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            linkLast(value);
        } else {
            checkIndex(index);
            linkBefore(value, node(index));
        }
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
        return node(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T output = node(index).element;
        node(index).element = value;
        return output;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> nodeTarget = node(index);
        Node<T> firstNode = nodeTarget.previous;
        Node<T> secondNode = nodeTarget.next;
        T element = nodeTarget.element;
        if (index == 0) {
            removeFirst();
            return element;
        }
        if (index == size - 1) {
            removeLast();
            return element;
        }
        firstNode.next = secondNode;
        secondNode.previous = firstNode;
        size--;
        return element;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (node.element == null && object != null) {
                node = node.next;
                continue;
            }
            if (node.element == null && object == null || node.element.equals(object)) {
                remove(i);
                return true;
            } else {
                node = node.next;
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

    private void linkLast(T value) {
        final Node<T> last = tail;
        final Node<T> newNode = new Node<>(tail, value, null);
        tail = newNode;
        if (last == null) {
            head = newNode;
        } else {
            last.next = newNode;
        }
        size++;
    }

    private void linkBefore(T value, Node<T> targetNode) {
        final Node<T> prev = targetNode.previous;
        final Node<T> newNode = new Node<>(prev, value, targetNode);
        targetNode.previous = newNode;
        if (prev == null) {
            head = newNode;
        } else {
            prev.next = newNode;
        }
        size++;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index cannot be equal or bigger than size!");
        }
    }

    private Node<T> node(int index) {
        Node<T> current = head;
        while (index != 0) {
            current = current.next;
            index = index - 1;
        }
        return current;
    }

    private void removeFirst() {
        if (size == 1) {
            head = null;
            tail = null;
            size--;
            return;
        }
        head = head.next;
        head.previous = null;
        size--;
    }

    private void removeLast() {
        tail = tail.previous;
        tail.next = null;
        size--;
    }
}
