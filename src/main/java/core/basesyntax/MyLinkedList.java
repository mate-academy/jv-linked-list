package core.basesyntax;

import java.util.List;
import java.util.NoSuchElementException;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node head;
    private Node tail;
    private int size;

    @Override
    public void add(T value) {
        if (head == null) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            Node<T> node = new Node<>(tail, value, null);
            tail.next = node;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("");
        }

        if (index == size) {
            add(value);
        } else {
            Node<T> nodeAtIndex = findNodeByIndex(index);
            Node<T> currentNode = new Node<>(nodeAtIndex.prev, value, nodeAtIndex);

            if (nodeAtIndex.prev != null) {
                nodeAtIndex.prev.next = currentNode;
            } else {
                head = currentNode;
            }
            nodeAtIndex.prev = currentNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = findNodeByIndex(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeAtIndex = findNodeByIndex(index);
        unlink(nodeAtIndex);
        return nodeAtIndex.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = findNodeByValue(object);
        if (currentNode != null) {
            unlink(currentNode);
            return true;
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

    private Node<T> findNodeByValue(T item) {
        Node<T> node = head;
        while (node != null) {
            if ((item == null && node.value == null) || (item != null && item.equals(node.value))) {
                return node;
            }
            node = node.next;
        }
        return null;
    }

    private Node<T> findNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("");
        }

        if (head == null) {
            throw new NoSuchElementException("");
        }

        Node<T> node;
        if (index <= size / 2) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    private void unlink(Node<T> node) {
        if (node == null) {
            return;
        }

        Node<T> prev = node.prev;
        Node<T> next = node.next;

        if (prev == null) {
            head = next;
        }

        if (next == null) {
            tail = prev;
        }

        if (prev != null) {
            prev.next = next;
            node.prev = null;
        }

        if (next != null) {
            next.prev = prev;
            node.next = null;
        }

        size--;
    }

    private static class Node<T> {
        private T value;
        private Node next;
        private Node prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.next = next;
            this.value = element;
            this.prev = prev;
        }
    }
}
