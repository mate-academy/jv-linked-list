package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        private Node(T value) {
            this.value = value;
        }

        private Node(T value, Node<T> next, Node<T> prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    private static final int ONE_ELEMENT = 1;
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (head == null) {
            head = tail = new Node<>(value, null, null);
        } else {
            Node<T> newNode = new Node<>(value, null, tail);
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index " + index);
        }
        Node<T> newNode = new Node<>(value);
        if (isEmpty()) {
            add(value);
            return;
        } else if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else if (index == size) {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        } else {
            Node<T> prevNode = findNodeByIndex(index);
            if (prevNode == null) {
                newNode.prev = tail;
                tail.next = newNode;
                tail = newNode;
            } else {
                insert(value, prevNode);
                return;
            }
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
        Objects.checkIndex(index, size);
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Objects.checkIndex(index, size);
        Node<T> current = findNodeByIndex(index);
        T prev = current.value;
        current.value = value;
        return prev;
    }

    @Override
    public T remove(int index) {
        Node<T> deletedElement = findNodeByIndex(index);
        if (deletedElement == null) {
            throw new IndexOutOfBoundsException("wrong index: " + index + ", for empty list");
        }
        unlink(deletedElement);
        size--;
        return deletedElement.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> deletedElement = findNodeByElement(object);
        boolean isDeleted = unlink(deletedElement);
        if (isDeleted) {
            size--;
        }
        return isDeleted;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    private Node<T> findNodeByElement(T element) {
        Node<T> curNode = head;
        while (curNode != null) {
            if ((curNode.value != null && curNode.value.equals(element))
                    || curNode.value == element) {
                return curNode;
            }
            curNode = curNode.next;
        }
        return null;
    }

    private boolean unlink(Node<T> node) {
        if (node == head) {
            head = head.next;
            return true;
        } else if (node == tail) {
            tail = tail.prev;
            return true;
        } else if (node != null) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            return true;
        }
        return false;
    }

    private void insert(T value, Node<T> previous) {
        Node<T> newNode = new Node(value, previous, previous.prev);
        previous.prev.next = newNode;
        previous.prev = newNode;
        size++;
    }

    private Node<T> findNodeByIndex(int index) {
        Objects.checkIndex(index, size);
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

}

