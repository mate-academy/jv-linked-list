package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }
        if (index == size) {
            add(value);
            return;
        }
        final Node<T> oldNode = fastIndexFinder(index);
        final Node<T> previousNode = oldNode.prev;
        final Node<T> newNode = new Node<>(previousNode, value, oldNode);
        oldNode.prev = newNode;
        if (previousNode == null) {
            head = newNode;
        } else {
            previousNode.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T each : list) {
            add(each);
        }
    }

    @Override
    public T get(int index) {
        checkIndexForGetSetRemove(index);
        return fastIndexFinder(index).data;
    }

    @Override
    public T set(T value, int index) {
        checkIndexForGetSetRemove(index);
        Node<T> current = fastIndexFinder(index);
        T oldValue = current.data;
        current.data = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexForGetSetRemove(index);
        Node<T> nodeToRemove = fastIndexFinder(index);
        return unlink(nodeToRemove);
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> currentNode = head; currentNode != null; currentNode = currentNode.next) {
                if (currentNode.data == null) {
                    unlink(currentNode);
                    return true;
                }
            }
        } else {
            for (Node<T> currentNode = head; currentNode != null; currentNode = currentNode.next) {
                if (object.equals(currentNode.data)) {
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
        return size == 0;
    }

    private void checkIndexForGetSetRemove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(index + " is incorrect index");
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(index + " is incorrect index");
        }
    }

    private Node<T> fastIndexFinder(int index) {
        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private T unlink(Node<T> node) {
        final T element = node.data;
        final Node<T> nextNode = node.next;
        final Node<T> previousNode = node.prev;
        if (previousNode == null) {
            head = nextNode;
        } else {
            previousNode.next = nextNode;
        }
        if (nextNode == null) {
            tail = previousNode;
        } else {
            nextNode.prev = previousNode;
        }
        node.data = null;
        node.prev = null;
        node.next = null;
        size--;
        return element;
    }

    private class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T data;

        public Node(Node<T> prev, T data, Node<T> next) {
            this.prev = prev;
            this.data = data;
            this.next = next;
        }
    }
}
