package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int INIT_SIZE = 0;
    private Node<T> head;
    private Node<T> tail;
    private int size;

    MyLinkedList() {
        size = INIT_SIZE;
    }

    @Override
    public void add(T value) {
        if (size != 0) {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
            size++;
            return;
        }
        initFirstNode(value);
    }

    @Override
    public void add(T value, int index) {
        if (index != size) {
            checkIndex(index);
            if (index == 0) {
                Node<T> newHead = new Node<>(null, value, head);
                toLink(newHead, head);
                head = newHead;
            } else if (index > 0 && index < size) {
                Node<T> indexNode = getNode(index);
                Node<T> currentNode = new Node<>(null, value, null);
                toLink(indexNode.prev, currentNode);
                toLink(currentNode, indexNode);
            } else {
                throw new RuntimeException("Wrong index: " + index + " for size: " + size);
            }
        }
        add(value);
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> indexNode = getNode(index);
        if (indexNode != null) {
            toLink(indexNode.prev, indexNode.next);
            size--;
            return indexNode.value;
        }
        return null;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = getNode(object);
        if (node != null) {
            toLink(node.prev, node.next);
            size--;
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

    private static class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private void initFirstNode(T value) {
        Node<T> firstNode = new Node<>(null, value, null);
        head = firstNode;
        tail = firstNode;
        size++;
    }

    private void checkIndex(int index) {
        if (index < 0 || (index >= size)) {
            throw new IndexOutOfBoundsException("Wrong index: " + index);
        }
    }

    private Node<T> getNode(int index) {
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private Node<T> getNode(T value) {
        Node<T> currentNode = head;
        while (currentNode.next != null) {
            if (Objects.equals(currentNode.value, value)) {
                return currentNode;
            }
            currentNode = currentNode.next;
        }
        return currentNode.value.equals(value) ? currentNode : null;
    }

    private void toLink(Node<T> current, Node<T> next) {
        if (current != null) {
            current.next = next;
        } else {
            head = next;
        }
        if (next != null) {
            next.prev = current;
        } else {
            tail = current;
        }
    }
}
