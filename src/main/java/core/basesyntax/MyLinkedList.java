package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node newNode = new Node<>(value, tail, null);
        if (size == 0) {
            head = newNode;
        } else {
            if (tail == null) {
                tail = newNode;
                tail.previous = head;
                head.next = tail;
            } else {
                tail.next = newNode;
                tail = newNode;
            }
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (isEmpty() && index == 0 || index == size) {
            add(value);
            return;
        }
        linkNode(getNode(index), value);
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        return unlinkNode(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> deletedNode = getNode(object);
        if (deletedNode != null) {
            unlinkNode(deletedNode);
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
        return size == 0;
    }

    private Node<T> getNode(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Incorrect index: "
                    + index + " for LinkedList size " + size);
        }
        Node<T> currentNode;
        if (index < size % 2) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode;
        }
        currentNode = tail;
        for (int i = size - 1; i > index; i--) {
            currentNode = currentNode.previous;
        }
        return currentNode;
    }

    private Node<T> getNode(T object) {
        if (!isEmpty()) {
            Node<T> currentNode = head;
            for (int i = 0; i < size; i++) {
                if (object == null && currentNode.value == null
                        || currentNode.value.equals(object)) {
                    return currentNode;
                }
                currentNode = currentNode.next;
            }
        }
        return null;
    }

    private void linkNode(Node<T> node, T value) {
        if (node.equals(head)) {
            head.previous = new Node<>(value, null, head);
            head = head.previous;
            size++;
            return;
        }
        node.previous.next = new Node<>(value, node.previous, node);
        node.previous = node.previous.next;
        size++;
    }

    private T unlinkNode(Node<T> node) {
        T value = node.value;
        if (node.equals(tail)) {
            tail = tail.previous;
            tail.next = null;
            size--;
            return value;
        }
        if (node.equals(head) && size > 1) {
            head = head.next;
            head.previous = null;
            size--;
            return value;
        }
        if (node.equals(head) && size == 1) {
            head = null;
            size--;
            return value;
        }
        node.previous.next = node.next;
        node.next.previous = node.previous;
        size--;
        return value;
    }

    private static class Node<T> {
        private T value;
        private Node<T> previous;
        private Node<T> next;

        Node(T value, Node<T> previous, Node<T> next) {
            this.previous = previous;
            this.next = next;
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public Node<T> getPrevious() {
            return previous;
        }

        public Node<T> getNext() {
            return next;
        }
    }
}
