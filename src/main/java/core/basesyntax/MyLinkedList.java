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
        if (size == 0) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Unable to add index: " + index);
        }
        Node<T> currentNode = findNodeAtIndex(index);
        Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
        if (currentNode.prev == null) {
            first = newNode;
        } else {
            currentNode.prev.next = newNode;
        }
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
        Node<T> currentNode = findNodeAtIndex(index);
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = findNodeAtIndex(index);
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> findedNode = findNodeAtIndex(index);
        unlink(findedNode);
        size--;
        return findedNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> findedNode = findIndexAtValue(object);
        if (findedNode != null) {
            unlink(findedNode);
            size--;
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

    private Node<T> findNodeAtIndex(int index) {
        Node<T> findedNode;
        if (index > size / 2) {
            findedNode = last;
            for (int i = size - 1; i != index; i--) {
                findedNode = findedNode.prev;
            }
        } else {
            findedNode = first;
            for (int i = 0; i != index; i++) {
                findedNode = findedNode.next;
            }
        }
        return findedNode;
    }

    private Node<T> findIndexAtValue(T value) {
        for (Node<T> node = first; node != null; node = node.next) {
            if (Objects.equals(node.value, value)) {
                return node;
            }
        }
        return null;
    }

    private void unlink(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            first = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            last = node.prev;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index not found: " + index);
        }
    }

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
}
