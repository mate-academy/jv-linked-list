package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            head = new Node<T>(value, null, null);
            tail = head;
        } else if (size == 1) {
            tail = new Node<T>(value, head, null);
            head.next = tail;
        } else {
            tail = new Node<T>(value, tail, null);
            tail.previous.next = tail;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            head.previous = new Node<T>(value, null, head);
            head = head.previous;
        } else {
            Node<T> node = getNode(index);
            node.previous.next = new Node<T>(value, node.previous, node);
            node.previous = node.previous.next;
        }
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
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNode(index);
        T oldValue = node.value;
        removeNode(node);
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        boolean output = false;
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (node.value == object || object != null && object.equals(node.value)) {
                output = true;
                removeNode(node);
                break;
            }
            node = node.next;
        }
        return output;
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
        private Node<T> previous;

        public Node(T value, Node<T> previous, Node<T> next) {
            this.value = value;
            this.next = next;
            this.previous = previous;
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> node;
        if (index < size / 2) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.previous;
            }
        }
        return node;
    }

    private void checkIndex(int index) {
        if (index < 0 || index != 0 && index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    private void removeNode(Node<T> node) {
        if (size == 1) {
            node = null;
            size--;
            return;
        }
        if (node != head) {
            node.previous.next = node.next;
            if (node == tail) {
                node = node.previous;
                tail = node;
            }
        }
        if (node != tail) {
            node.next.previous = node.previous;
            if (node == head) {
                node = node.next;
                head = node;
            }
        }
        size--;
    }
}
