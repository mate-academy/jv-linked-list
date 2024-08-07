package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int EMPTY_LIST = 0;
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, null, null);
        if (size == EMPTY_LIST) {
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        validateIndex(index, true);
        if (index == size) {
            add(value);
        } else {
            Node<T> current = findNodeByIndex(index);
            Node<T> newNode = new Node<>(value, current.prev, current);
            if (current.prev != null) {
                current.prev.next = newNode;
            } else {
                head = newNode;
            }
            current.prev = newNode;
            size++;
        }
    }

    private void validateIndex(int index, boolean allowEnd) {
        if (index < EMPTY_LIST || index > size || (!allowEnd && index == size)) {
            throw new IndexOutOfBoundsException("Index is out of list");
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null || list.isEmpty()) {
            throw new RuntimeException("List can't be empty");
        }
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        validateIndex(index, false);
        Node<T> current = findNodeByIndex(index);
        return current.item;
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private Node<T> getNode(T value) {
        Node<T> current = head;
        while (current != null) {
            if ((current.item == null && value == null) ||
                    (current.item != null &&
                            current.item.equals(value))) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index, false);
        Node<T> current = findNodeByIndex(index);
        current.item = value;
        return current.item;
    }

    @Override
    public T remove(int index) {
        validateIndex(index, false);
        Node<T> current = findNodeByIndex(index);
        unlinkNode(current);
        size--;
        return current.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = getNode(object);
        if (current == null) {
            return false;
        }
        unlinkNode(current);
        size--;
        return true;
    }

    private void unlinkNode(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == EMPTY_LIST;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(T item, Node<T> prev, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}
