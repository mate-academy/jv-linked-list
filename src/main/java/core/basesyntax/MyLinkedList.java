package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            equalHeadAndTail(value);
        } else {
            tail.next = new Node<>(value, tail, null);
            tail = tail.next;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException("Index out of array boundaries size = "
                    + size + " " + index);
        }
        if (isEmpty()) {
            equalHeadAndTail(value);
        } else if (index == size) {
            add(value);
        } else if (index == 0) {
            addToTheFirst(value);
        } else {
            Node<T> currentWithThisIndex = search(index);
            Node<T> newNode = new Node<>(value,
                    currentWithThisIndex.prev, currentWithThisIndex);
            currentWithThisIndex.prev.next = newNode;
            currentWithThisIndex.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T elem : list) {
            add(elem);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return search(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = search(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> removedNode = search(index);
        final T removedItem = removedNode.item;
        unlink(removedNode);
        return removedItem;
    }

    @Override
    public boolean remove(T object) {
        Node<T> toRemove = head;
        while (toRemove != null && (object == null
                ? toRemove.item != null : !object.equals(toRemove.item))) {
            toRemove = toRemove.next;
        }
        if (toRemove == null) {
            return false;
        }
        unlink(toRemove);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("Index out of array boundaries size ="
                    + size + " " + index);
        }
    }

    private Node<T> search(int index) {
        Node<T> node;
        if ((size - 1) / 2 > index) {
            node = tail;
            while (index != size - 1) {
                node = node.prev;
                index++;
            }
        } else {
            node = head;
            while (index != 0) {
                node = node.next;
                index--;
            }
        }
        return node;
    }

    private void equalHeadAndTail(T value) {
        head = new Node<>(value, null, null);
        tail = head;
        size++;
    }

    private void addToTheFirst(T value) {
        head.prev = new Node<>(value, null, head);
        head = head.prev;
        size++;
    }

    private void unlink(Node<T> node) {
        if (node == head) {
            head = head.next;
            if (head != null) {
                head.prev = null;
            } else {
                tail = null;
            }
        } else if (node == tail) {
            tail = tail.prev;
            if (tail != null) {
                tail.next = null;
            } else {
                head = null;
            }
        } else {
            if (node.prev != null) {
                node.prev.next = node.next;
            }
            if (node.next != null) {
                node.next.prev = node.prev;
            }
        }
        size--;
    }

    public static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(T item, Node<T> prev, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}
