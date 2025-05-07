package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

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
        checkIndexForAdd(index);
        Node<T> newNode = new Node<>(null, value, null);
        if (index == 0) {
            newNode.next = head;
            if (head != null) {
                head.prev = newNode;
            }
            head = newNode;
            if (size == 0) {
                tail = newNode;
            }
        } else {
            Node<T> current = getNodeByIndex(index - 1);
            newNode.next = current.next;
            newNode.prev = current;
            if (current.next != null) {
                current.next.prev = newNode;
            }
            current.next = newNode;

            if (index == size) {
                tail = newNode;
            }
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> current = getNodeByIndex(index);
        return current.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> current = getNodeByIndex(index);
        T changedValue = current.item;
        current.item = value;
        return changedValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> current = getNodeByIndex(index);
        unlink(current);
        return current.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if (object == null ? current.item == null : object.equals(current.item)) {
                unlink(current);
                return true;
            }
            current = current.next;
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
        checkIndex(index);
        Node<T> current;
        if (index <= size / 2) {
            current = head;
            for (int i = 0; i < index; ++i) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; --i) {
                current = current.prev;
            }
        }
        return current;
    }

    private void unlink(Node<T> node) {
        if (node.prev == null) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }

        if (node.next == null) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        size--;
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Theres no index " + index);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Theres no index " + index);
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}
