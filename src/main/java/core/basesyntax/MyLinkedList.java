package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (head == null) {
            addHead(value);
        } else {
            addTail(value);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        if (index == 0) {
            addHead(value);
        } else if (index == size) {
            addTail(value);
        } else {
            Node<T> currentNode = getNodeByIndex(index);
            currentNode.prev.next = new Node<>(currentNode.prev, value, currentNode);
            currentNode.prev = currentNode.prev.next;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element: list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNodeByIndex(index);
        unlink(node);
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (currentNode.value == object
                    || currentNode.value != null && currentNode.value.equals(object)) {
                unlink(currentNode);
                return true;
            }
            currentNode = currentNode.next;
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

    private void addHead(T value) {
        if (head == null) {
            head = new Node<>(null, value, null);
        } else {
            head.prev = new Node<>(null, value, head);
            head = head.prev;
            if (tail == null) {
                tail = head.next;
            }
        }
    }

    private void addTail(T value) {
        if (tail == null) {
            tail = new Node<>(head, value, null);
            head.next = tail;
        } else {
            tail.next = new Node<>(tail, value, null);
            tail = tail.next;
        }
    }

    private Node<T> getNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        Node<T> currentNode;
        if (index <= size / 2) {
            currentNode = head;
            while (index > 0) {
                currentNode = currentNode.next;
                index--;
            }
        } else {
            currentNode = tail;
            while (size - index > 1) {
                currentNode = currentNode.prev;
                index++;
            }
        }
        return currentNode;
    }

    private void unlink(Node<T> node) {
        if (node.prev == null) {
            if (head.next == tail) {
                tail = null;
            }
            head = head.next;
        } else if (node.next == null) {
            if (node.prev == head) {
                tail = null;
            } else {
                tail = node.prev;
                tail.next = null;
            }
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
    }
}
