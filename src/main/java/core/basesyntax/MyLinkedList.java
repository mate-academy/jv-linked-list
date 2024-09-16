package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (head == null) {
            Node<T> node = new Node<>(null, value, null);
            head = node;
            tail = node;
            size++;
            return;
        }

        Node<T> newNode = new Node<>(tail, value, null);
        tail.next = newNode;
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        if (index == size) {
            add(value);
            return;
        }

        Node<T> newNode = new Node<>(null, value, null);

        if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
            size++;
            return;
        }

        Node<T> temp = head;

        for (int i = 0; i < index - 1; i++) {
            temp = temp.next;
        }

        newNode.next = temp.next;
        newNode.prev = temp;

        if (temp.next != null) {
            temp.next.prev = newNode;
        }

        temp.next = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndexOutOfBounds(index, size);

        return getNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkIndexOutOfBounds(index, size);

        Node<T> temp = getNodeByIndex(index);
        T initialItem = temp.element;
        temp.element = value;

        return initialItem;
    }

    @Override
    public T remove(int index) {
        checkIndexOutOfBounds(index, size);

        if (index == 0) {
            final Node<T> temp = head;
            head = head.next;

            if (head != null) {
                head.prev = null;
            } else {
                tail = null;
            }

            size--;
            return temp.element;
        }

        if (index == size - 1) {
            final Node<T> temp = tail;
            tail = tail.prev;

            if (tail != null) {
                tail.next = null;
            } else {
                head = null;
            }

            size--;
            return temp.element;
        }

        Node<T> nodeToRemove = getNodeByIndex(index);
        nodeToRemove.prev.next = nodeToRemove.next;
        nodeToRemove.next.prev = nodeToRemove.prev;

        size--;
        return nodeToRemove.element;
    }

    @Override
    public boolean remove(T element) {
        Node<T> current = head;

        if (element == null) {
            for (int i = 0; current != null; i++) {
                if (current.element == null) {
                    remove(i);
                    return true;
                }

                current = current.next;
            }
        } else {
            for (int i = 0; current != null; i++) {
                if (element.equals(current.element)) {
                    remove(i);
                    return true;
                }

                current = current.next;
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

    private static class Node<T> {
        private Node<T> prev;
        private T element;
        private Node<T> next;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }
    }

    private void checkIndexOutOfBounds(int index, int range) {
        if (index < 0 || index >= range) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> node = head;

        for (int i = 0; i < index; i++) {
            node = node.next;
        }

        return node;
    }
}
