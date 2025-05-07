package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T data;
        private Node<T> next;
        private Node<T> prev;

        Node(T data, Node<T> prev, Node<T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
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

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, tail, null);
        if (tail == null) {
            head = newNode;
        } else {
            tail.setNext(newNode);
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == size) {
            add(value);
        } else {
            Node<T> current = getNode(index);
            Node<T> newNode = new Node<>(value, current.getPrev(), current);
            if (current.getPrev() == null) {
                head = newNode;
            } else {
                current.getPrev().setNext(newNode);
            }
            current.setPrev(newNode);
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).getData();
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> current = getNode(index);
        T oldData = current.getData();
        current.setData(value);
        return oldData;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> nodeToRemove = getNode(index);
        T removedData = nodeToRemove.getData();
        unlink(nodeToRemove);
        return removedData;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if ((object == null && current.getData() == null)
                    || (object != null && object.equals(current.getData()))) {
                unlink(current);
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
        Node<T> current;
        if (index < (size / 2)) {
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

    private void unlink(Node<T> node) {
        if (node.getPrev() == null) {
            head = node.getNext();
        } else {
            node.getPrev().setNext(node.getNext());
        }
        if (node.getNext() == null) {
            tail = node.getPrev();
        } else {
            node.getNext().setPrev(node.getPrev());
        }
        size--;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }
}
