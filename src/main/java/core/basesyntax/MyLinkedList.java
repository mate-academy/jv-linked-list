package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            addFirstNode(value);
        } else {
            Node<T> newNode = new Node<>(tail, value, null);
            newNode.setPrev(tail);
            tail.setNext(newNode);
            tail = newNode;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode;
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            newNode = new Node<>(null, value, head);
            newNode.setNext(head);
            head.setPrev(newNode);
            head = newNode;
        } else {
            Node<T> currentNode = node(index);
            Node<T> currentNodePrev = currentNode.getPrev();
            newNode = new Node<>(currentNodePrev, value, currentNode);
            currentNode.setPrev(newNode);
            currentNodePrev.setNext(newNode);
            newNode.setNext(currentNode);
            newNode.setPrev(currentNodePrev);
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
        return node(index).getValue();
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = node(index);
        T currentValue = currentNode.getValue();
        currentNode.setValue(value);
        return currentValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(node(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (!(currentNode.value == object
                || currentNode.value != null && currentNode.value.equals(object))) {
            if (currentNode == tail) {
                return false;
            }
            currentNode = currentNode.next;
        }
        unlink(currentNode);
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

    private void addFirstNode(T value) {
        Node<T> newNode;
        newNode = new Node<>(null, value, null);
        head = newNode;
        tail = newNode;
        head.setPrev(null);
        tail.setNext(null);
        size++;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
    }

    private Node<T> node(int index) {
        Node<T> currentNode;
        if (size / 2 > index) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.getNext();
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.getPrev();
            }
        }
        return currentNode;
    }

    private T unlink(Node<T> node) {
        final T currentValue = node.getValue();
        if (node != tail) {
            node.getNext().setPrev(node.getPrev());
        } else {
            if (size > 1) {
                tail = node.getPrev();
            }
            tail.setNext(null);
        }
        if (node != head) {
            node.getPrev().setNext(node.getNext());
        } else {
            if (size > 1) {
                head = node.getNext();
            }
            head.setPrev(null);
        }
        node.setValue(null);
        size--;
        return currentValue;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }

        public Node<T> getPrev() {
            return prev;
        }

        public void setPrev(Node<T> prev) {
            this.prev = prev;
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
    }
}
