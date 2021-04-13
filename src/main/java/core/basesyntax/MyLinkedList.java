package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> current;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public boolean add(T value) {
        Node<T> newNode;
        if (size == 0) {
            newNode = new Node<>(null, value, null);
            head = newNode;
        } else {
            newNode = new Node<>(tail, value, null);
            tail.next = newNode;
        }
        tail = newNode;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        current = findByIndex(index);

        Node<T> newNode = new Node<>(current.prev, value, current);
        if (newNode.prev == null) {
            head = newNode;
        } else {
            newNode.prev.next = newNode;
        }
        current.prev = newNode;
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        current = findByIndex(index);
        return current.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        current = findByIndex(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> nodeToRemove = findByIndex(index);
        unlink(nodeToRemove);
        size--;
        return nodeToRemove.value;
    }

    @Override
    public boolean remove(T object) {
        //Node <T> nodeToRemove = head;
        for (int i = 0; i < size; i++) {
            current = findByIndex(i);
            if (object == current.value || object != null
                    && object.equals(current.value)) {
                unlink(current);
                size--;
                return true;
            }
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
        if (index > size - 1 || index < 0) {
            throw new IndexOutOfBoundsException("Index is out of list bounds");
        }
    }

    private Node<T> findByIndex(int index) {
        Node<T> nodeByIndex = head;
        for (int i = 0; i < index; i++) {
            nodeByIndex = nodeByIndex.next;
        }
        return nodeByIndex;
    }

    private void unlink(Node<T> node) {

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
    }
}
