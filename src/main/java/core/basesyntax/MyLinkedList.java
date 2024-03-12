package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index > size() || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " doesn't exist");
        }
        if (index == 0 && size() == 0) {
            add(value);
        } else if (index == size()) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
        } else {
            Node<T> currentNode = searchNode(index);
            Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " doesn't exist");
        }
        Node<T> getNode = head;
        for (int i = 0; i < index; i++) {
            getNode = getNode.next;
            if (getNode == null) {
                throw new IndexOutOfBoundsException("Index " + index + " doesn't exist");
            }
        }
        return getNode.value;
    }

    @Override
    public T set(T value, int index) {
        T removeValue;
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " doesn't exist");
        }
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
            if (currentNode == null) {
                throw new IndexOutOfBoundsException("Index " + index + " doesn't exist");
            }
        }
        removeValue = currentNode.value;
        currentNode.value = value;
        return removeValue;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index " + index + " doesn't exist");
        }
        Node<T> currentNode = searchNode(index);
        unlink(currentNode);
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size();i++) {
            if (object == null || currentNode.value == null) {
                if (object == currentNode.value) {
                    unlink(currentNode);
                    return true;
                }
            } else if (object.equals(currentNode.value)) {
                unlink(currentNode);
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    @Override
    public int size() {
        if (head == null) {
            return 0;
        }
        int size = 1;
        Node<T> currentNode = head;
        for (int i = 0; currentNode != tail; i++) {
            currentNode = currentNode.next;
            size++;
        }
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    private void unlink(Node<T> node) {
        if (node.equals(tail) && node.equals(head)) {
            head = tail = null;
        } else if (node.equals(head)) {
            node.next.prev = null;
            head = node.next;
        } else if (node.equals(tail)) {
            node.prev.next = null;
            tail = node.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    private Node searchNode(int index) {
        Node<T> currentNode;
        if (size() - index < size() / 2) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size() - 1; index < i; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    class Node<T> {
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
