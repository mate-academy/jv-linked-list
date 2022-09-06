package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (tail != null) {
            tail.next = newNode;
        }
        if (head == null) {
            head = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node newNode = new Node(null, value, head);
            head.prev = newNode;
            head = newNode;
        } else {
            checkIndex(index);
            Node currentNode = findNode(index);
            Node newNode = new Node(currentNode.prev, value, currentNode);
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        return (T) findNode(index).element;
    }

    @Override
    public T set(T value, int index) {
        Node current = findNode(index);
        T oldValue = (T) current.element;
        current.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node current = findNode(index);
        return unlink(current);
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> node = head; node != null; node = node.next) {
            if (node.element == object
                    || (node.element != null && node.element.equals(object))) {
                unlink(node);
                return true;
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range: "
                    + index + " for size " + size);
        }
    }

    private class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> last, T element, Node<T> next) {
            this.prev = last;
            this.element = element;
            this.next = next;
        }
    }

    private Node findNode(int index) {
        checkIndex(index);
        Node currentNode;
        if (index < (size / 2)) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private T unlink(Node currentNode) {
        if (size == 1) {
            head = null;
            tail = null;
        } else if (currentNode.prev == null) {
            head = currentNode.next;
            head.prev = null;
        } else if (currentNode.next == null) {
            tail = currentNode.prev;
            tail.next = null;
        } else {
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
        }
        size--;
        return (T) currentNode.element;
    }
}
