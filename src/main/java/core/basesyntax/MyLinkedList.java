package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (head == null) {
            head = tail = newNode;
        } else {
            newNode = new Node(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode = new Node<>(null, value, null);
        checkIndexForAdd(index);
        if (head == null) {
            head = tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else if (index == size) {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        } else {
            Node<T> previous = node(index - 1);
            newNode = new Node<>(previous, value, previous.next);
            previous.next.prev = newNode;
            previous.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return node(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = node(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(node(index));
        /* T removedElement;
        if (index == 0) {
            removedElement = head.value;
            head = head.next;
            if (head == null) {
                tail = null;
            }
        } else {
            Node<T> previous = getByIndex(index - 1);
            removedElement = previous.next.value;
            previous.next = previous.next.next;
            if (index == (size - 1)) {
                tail = previous;
            }
        }
        size--;
        return removedElement; */
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> currentNode = head; currentNode != null; currentNode = currentNode.next) {
                if (currentNode.value == null) {
                    unlink(currentNode);
                    return true;
                }
            }
        } else {
            for (Node<T> currentNode = head; currentNode != null; currentNode = currentNode.next) {
                if (object.equals(currentNode.value)) {
                    unlink(currentNode);
                    return true;
                }
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
        return head == null;
    }

    private Node<T> node(int index) {
        /* Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode; */
        Node<T> currentNode;
        if (index < (size >> 1)) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of range: " + index);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range: " + index);
        }
    }

    private T unlink(Node<T> node) {
        final T element = node.value;
        final Node<T> next = node.next;
        final Node<T> prev = node.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }

        node.value = null;
        size--;
        return element;
    }
}
