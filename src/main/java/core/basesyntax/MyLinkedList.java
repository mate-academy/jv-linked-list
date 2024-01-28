package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index == 0 && size > 0) {
            linkFirst(value);
        }
        if (index < size && index > 0) {
            linkBefore(value, node(index));
        }
        if (index == size) {
            linkLast(value);
        }
        checkElementIndex(index);
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return node(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        T oldElement = node(index).element;
        node(index).element = value;
        return oldElement;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(node(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> node = head; node != null; node = node.next) {
            if (node.element == object || node.element != null && node.element.equals(object)) {
                unlink(node);
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
        return size == 0;
    }

    private static class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T element, Node<T> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }
    }

    private void linkFirst(T element) {
        Node<T> firstElement = head;
        Node<T> newNode = new Node<>(null, element, firstElement);
        head = newNode;
        if (firstElement == null) {
            tail = newNode;
        } else {
            firstElement.prev = newNode;
        }
        size++;
    }

    private void linkLast(T element) {
        Node<T> lastElement = tail;
        Node<T> newNode = new Node<>(lastElement, element, null);
        tail = newNode;
        if (lastElement == null) {
            head = newNode;
        } else {
            lastElement.next = newNode;
        }
        size++;
    }

    private void linkBefore(T element, Node<T> current) {
        Node<T> previous = current.prev;
        Node<T> newNode = new Node<>(previous, element, current);
        current.prev = newNode;
        if (previous == null) {
            head = newNode;
        } else {
            previous.next = newNode;
        }
        size++;
    }

    private T unlink(Node<T> node) {
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
        return node.element;
    }

    private void checkElementIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(index + " is invalid index, because "
                    + "size of LinkedList is " + size + ".");
        }
    }

    private Node<T> node(int index) {
        Node<T> element;
        if (index < (size / 2)) {
            element = head;
            for (int i = 0; i < index; i++) {
                element = element.next;
            }
        } else {
            element = tail;
            for (int i = size - 1; i > index; i--) {
                element = element.prev;
            }
        }
        return element;
    }
}
