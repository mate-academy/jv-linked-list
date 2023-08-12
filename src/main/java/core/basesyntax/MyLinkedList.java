package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (tail == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkAddIndex(index);
        if (index == 0 && head != null) {
            Node<T> newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
        } else if (index == 0 && head == null) {
            Node<T> newNode = new Node<>(null, value, null);
            head = newNode;
            tail = newNode;
            head.next = tail;
            tail.prev = head;
        } else if (index == size) {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        } else {
            Node<T> currentNode = getNodeByIndex(index);
            Node<T> previous = currentNode.prev;
            Node<T> newNode = new Node<>(previous, value, currentNode);
            previous.next = newNode;
            currentNode.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (tail == null) {
            Node<T> firstNode = new Node<>(null, list.get(0), null);
            head = firstNode;
            tail = firstNode;
            head.next = tail;
            tail.prev = head;
            size++;
            for (int i = 1; i < list.size(); i++) {
                T element = list.get(i);
                Node<T> newNode = new Node<>(tail, element, null);
                tail.next = newNode;
                tail = newNode;
                size++;
            }
        } else {
            for (T element : list) {
                Node<T> newNode = new Node<>(tail, element, null);
                tail.next = newNode;
                tail = newNode;
                size++;
            }
        }
    }

    @Override
    public T get(int index) {
        checkGetIndex(index);
        Node<T> node = getNodeByIndex(index);
        return node.item;
    }

    @Override
    public T set(T value, int index) {
        checkGetIndex(index);
        Node<T> currentNode = getNodeByIndex(index);
        T previousItem = currentNode.item;
        currentNode.item = value;
        return previousItem;
    }

    @Override
    public T remove(int index) {
        checkGetIndex(index);
        Node<T> currentNode = getNodeByIndex(index);
        unlink(currentNode);
        size--;
        return currentNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> nodeToRemove = getNodeByObject(object);
        if (nodeToRemove != null) {
            unlink(nodeToRemove);
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
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }

    }

    private void checkAddIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
    }

    private void checkGetIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
    }

    private Node<T> getNodeByIndex(int index) {
        if (index == size - 1) {
            return tail;
        }
        Node<T> node = head;
        if (size / 2 > index) {
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = 0; i < size - index - 1; i++) {
                node = node.prev;
            }
        }
        return node;
    }

    private void unlink(Node<T> node) {
        Node<T> previous = node.prev;
        Node<T> next = node.next;
        if (previous != null) {
            previous.next = next;
        }
        if (next != null) {
            next.prev = previous;
        }
        if (node == head) {
            head = next;
        }
        if (node == tail) {
            tail = previous;
        }
        node = null;
    }

    private Node<T> getNodeByObject(T object) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (node.item == object || (node.item != null && node.item.equals(object))) {
                return node;
            }
            node = node.next;
        }
        return null;
    }
}
