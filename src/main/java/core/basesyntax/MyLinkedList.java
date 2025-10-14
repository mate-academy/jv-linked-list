package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }

        public T getValue() {
            return value;
        }

        public Node<T> getPrev() {
            return prev;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public void setPrev(Node<T> prev) {
            this.prev = prev;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
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
        checkPositionIndex(index);
        if (index == size) {
            add(value);
            return;
        }

        Node<T> nextNode = getNode(index);
        Node<T> prevNode = nextNode.getPrev();
        Node<T> newNode = new Node<>(prevNode, value, nextNode);
        nextNode.setPrev(newNode);

        if (prevNode == null) {
            head = newNode;
        } else {
            prevNode.setNext(newNode);
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
        checkElementIndex(index);
        return getNode(index).getValue();
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> node = getNode(index);
        T oldValue = node.getValue();
        node.setValue(value);
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if ((object == null && current.getValue() == null)
                    || (object != null && object.equals(current.getValue()))) {
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
        if (index < (size / 2)) {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
            return current;
        } else {
            Node<T> current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.getPrev();
            }
            return current;
        }
    }

    private T unlink(Node<T> node) {
        Node<T> prev = node.getPrev();
        Node<T> next = node.getNext();
        if (prev == null) {
            head = next;
        } else {
            prev.setNext(next);
            node.setPrev(null);
        }

        if (next == null) {
            tail = prev;
        } else {
            next.setPrev(prev);
            node.setNext(null);
        }

        T element = node.getValue();
        node.setValue(null);
        size--;
        return element;
    }

    private void checkElementIndex(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private void checkPositionIndex(int index) {
        if (!(index >= 0 && index <= size)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }
}
