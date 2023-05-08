package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

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
        isValidIndexForAdd(index);
        Node<T> newNode = new Node<>(value);
        if (index == size) {
            add(value);
            return;
        } else if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
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
        checkIndex(index);
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> current = findNodeByIndex(index);
        T prev = current.value;
        current.value = value;
        return prev;
    }

    @Override
    public T remove(int index) {
        Node<T> deletedElement = findNodeByIndex(index);
        unlink(deletedElement);
        return deletedElement.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> deletedElement = findNodeByElement(object);
        if (deletedElement == null) {
            return false;
        }
        unlink(deletedElement);
        return true;

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

    private void unlink(Node<T> node) {
        if (node == null) {
            return;
        }
        if (node == head) {
            head = head.next;
        } else if (node == tail) {
            tail = tail.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }

    private void insert(T value, Node<T> previous) {
        Node<T> newNode = new Node(value, previous, previous.prev);
        previous.prev.next = newNode;
        previous.prev = newNode;
        size++;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    private Node<T> findNodeByIndex(int index) {
        checkIndex(index);
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

    private void isValidIndexForAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("invalid index: " + index);
        }
    }

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

}

