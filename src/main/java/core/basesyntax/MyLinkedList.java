package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (size == 0) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            connectFirst(value);
        } else {
            connect(value, index);
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> newNode = getNodeByIndex(index);
        T oldValue = newNode.element;
        newNode.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        unlink(node);
        return node.element;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if (current.element == object || (current.element != null
                    && current.element.equals(object))) {
                unlink(current);
                return true;
            }
            current = current.next;
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

    public void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is wrong " + index);
        }
    }

    public void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is wrong for add method" + index);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        if (index < size / 2) {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else {
            Node<T> current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
            return current;
        }
    }

    private void connectFirst(T element) {
        Node<T> newNode = new Node<>(null, element, head);
        if (size > 0) {
            head.prev = newNode;
        } else {
            tail = newNode;
        }
        head = newNode;
    }

    private void connect(T element, int index) {
        Node<T> previous = getNodeByIndex(index - 1);
        Node<T> newNode = new Node<>(previous, element, previous.next);
        previous.next.prev = newNode;
        previous.next = newNode;
    }

    private void unlink(Node<T> removedNode) {
        if (removedNode == head) {
            head = removedNode.next;
        } else {
            removedNode.prev.next = removedNode.next;
        }
        if (removedNode == tail) {
            tail = removedNode.prev;
        } else {
            removedNode.next.prev = removedNode.prev;
        }
        size--;
    }

    private static class Node<T> {
        private Node<T> prev;
        private T element;
        private Node<T> next;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }
    }
}
