package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public boolean add(T value) {
        Node<T> current = new Node<>(tail, value, null);
        if (size == 0) {
            head = current;
        } else {
            tail.next = current;
        }
        tail = current;
        size++;
        return true;

    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkElementIndex(index);
        Node<T> oldNode = getNode(index);
        Node<T> newNode = new Node<>(oldNode.previous, value, oldNode);
        if (index > 0) {
            newNode.previous.next = newNode;
        } else {
            head = newNode;
        }
        oldNode.previous = newNode;
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return getNode(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> node = getNode(index);
        T oldVal = node.element;
        node.element = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        Node<T> node = getNode(index);
        if (index > 0) {
            unlink(node);
        } else {
            head = node.next;
            size--;
        }
        return node.element;
    }

    @Override
    public boolean remove(T t) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (node.element == null || node.element == t || node.element.equals(t)) {
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

    private class Node<T> {
        T element;
        Node<T> next;
        Node<T> previous;

        Node(Node<T> previous, T element, Node<T> next) {
            this.element = element;
            this.next = next;
            this.previous = previous;
        }
    }

    private Node<T> getNode(int index) {
        Node<T> node;
        if (index < (size >> 1)) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.previous;
            }
        }
        return node;
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void unlink(Node<T> nodeToRemove) {
        nodeToRemove.previous.next = nodeToRemove.next;
        if (nodeToRemove.next != null) {
            nodeToRemove.next.previous = nodeToRemove.previous;
        } else {
            tail = nodeToRemove.previous;
        }
        size--;
    }
}
