package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String INDEX_OUT_OF_BOUND = "Index is out of bound";
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public boolean add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (size == 0) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUND);
        }
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> newNode = new Node(null, value, head);
            head.previous = newNode;
            head = newNode;
        } else {
            Node<T> newNode = getNode(index);
            Node<T> added = new Node<>(newNode.previous, value, newNode);
            newNode.previous.next = added;
            newNode.previous = added;
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> newNode = getNode(index);
        T currentValue = newNode.element;
        newNode.element = value;
        return currentValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (object == current.element || object != null && object.equals(current.element)) {
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
        return size == 0;
    }

    private class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> previous;

        public Node(Node<T> previous, T element, Node<T> next) {
            this.element = element;
            this.next = next;
            this.previous = previous;
        }
    }

    private Node<T> getNode(int index) {
        Node<T> current = null;
        if (size / 2 > index) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.previous;
            }
        }
        return current;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUND);
        }
    }

    private T unlink(Node<T> node) {
        Node<T> nextNode = node.next;
        Node<T> previousNode = node.previous;
        if (previousNode == null) {
            head = nextNode;
        } else {
            previousNode.next = nextNode;
            node.previous = null;
        }
        if (nextNode == null) {
            tail = previousNode;
        } else {
            nextNode.previous = previousNode;
            node.next = null;
        }
        size--;
        return node.element;
    }
}
