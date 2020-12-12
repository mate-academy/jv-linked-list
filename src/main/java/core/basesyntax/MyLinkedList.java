package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        Node<T> prev;
        T value;
        Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    public MyLinkedList() {
        tail = new Node<>(null, null, null);
        head = tail;
        size = 0;
    }

    @Override
    public boolean add(T value) {
        if (size == 0) {
            tail.value = value;
            size++;
            return true;
        }
        Node<T> newNode = new Node<>(tail, value, null);
        tail.next = newNode;
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
        indexCheck(index);
        if (index == 0) {
            head = new Node<>(null, value, head);
            size++;
            return;
        }
        Node<T> currentNode = findNodeByIndex(index);
        Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
        newNode.prev.next = newNode;
        currentNode.prev = newNode;
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
        return true;
    }

    @Override
    public T get(int index) {
        indexCheck(index);
        Node<T> node = findNodeByIndex(index);
        if (node == head) {
            return head.value;
        }
        return node.value;
    }

    @Override
    public T set(T value, int index) {
        indexCheck(index);
        Node<T> node = findNodeByIndex(index);
        T temp = node.value;
        node.value = value;
        return temp;
    }

    @Override
    public T remove(int index) {
        indexCheck(index);
        Node<T> node = findNodeByIndex(index);
        T temp = node.value;
        if (size == 1) {
            head.value = null;
        } else {
            if (node == head) {
                head.next.prev = null;
                head = head.next;
            } else if (node == tail) {
                tail.prev.next = null;
                tail = tail.prev;
            } else {
                node.prev.next = node.next;
                node.next.prev = node.prev;
            }
        }
        size--;
        return temp;
    }

    @Override
    public boolean remove(T object) {
        int i = 0;
        Node<T> node = head;
        while (i < size()) {
            if (node.value == null && object == null
                    || node.value != null && node.value.equals(object)) {
                remove(i);
                return true;
            }
            node = node.next;
            i++;
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

    private void indexCheck(int i) {
        if (i >= size || i < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> currentNode;
        if (index < size / 2) {
            currentNode = head;
            int i = 0;
            while (i < index) {
                currentNode = currentNode.next;
                i++;
            }
        } else {
            currentNode = tail;
            int i = size - 1;
            while (i > index) {
                currentNode = currentNode.prev;
                i--;
            }
        }
        return currentNode;
    }
}
