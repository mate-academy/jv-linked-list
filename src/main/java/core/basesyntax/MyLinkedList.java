package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(head, value, tail);
        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        validateAddIndex(index);
        Node<T> newNode = new Node<>(null, value, null);
        if (head == null) {
            head = tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else if (index == size) {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        } else {
            Node<T> temporaryNode = getNode(index);
            newNode.next = temporaryNode;
            newNode.prev = temporaryNode.prev;
            temporaryNode.prev.next = newNode;
            temporaryNode.prev = newNode;
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
        validateIndex(index);
        Node<T> currentNode = head;
        int counter = 0;
        while (currentNode != null && counter != index) {
            currentNode = currentNode.next;
            counter++;
        }
        return currentNode.element;
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index);
        Node<T> currentNode = head;
        int counter = 0;
        while (currentNode != null && counter != index) {
            currentNode = currentNode.next;
            counter++;
        }
        T prevElement = currentNode.element;
        currentNode.element = value;
        return prevElement;
    }

    @Override
    public T remove(int index) {
        validateIndex(index);
        Node<T> deletedNode = getNode(index);
        T deletedValue = deletedNode.element;

        if (index == 0) {
            if (size == 1) {
                isEmpty();
            } else {
                head.next.prev = null;
                head = head.next;
            }
        } else if (index == size - 1) {
            tail.prev.next = deletedNode.next;
            tail = tail.prev;
        } else {
            unlink(deletedNode);
        }

        size--;
        return deletedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if ((node.element == null && object == null)
                    || (node.element != null && node.element.equals(object))) {
                unlink(node);
                size--;
                return true;
            }
            node = node.next;
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

    private Node<T> getNode(int index) {
        validateIndex(index);
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

    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private void validateAddIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    class Node<T> {
        private T element;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }
    }
}
