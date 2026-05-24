package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, null, null);
        if (size == 0) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Wrong input");
        }

        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(value, null, null);
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
            size++;
        } else {
            Node<T> newNode = new Node<>(value, null, null);
            Node<T> previous = findNodeByIndex(index - 1);
            previous.next.prev = newNode;
            newNode.next = previous.next;
            newNode.prev = previous;
            previous.next = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = findNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> nodeToDelete = findNodeByIndex(index);
        unlink(nodeToDelete);
        return nodeToDelete.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        if (object == null) {
            for (int i = 0; i < size; i++) {
                if (current.value == null) {
                    unlink(current);
                    return true;
                }
                current = current.next;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (object.equals(current.value)) {
                    unlink(current);
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
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(T value, Node<T> next, Node<T> prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Wrong input");
        }
    }

    private Node<T> findNodeByIndex(int index) {
        if (index < size / 2) {
            Node<T> current = head;

            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else {
            Node<T> current = tail;

            for (int i = 0; i < size - 1 - index; i++) {
                current = current.prev;
            }
            return current;
        }
    }

    private void unlink(Node<T> node) {
        if (node.next == null && node.prev == null) {
            head = null;
            tail = null;
            size--;
            return;
        }
        if (node.prev == null) {
            head = node.next;
            head.prev = null;
            size--;
        } else if (node.next == null) {
            tail = node.prev;
            tail.next = null;
            size--;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            size--;
        }
    }
}
