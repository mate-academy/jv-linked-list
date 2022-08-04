package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    private class Node<T> {
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
    public void add(T value) {
        Node<T> node = new Node<>(null, value, null);
        size++;
        if (tail == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        indexValidator(index);

        Node<T> node = new Node<>(null, value, null);
        Node<T> currentNode = findNode(index);
        if (index == 0) {
            node.next = head;
            head.prev = node;
            head = node;
            size++;
            return;
        }
        node.prev = currentNode.prev;
        node.next = currentNode;
        currentNode.prev.next = node;
        currentNode.prev = node;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        indexValidator(index);
        Node<T> currentNode = findNode(index);
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        indexValidator(index);
        Node<T> currentNode = findNode(index);
        T oldValiue = currentNode.value;
        currentNode.value = value;
        return oldValiue;
    }

    @Override
    public T remove(int index) {
        indexValidator(index);
        Node<T> currentNode = findNode(index);
        unlink(currentNode);
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (currentNode.value == null && object == null
                    || currentNode != null && currentNode.value.equals(object)) {
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

    private void unlink(Node<T> node) {
        if (node == head && size == 1) {
            head = null;
            tail = null;
            size--;
            return;
        }
        if (node == head) {
            head = node.next;
            node.next.prev = null;
            size--;
            return;
        }
        if (node == tail) {
            tail = node.prev;
            node.prev.next = null;
            size--;
            return;
        }
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
    }

    private void indexValidator(int index) {
        if (index >= 0 && index < size) {
            return;
        }
        throw new IndexOutOfBoundsException("There is no such index.");
    }

    private Node<T> findNode(int index) {
        int counter = 0;
        if (index < size / 2) {
            Node<T> currentNode = head;
            while (currentNode != null) {
                if (counter == index) {
                    break;
                }
                counter++;
                currentNode = currentNode.next;
            }
            return currentNode;
        }
        index = size - index - 1;
        Node<T> currentNode = tail;
        while (currentNode != null) {
            if (counter == index) {
                break;
            }
            counter++;
            currentNode = currentNode.prev;
        }
        return currentNode;
    }
}
