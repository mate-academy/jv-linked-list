package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public boolean add(T value) {
        Node<T> node = new Node<>(last, value, null);
        if (size == 0) {
            first = node;
        } else {
            last.next = node;
        }
        last = node;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            Node<T> node = getNode(index);
            Node<T> previousNode = node.prev;
            Node<T> newNode = new Node<>(previousNode, value, node);
            node.prev = newNode;
            if (index == 0) {
                first = newNode;
            } else {
                previousNode.next = newNode;
            }
            size++;
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
        return true;
    }

    @Override
    public T get(int index) {
        Node<T> node = getNode(index);
        return node.element;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T oldElement = node.element;
        node.element = value;
        return oldElement;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = getNode(index);
        Node<T> previousNode = node.prev;
        Node<T> nextNode = node.next;
        if (index == 0) {
            first = nextNode;
        } else {
            previousNode.next = nextNode;
        }
        if (index == size - 1) {
            last = previousNode;
        } else {
            nextNode.prev = previousNode;
            node.next = null;
        }
        size--;
        return node.element;
    }

    @Override
    public boolean remove(T t) {
        Node<T> currentNode = first;
        for (int i = 0; i < size; i++) {
            if (t == currentNode.element || currentNode.element.equals(t)) {
                remove(i);
                return true;
            }
            currentNode = currentNode.next;
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
        T element;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> currentNode;
        if (index < size / 2) {
            currentNode = first;
            for (int i = 0; i != index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = last;
            for (int i = size - 1; i != index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Wrong index of list");
        }
    }
}
