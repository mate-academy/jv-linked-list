package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(tail, value, null);
        if (isEmpty()) {
            head = node;
        } else {
            tail.next = node;
        }
        size++;
        tail = node;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " out of range " + size);
        }
        Node<T> nextNode = getNode(index);
        Node<T> prevNode = nextNode.prev;
        Node<T> newNode = new Node<>(prevNode, value, nextNode);
        if (prevNode == null) {
            head = newNode;
        } else {
            prevNode.next = newNode;
        }
        nextNode.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> newNode = getNode(index);
        T oldItem = newNode.item;
        newNode.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        checkRange(index);
        Node<T> temp = getNode(index);
        unlink(temp);
        return temp.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> temp = head;
        while (temp != null) {
            if (isEquals(object, temp.item)) {
                unlink(temp);
                return true;
            }
            temp = temp.next;
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

    public void unlink(Node<T> node) {
        if (node == head && node == tail) {
            head = null;
            tail = null;
            size--;
        } else if (node == head) {
            node.next.prev = null;
            head = node.next;
            size--;
        } else if (node == tail) {
            node.prev.next = null;
            tail = node.prev;
            size--;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            size--;
        }
    }

    public boolean isEquals(T first, T second) {
        return first == second || first != null && first.equals(second);
    }

    private void checkRange(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " out of range " + (size() - 1));
        }
    }

    private Node<T> getNode(int index) {
        checkRange(index);
        Node<T> node;
        if (index == 0) {
            return head;
        } else if (index == size - 1) {
            return tail;
        } else {
            node = head;
            if (index < (size() / 2)) {
                for (int i = 0; i < index; i++) {
                    node = node.next;
                }
            } else {
                node = tail;
                for (int i = size() - 1; i > index; i--) {
                    node = node.prev;
                }
            }
        }
        return node;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.prev = prev;
            this.next = next;
        }
    }
}
