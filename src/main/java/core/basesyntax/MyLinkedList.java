package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, false);
        if (index == size) {
            add(value);
            return;
        }

        Node<T> newNode = new Node<>(value);
        Node<T> current = getNode(index);

        if (current != null) {
            Node<T> prevNode = current.getPrev();
            if (prevNode != null) {
                prevNode.setNext(newNode);
            }
            newNode.setPrev(prevNode);
            newNode.setNext(current);
            current.setPrev(newNode);

            if (index == 0) {
                head = newNode;
            }
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index, true);
        return getNode(index).getValue();
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, true);
        Node<T> current = getNode(index);
        T oldValue = current.getValue();
        current.setValue(value);
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, true);
        Node<T> current = getNode(index);
        T removedValue = current.getValue();

        unlink(current);

        size--;
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if (current.getValue() != null && current.getValue().equals(object)) {
                unlink(current);
                size--;
                return true;
            }
            current = current.getNext();
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

    private Node<T> getNode(int index) {
        checkIndex(index, true);
        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.getPrev();
            }
        }
        return current;
    }

    private void checkIndex(int index, boolean mustExist) {
        if (index < 0 || index >= size || (mustExist && getNode(index) == null)) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds.");
        }
    }

    private void unlink(Node<T> node) {
        if (node.getPrev() != null) {
            node.getPrev().setNext(node.getNext());
        } else {
            head = node.getNext();
        }

        if (node.getNext() != null) {
            node.getNext().setPrev(node.getPrev());
        } else {
            tail = node.getPrev();
        }

        node.setPrev(null);
        node.setNext(null);
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(T value) {
            this.value = value;
            this.next = null;
            this.prev = null;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

        public Node<T> getPrev() {
            return prev;
        }

        public void setPrev(Node<T> prev) {
            this.prev = prev;
        }
    }
}
