package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
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
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> newNode = new Node<>(value);
        if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else {
            Node<T> nextNode = getNode(index);
            Node<T> prevNode = nextNode.prev;

            newNode.next = nextNode;
            newNode.prev = prevNode;
            prevNode.next = newNode;
            nextNode.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            throw new NullPointerException("Specified collection is null");
        }
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> nodeToSet = getNode(index);
        T oldValue = nodeToSet.value;
        nodeToSet.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> nodeToRemove = getNode(index);
        return unlinkAndGetData(nodeToRemove);
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if (object == null) {
                if (current.value == null) {
                    unlinkAndGetData(current);
                    return true;
                }
            } else {
                if (object.equals(current.value)) {
                    unlinkAndGetData(current);
                    return true;
                }
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    private Node<T> getNode(int index) {
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

    private T unlinkAndGetData(Node<T> nodeToUnlink) {
        Node<T> prevNode = nodeToUnlink.prev;
        Node<T> nextNode = nodeToUnlink.next;
        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
            nodeToUnlink.prev = null;
        }
        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prev = prevNode;
            nodeToUnlink.next = null;
        }

        size--;
        return nodeToUnlink.value;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(T value) {
            this.value = value;
        }
    }
}
