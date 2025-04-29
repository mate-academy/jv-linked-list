package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static class Node<E> {
        private Node<E> prev;
        private Node<E> next;
        private E value;

        Node(Node<E> prev, E value, Node<E> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }

        public E getValue() {
            return value;
        }

        public void setValue(E value) {
            this.value = value;
        }

        public Node<E> getPrev() {
            return prev;
        }

        public void setPrev(Node<E> prev) {
            this.prev = prev;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }
    }

    private int size;
    private Node<T> head;
    private Node<T> tail;

    public MyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (head == null || tail == null) {
            head = tail = newNode;
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
            throw new IndexOutOfBoundsException();
        }

        if (index == size) {
            add(value);
            return;
        }

        Node<T> current = getNode(index);
        Node<T> newNode = new Node<>(current.prev, value, current);

        if (current.prev != null) {
            current.prev.next = newNode;
        } else {
            head = newNode;
        }

        current.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null || list.isEmpty()) {
            return;
        }

        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return getNode(index).getValue();
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> node = getNode(index);
        T oldValue = node.getValue();
        node.setValue(value);
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> nodeToRemove = getNode(index);

        if (nodeToRemove.prev == null) {
            head = nodeToRemove.next;
            if (head != null) {
                head.prev = null;
            }
        } else {
            nodeToRemove.prev.next = nodeToRemove.next;
        }

        if (nodeToRemove.next == null) {
            tail = nodeToRemove.prev;
            if (tail != null) {
                tail.next = null;
            }
        } else {
            nodeToRemove.next.prev = nodeToRemove.prev;
        }

        if (size == 1) {
            head = null;
            tail = null;
        }

        size--;

        return nodeToRemove.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> nodeToRemove = getNodeByValue(object);
        if (nodeToRemove == null) {
            return false;
        }

        if (nodeToRemove.prev == null) {
            head = nodeToRemove.next;
            if (head != null) {
                head.prev = null;
            }
        } else {
            nodeToRemove.prev.next = nodeToRemove.next;
        }

        if (nodeToRemove.next == null) {
            tail = nodeToRemove.prev;
            if (tail != null) {
                tail.next = null;
            }
        } else {
            nodeToRemove.next.prev = nodeToRemove.prev;
        }

        size--;

        if (size == 0) {
            head = tail = null;
        }

        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> actualNode;
        if (index < size / 2) {
            actualNode = head;
            for (int i = 0; i < index; i++) {
                actualNode = actualNode.next;
            }
        } else {
            actualNode = tail;
            for (int i = size - 1; i > index; i--) {
                actualNode = actualNode.prev;
            }
        }

        return actualNode;
    }

    private Node<T> getNodeByValue(T value) {
        Node<T> actualNode = head;
        while (actualNode != null) {
            if (Objects.equals(actualNode.getValue(), value)) {
                return actualNode;
            }
            actualNode = actualNode.next;
        }
        return null;
    }
}
