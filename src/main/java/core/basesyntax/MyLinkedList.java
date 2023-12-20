package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null && tail == null) {
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
        checkIndexForAdd(index);
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else if (index == size) {
            add(value);
            return;
        } else {
            Node<T> nodeBeforeAdd = getNodeByIndex(index - 1);
            newNode.prev = nodeBeforeAdd;
            newNode.next = nodeBeforeAdd.next;
            nodeBeforeAdd.next.prev = newNode;
            nodeBeforeAdd.next = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T data : list) {
            add(data);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> nodeToSet = getNodeByIndex(index);
        T valueToSet = nodeToSet.value;
        nodeToSet.value = value;
        return valueToSet;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> removeNode = getNodeByIndex(index);
        T removeValue = removeNode.value;
        unlink(removeNode);
        return removeValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if (current.value == object || current.value != null && current.value.equals(object)) {
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index must be a positive number "
                    + "and less than the number of elements in the collection: "
                    + index);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index must be a positive number "
                    + "and less than the number of elements in the collection: "
                    + index);
        }
    }

    private void unlink(Node<T> unlinkNode) {
        Node<T> nodePrev = unlinkNode.prev;
        Node<T> nodeNext = unlinkNode.next;

        if (nodePrev == null) {
            head = nodeNext;
        } else {
            nodePrev.next = nodeNext;
        }
        if (nodeNext == null) {
            tail = nodePrev;
        } else {
            nodeNext.prev = nodePrev;
        }
        size--;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> current = null;
        if (index <= size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        }
        if (index > size / 2) {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private static class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        Node(T value) {
            this.value = value;
        }
    }
}
