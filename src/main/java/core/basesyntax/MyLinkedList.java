package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node tail;
    private Node head;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            head = new Node(null, value, null);
            tail = head;
        } else {
            head.next = new Node(head, value, null);
            head = head.next;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index + " for size: " + size);
        }
        if (index == size) {
            add(value);
        } else if (index == 0) {
            tail = new Node(null, value, tail);
        } else {
            Node nextNode = getNode(index);
            nextNode.prev.next = new Node(nextNode.prev, value, nextNode);
            nextNode.prev = nextNode.prev.next;
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
        checkIndexValidation(index);
        return getNode(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkIndexValidation(index);
        T oldValue = get(index);
        getNode(index).element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexValidation(index);
        Node node = getNode(index);
        T removedElement = get(index);
        if (node == tail) {
            if (size == 1) {
                tail = null;
            } else {
                tail = node.next;
                tail.prev = null;
            }
        } else if (node == head) {
            head = node.prev;
            head.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
        return removedElement;
    }

    @Override
    public boolean remove(T object) {
        Node node = tail;
        for (int i = 0; node != null; i++) {
            if (node.element == object
                    || node.element != null && node.element.equals(object)) {
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

    private void checkIndexValidation(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index + " for size: " + size);
        }
    }

    private Node getNode(int index) {
        if (index == 0) {
            return tail;
        }
        if (index == size - 1) {
            return head;
        }
        if (index < size / 2) {
            Node node = tail;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        }
        Node node = head;
        for (int i = size - 1; i > index; i--) {
            node = node.prev;
        }
        return node;
    }

    private class Node {
        private T element;
        private Node prev;
        private Node next;

        private Node(Node prev, T element, Node next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
            size++;
        }
    }
}
