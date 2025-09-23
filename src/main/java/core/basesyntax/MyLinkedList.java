package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode;
        if (size == 0) {
            head = tail = new Node<>(null, null, value);
        } else {
            newNode = new Node<>(null, tail, value);
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkElementIndex(index);

        Node<T> newNode;

        if (index == 0) {
            newNode = new Node<>(head, null, value);
            if (head != null) {
                head.prev = newNode;
            }
            head = newNode;
            if (tail == null) {
                tail = newNode;
            }
        } else if (index == size) { // вставка в кінець
            newNode = new Node<>(null, tail, value);
            tail.next = newNode;
            tail = newNode;
        } else { // вставка в середину
            Node<T> foundedNode = findNodeByIndex(index);
            newNode = new Node<>(foundedNode, foundedNode.prev, value);
            foundedNode.prev.next = newNode;
            foundedNode.prev = newNode;
        }

        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list != null) {
            for (T element : list) {
                add(element);
            }
        }
    }

    @Override
    public T get(int index) {
        checkPositionIndex(index);

        Node<T> node = findNodeByIndex(index);

        return node.value;
    }

    @Override
    public T set(T value, int index) {
        checkPositionIndex(index);

        Node<T> current = findNodeByIndex(index);

        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkPositionIndex(index);
        Node<T> current = findNodeByIndex(index);

        unlink(current);
        return current.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;

        while (current != null) {
            boolean match = (object == null && current.value == null)
                    || (object != null && object.equals(current.value));

            if (match) {
                unlink(current);
                return true;
            }
            current = current.next;
        }

        return false;
    }

    public String toString() {
        if (head == null) {
            return "[]";
        }

        StringBuilder result = new StringBuilder("[");
        Node<T> current = head;
        while (current != null) {
            result.append(current.value);
            if (current.next != null) {
                result.append(", ");
            }
            current = current.next;
        }
        result.append("]");
        return result.toString();
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
        private Node<T> next;
        private Node<T> prev;
        private T value;

        public Node(Node<T> next, Node<T> prev, T value) {
            this.next = next;
            this.prev = prev;
            this.value = value;
        }
    }

    private Node<T> findNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

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

    private void unlink(Node<T> node) {

        if (node.prev == null) {
            head = node.next;
            if (head != null) {
                head.prev = null;
            } else {
                tail = null;
            }

        } else if (node.next == null) {
            tail = node.prev;
            tail.next = null;

        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        node.prev = null;
        node.next = null;

        size--;
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
    }
}
