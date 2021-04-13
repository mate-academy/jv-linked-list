package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String EXCEPTION_TEXT = "Index out of bound exception.";
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public boolean add(T value) {
        Node<T> first = new Node<>(null, value, null);
        Node<T> newNode = new Node<>(tail, value, null);
        if (size == 0) {
            head = first;
            tail = first;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(EXCEPTION_TEXT);
        }
        if (index == size) {
            add(value);
        } else {
            Node<T> current = getNode(index);
            Node<T> left = current.prev;
            Node<T> newNode = new Node<>(left, value, current);
            if (left == null) {
                head = newNode;
            } else {
                current.prev = newNode;
                left.next = newNode;
            }
            size++;
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> oldNode = getNode(index);
        T oldValue = oldNode.value;
        oldNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (object == currentNode.value || Objects.equals(object, currentNode.value)) {
                unlink(currentNode);
                return true;
            }
            currentNode = currentNode.next;
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(EXCEPTION_TEXT);
        }
    }

    private Node<T> getNode(int index) {
        Node<T> searchedNode = index < size / 2 ? head : tail;
        if (index < size / 2) {
            for (int i = 0; i < index; i++) {
                searchedNode = searchedNode.next;
            }
        } else {
            for (int i = size - 1; i > index; i--) {
                searchedNode = searchedNode.prev;
            }
        }
        return searchedNode;
    }

    private T unlink(Node<T> node) {
        Node<T> prevNode = node.prev;
        Node<T> nextNode = node.next;
        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
            node.prev = null;
        }
        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prev = prevNode;
            node.next = null;
        }
        T oldValue = node.value;
        node.value = null;
        size--;
        return oldValue;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
