package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    static class Node<T> {
        private Node<T> prev;
        private T element;
        private Node<T> next;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (isEmpty()) {
            connectFirst(value);
        } else {
            connectLast(value);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == 0) {
            connectFirst(value);
        } else if (index == size) {
            connectLast(value);
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
        size--;
        return node.element;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if (current.element == object || (current.element != null
                    && current.element.equals(object))) {
                unlink(current);
                size--;
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
            throw new IndexOutOfBoundsException("Index is wrong" + index);
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

    private void connectLast(T element) {
        Node<T> newNode = new Node<>(tail, element, null);
        tail.next = newNode;
        tail = newNode;
    }

    private void connect(T element, int index) {
        Node<T> newNode = new Node<>(null, element, null);
        Node<T> previous = getNodeByIndex(index - 1);
        newNode.prev = previous;
        newNode.next = previous.next;
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
    }
}
