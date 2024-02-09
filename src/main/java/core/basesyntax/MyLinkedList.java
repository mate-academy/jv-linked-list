package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        if (first == null) {
            first = last = new Node<>(null, value, null);
            size++;
            return;
        }
        last.next = newNode;
        last = last.next;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        if (size == 0) {
            Node<T> newNode = new Node<>(null, value, null);
            first = newNode;
            last = newNode;
            size++;
            return;
        }
        if (index == 0) {
            first = new Node<>(null, value, first);
            first.next.prev = first;
            size++;
            return;
        }

        Node<T> currentNode = searchNode(index);
        Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
        currentNode.prev.next = newNode;
        currentNode.prev = newNode;
        size++;
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
        Node<T> currentNode = searchNode(index);
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = searchNode(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> currentNode = searchNode(index);
        return unlink(currentNode);
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        while (currentNode != null) {
            if (Objects.equals(currentNode.value, object)) {
                unlink(currentNode);
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
        return (size == 0) ? true : false;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    private T unlink(Node<T> node) {
        Node<T> previous = node.prev;
        Node<T> next = node.next;
        if (previous == null) {
            first = next;
        } else {
            previous.next = next;
            node.prev = null;
        }

        if (next == null) {
            last = previous;
        } else {
            next.prev = previous;
            node.next = next;
        }
        size--;
        return node.value;
    }

    private Node<T> searchNode(int index) {
        Node<T> currentNode;
        if (index > size() / 2) {
            currentNode = searchNodeInTail(index);
        } else {
            currentNode = searchNodeInHead(index);
        }
        return currentNode;
    }

    private Node<T> searchNodeInTail(int index) {
        Node<T> currentNode = last;
        int i = size() - 1;
        while (currentNode != null && i != index) {
            currentNode = currentNode.prev;
            i--;
        }
        return currentNode;
    }

    private Node<T> searchNodeInHead(int index) {
        Node<T> currentNode = first;
        int i = 0;
        while (i != index) {
            currentNode = currentNode.next;
            i++;
        }
        return currentNode;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index does not exist " + index);
        }
    }
}

