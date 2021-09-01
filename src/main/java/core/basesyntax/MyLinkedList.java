package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node(tail, value, null);
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexAdd(index);
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            newNode.next = head;
            head = newNode;
        } else {
            Node<T> nextNode = getNodeByIndex(index);
            Node<T> prevNode = nextNode.prev;
            Node<T> newNode = new Node<>(prevNode, value, nextNode);
            nextNode.prev = newNode;
            if (prevNode == null) {
                head = newNode;
            } else {
                prevNode.next = newNode;
            }
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> newNode = getNodeByIndex(index);
        T oldNode = newNode.item;
        newNode.item = value;
        return oldNode;
    }

    @Override
    public T remove(int index) {
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> removedNode = getNodeByValue(object);
        if (removedNode != null) {
            unlink(removedNode);
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
        return head == null;
    }

    private T unlink(Node<T> node) {
        Node<T> first = node.prev;
        Node<T> second = node.next;
        if (first == null) {
            head = second;
        } else {
            first.next = second;
        }
        if (second == null) {
            tail = first;
        } else {
            second.prev = first;
        }
        T element = node.item;
        node.item = null;
        size--;
        return element;
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        Node<T> current = head;
        if (index < (size / 2)) {
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

    private Node<T> getNodeByValue(T value) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if ((current.item == value)
                    || (value != null && value.equals(current.item))) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is invalid");
        }
    }

    private void checkIndexAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is invalid");
        }
    }
}
