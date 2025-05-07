package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> tail;
    private Node<T> head;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail = newNode;
            newNode.prev.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexWithoutEquals(index);
        if (index == size()) {
            add(value);
        } else if (index == 0) {
            Node<T> node = new Node<>(null, value, head);
            head.prev = node;
            head = node;
            size++;
        } else {
            Node<T> currentNode = getNode(index);
            Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
            if (currentNode.prev != null) {
                currentNode.prev.next = newNode;
            } else {
                head = newNode;
            }
            currentNode.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        if (head == null) {
            add(value);
        } else {
            Node<T> currentNode = getNode(index);
            T oldItem = currentNode.value;
            currentNode.value = value;
            return oldItem;
        }
        return null;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        if (head == null) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        Node<T> removeNode = getNode(index);
        return unlink(removeNode).value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        while (node != null) {
            if (node.value == object || node.value != null && node.value.equals(object)) {
                unlink(node);
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
        return size() == 0;
    }

    private Node<T> unlink(Node<T> removeNode) {
        if (removeNode.prev != null) {
            removeNode.prev.next = removeNode.next;
        } else {
            head = removeNode.next;
        }
        if (removeNode.next != null) {
            removeNode.next.prev = removeNode.prev;
        } else {
            tail = removeNode.prev;
        }
        size--;
        return removeNode;
    }

    private void checkIndex(int index) {
        if (index >= size() || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    private void checkIndexWithoutEquals(int index) {
        if (index > size() || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        if (size() / 2 > index) {
            Node<T> currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode;
        } else {
            Node<T> currentNode = tail;
            for (int i = size() - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
            return currentNode;
        }
    }

    private static class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
