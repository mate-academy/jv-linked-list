package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String EXCEPTION_MESSAGE = "Index out of bound";
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private class Node<T> {
        private T element;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (head == null) {
            Node<T> firstNode = new Node<>(null, value, null);
            head = firstNode;
            tail = firstNode;
            size++;
        } else {
            Node<T> nextNode = new Node<>(tail, value, null);
            tail.next = nextNode;
            tail = nextNode;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        indexIsValidAdd(index);
        if (index == size) {
            add(value);
            return;
        }
        Node<T> prevNode = getNode(index).prev;
        Node<T> newNode = new Node<>(prevNode,value,getNode(index));
        getNode(index).prev = newNode;
        if (prevNode == null) {
            head = newNode;
        } else {
            prevNode.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T node : list) {
            add(node);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).element;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T oldValue = node.element;
        node.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> someNode = head;
        for (int i = 0; i < size; i++) {
            if (someNode.element == object || someNode.element != null
                    && someNode.element.equals(object)) {
                unlink(someNode);
                return true;
            }
            someNode = someNode.next;
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

    private void indexIsValid(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(EXCEPTION_MESSAGE);
        }
    }

    private Node<T> getNode(int index) {
        indexIsValid(index);
        Node<T> current;
        if (index > (size / 2)) {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
            return current;
        } else {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        }
    }

    private T unlink(Node<T> someNode) {
        Node<T> nextNode = someNode.next;
        Node<T> prevNode = someNode.prev;
        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
        }
        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prev = prevNode;
        }
        size--;
        return someNode.element;
    }

    private void indexIsValidAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(EXCEPTION_MESSAGE);
        }
    }
}
