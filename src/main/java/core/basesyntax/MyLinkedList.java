package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(null, value, null);
        if (head == null) {
            head = tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkAddIndex(index);

        if (index == size) {
            add(value);
        } else {
            Node<T> oldNode = findNodeByIndex(index);
            Node<T> previousNode = oldNode.prev;
            Node<T> newNode = new Node<>(previousNode, value, oldNode);
            oldNode.prev = newNode;
            if (previousNode == null) {
                head = newNode;
            } else {
                previousNode.next = newNode;
            }
            size++;
        }
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
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = findNodeByIndex(index);
        T oldVal = node.item;
        node.item = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> removeNode = findNodeByIndex(index);
        unlink(removeNode);
        return removeNode.item;
    }

    @Override
    public boolean remove(T object) {
        int index = findIndexByValue(object);
        if (index == -1) {
            return false;
        } else {
            unlink(findNodeByIndex(index));
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    private Node<T> findNodeByIndex(int index) {
        if (index < (size >> 1)) {
            Node<T> findNode = head;
            for (int i = 0; i < index; i++) {
                findNode = findNode.next;
            }
            return findNode;
        } else {
            Node<T> findNode = tail;
            for (int i = size - 1; i > index; i--) {
                findNode = findNode.prev;
            }
            return findNode;
        }
    }

    private int findIndexByValue(T value) {
        int index = 0;
        if (value == null) {
            for (Node<T> node = head; node != null; node = node.next) {
                if (node.item == null) {
                    return index;
                }
                index++;
            }
        } else {
            for (Node<T> node = head; node != null; node = node.next) {
                if (value.equals(node.item)) {
                    return index;
                }
                index++;
            }
        }
        return -1;
    }

    private void unlink(Node<T> node) {
        Node<T> next = node.next;
        Node<T> prev = node.prev;

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

        size--;
    }

    private void checkIndex(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException("There is no " + index + " index in LinkedList");
        }
    }

    private void checkAddIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("There is no " + index + " index in LinkedList");
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
