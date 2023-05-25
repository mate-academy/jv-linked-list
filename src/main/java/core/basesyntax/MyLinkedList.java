package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String INDEX_OUT_OF_BOUNDS = "Index is out of bounds of list";
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            head = new Node<>(value);
            tail = head;
            size = 1;
        } else {
            tail.next = new Node<>(value);
            tail.next.prev = tail;
            tail = tail.next;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        Node<T> node = getNode(index);
        Node<T> newNode = new Node<>(value);
        if (checkIndexOutOfBorder(index)) {
            if (isEmpty()) {
                head = newNode;
                tail = newNode;
                size++;
                return;
            }
            node = tail;
        }
        if (index == size) {
            newNode.prev = node;
            node.next = newNode;
            tail = newNode;
        } else {
            newNode.next = node;
            newNode.prev = node.prev;
            if (newNode.prev != null) {
                newNode.prev.next = newNode;
            }
            node.prev = newNode;
            if (index == 0) {
                head = newNode;
            }
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
        checkIndexOutOfBorderThrowException(index);
        Node<T> node = getNode(index);
        return node.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndexOutOfBorderThrowException(index);
        Node<T> node = getNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexOutOfBorderThrowException(index);
        Node<T> node = getNode(index);
        unlink(node);
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        int index = getNodeIndex(object);
        if (index < 0) {
            return false;
        }
        remove(index);
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

    private void checkIndex(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUNDS);
        }
    }

    private boolean checkIndexOutOfBorder(int index) {
        return size == 0 || index == size;
    }

    private void checkIndexOutOfBorderThrowException(int index) {
        if (size == 0 || index == size) {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUNDS);
        }
    }

    private void unlink(Node<T> node) {
        Node<T> nextNode = node.next;
        Node<T> prevNode = node.prev;

        if (nextNode != null) {
            nextNode.prev = prevNode;
        } else {
            tail = size == 1 ? head : prevNode;
        }
        if (prevNode != null) {
            prevNode.next = nextNode;
        } else {
            head = nextNode;
        }
        size--;
        if (isEmpty()) {
            head = null;
            tail = null;
        }
    }

    private int getNodeIndex(T object) {
        Node<T> nextNode = head;

        if (nextNode == null) {
            return -1;
        }
        int i = 0;
        while (!(nextNode.value == object
                || nextNode.value != null && nextNode.value.equals(object))) {
            nextNode = nextNode.next;
            i++;
            if (nextNode == null) {
                i++;
                break;
            }
        }
        if (nextNode == null) {
            return -1;
        }
        return i;
    }

    private Node<T> getNode(int index) {
        checkIndex(index);

        if (index == 0) {
            return head;
        }

        if (index == size - 1) {
            return tail;
        }
        int middleOfList = size / 2;
        Node<T> nextNode = head;
        if (index < middleOfList) {
            for (int i = 1; i <= index; i++) {
                nextNode = nextNode.next;
            }
        } else {
            nextNode = tail;
            for (int i = size - 1; i > index; i--) {
                nextNode = nextNode.prev;
            }
        }
        return nextNode;
    }

    private class Node<T> {
        private T value;
        private Node prev;
        private Node next;

        Node(T value) {
            this.value = value;
        }
    }
}
