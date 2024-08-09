package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> lastNode = tail;
        Node<T> newNode = new Node<>(null, value, null);
        tail = newNode;
        if (lastNode == null) {
            head = newNode;
        } else {
            lastNode.next = newNode;
            newNode.prev = lastNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index is not correct, bigger size or smaller 0");
        }
        if (index == size) {
            add(value);
        } else {
            Node<T> lastNode = findNodeByIndex(index);
            Node<T> newNode = new Node<>(null, value, null);
            if (lastNode == head) {
                lastNode.prev = newNode;
                newNode.next = lastNode;
                head = newNode;
            } else {
                lastNode.prev.next = newNode;
                newNode.prev = lastNode.prev;
                lastNode.prev = newNode;
                newNode.next = lastNode;
            }
            size++;
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
        checkIndex(index);
        Node<T> necessaryNode = findNodeByIndex(index);
        return necessaryNode.item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> necessaryNode = findNodeByIndex(index);
        T oldValue = necessaryNode.item;
        necessaryNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> current = findNodeByIndex(index);
        unlink(current);
        return current.item;
    }

    @Override
    public boolean remove(T object) {
        int index = index(object);
        if (index != -1) {
            remove(index);
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

    private void unlink(Node<T> node) {
        if (node.equals(head)) {
            head = head.next;
        } else {
            node.prev.next = node.next;
        }
        if (node.equals(tail)) {
            tail = tail.prev;
        } else {
            node.next.prev = node.prev;
        }
        size--;
    }

    private Node<T> findNodeByIndex(int index) {
        checkIndex(index);
        Node<T> lastNode = head;
        for (int i = 0; i <= index; i++) {
            if (i == index) {
                Node<T> element = lastNode;
                return element;
            }
            lastNode = lastNode.next;
        }
        return null;
    }

    private int index(T element) {
        int index = -1;
        Node<T> lastNode = head;
        for (int i = 0; i < size; i++) {
            if (lastNode.item == element
                    || lastNode.item != null && lastNode.item.equals(element)) {
                return i;
            }
            lastNode = lastNode.next;
        }
        return index;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index is not correct, bigger size or smaller 0");
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> next, T item, Node<T> prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}
