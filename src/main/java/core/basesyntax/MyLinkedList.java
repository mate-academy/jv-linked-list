package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (head == null && tail == null) {
            head = new Node<T>(null, value, null);
            tail = head;
            size++;
        } else {
            head.next = new Node<T>(head, value, null);
            head = head.next;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0 && head == null && tail == null) {
            head = new Node<T>(null, value, null);
            tail = head;
            size++;
        } else if (index == 0) {
            Node<T> nodeToAdd = new Node<T>(null, value, tail);
            tail.prev = nodeToAdd;
            tail = nodeToAdd;
            size++;
        } else if (index == size) {
            add(value);
        } else {
            Node<T> currentNode = getNode(index);
            Node<T> nodeToAdd = new Node<T>(currentNode.prev, value, currentNode);
            currentNode.prev.next = nodeToAdd;
            currentNode.prev = nodeToAdd;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T el : list) {
            add(el);
        }
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> currentNode = getNode(index);
        Node<T> nodeToSet = new Node<T>(currentNode.prev, value, currentNode.next);
        if (currentNode.next != null) {
            currentNode.next.prev = nodeToSet;
        } else {
            head = nodeToSet;
        }
        if (currentNode.prev != null) {
            currentNode.prev.next = nodeToSet;
        } else {
            tail = nodeToSet;
        }
        return currentNode.item;
    }

    @Override
    public T remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> currentNode = getNode(index);

        remove(currentNode.item);
        return currentNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = getNode(object);
        if (currentNode == null) {
            return false;
        }
        unlink(currentNode);
        size--;
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return tail == null && head == null;
    }

    private Node<T> getNode(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> foundedNode = tail;
        for (int i = 0; i < index; i++) {
            if (foundedNode == null) {
                throw new IndexOutOfBoundsException();
            }
            foundedNode = foundedNode.next;
        }
        return foundedNode;
    }

    private Node<T> getNode(T value) {
        Node<T> currentNode = tail;
        while (!Objects.equals(value, currentNode.item)) {
            currentNode = currentNode.next;
            if (currentNode == null) {
                return currentNode;
            }
        }
        return currentNode;
    }

    private void unlink(Node<T> node) {
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            head = node.prev;
        }
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            tail = node.next;
        }
        node.next = null;
        node.prev = null;
    }

    private class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
