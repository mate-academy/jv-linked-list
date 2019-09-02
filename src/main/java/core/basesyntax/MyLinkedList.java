package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        head = tail = null;
        size = 0;
    }

    @Override
    public void add(T value) {
        if (isEmpty()) {
            Node<T> node = new Node<>(null, value, null);
            head = tail = node;
        } else {
            Node<T> tailNode = tail;
            tailNode.next = new Node<>(tailNode, value, null);
            tail = tailNode.next;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (isEmpty() || index == size) {
            add(value);
        } else {
            Node<T> previous = getNode(index).prev;
            Node<T> newNode = new Node<>(previous, value, previous.next);
            previous.next.prev = newNode;
            size++;
        }
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
        return  getNode(index).element;
    }

    @Override
    public void set(T value, int index) {
        checkIndex(index);
        getNode(index).element = value;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return removeElement(getNode(index));
    }

    @Override
    public T remove(T t) {
        T value = null;
        if (t == null) {
            for (Node<T> node = head; node != null; node = node.next) {
                if (node.element == t) {
                    value = removeElement(node);
                }
            }
        } else {
            for (Node<T> node = head; node != null; node = node.next) {
                if (node.element.equals(t)) {
                    value = removeElement(node);
                }
            }
        }
        return value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private T removeElement(Node<T> node) {
        Node<T> prevNode = node.prev;
        Node<T> nextNode = node.next;
        T element = node.element;
        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
        }
        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prev = prevNode;
        }
        node.element = null;
        size--;
        return element;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> result;
        if (index < size / 2) {
            result = head;
            for (int i = 0; i < index; i++) {
                result = result.next;
            }
        } else {
            result = tail;
            for (int i = size - 1; i > index; i--) {
                result = result.prev;
            }
        }
        return result;
    }

    private static class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
