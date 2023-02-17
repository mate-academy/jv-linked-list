package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(head, value, tail);
        if (head == null) {
            head = tail = newNode;
        }
        tail.next = newNode;
        newNode.prev = tail;
        tail = newNode;
        tail.next = null;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        Node<T> findPrevNode = getNodeByIndex(index - 1);
        Node<T> findCurrentNode = getNodeByIndex(index);
        Node<T> newNode = new Node<>(findPrevNode, value, findCurrentNode);
        if (index == 0) {
            head = newNode;
        } else {
            findPrevNode.next = newNode;
        }
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
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        return node.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        T oldVal = node.item;
        node.item = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> nodeToRemove = getNodeByIndex(index);
        unlink(nodeToRemove);
        return nodeToRemove.item;
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> removedNode = head; removedNode != null; removedNode = removedNode.next) {
                if (removedNode.item == null) {
                    unlink(removedNode);
                    return true;
                }
            }
        } else {
            for (Node<T> removedNode = head; removedNode != null; removedNode = removedNode.next) {
                if (object.equals(removedNode.item)) {
                    unlink(removedNode);
                    return true;
                }
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
        return head == null;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index:"
                    + index + " Size:" + size);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index:"
                    + index + " Size:" + size);
        }
    }

    private void unlink(Node<T> node) {
        if (size == 1) {
            head = null;
            tail = null;
        } else if (node.equals(head)) {
            head.next.prev = null;
            head = head.next;
        } else if (node.equals(tail)) {
            tail.prev.next = null;
            tail = tail.prev;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
        size--;
    }

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
