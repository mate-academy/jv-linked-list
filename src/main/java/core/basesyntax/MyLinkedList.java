package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T element;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        final Node<T> prev = tail;
        final Node<T> newNode = new Node<>(prev, value, null);
        tail = newNode;
        if (prev == null) {
            head = newNode;
        } else {
            prev.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(index + " is invalid index");
        }
        if (index == 0) {
            Node<T> next = head;
            Node<T> node = new Node<>(null, value, next);
            if (size == 0) {
                tail = node;
            }
            head = node;
            if (size > 0) {
                next.prev = node;
            }
        } else if (index == size) {
            add(value);
            return;
        } else {
            Node<T> foundNode = getNodeByIndex(index).prev;
            Node<T> nextNode = foundNode.next;
            Node<T> node = new Node<>(foundNode, value, nextNode);
            foundNode.next = node;
            nextNode.prev = node;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        Object[] arr = list.toArray();
        for (Object obj : arr) {
            add((T) obj);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T element = getNodeByIndex(index).element;
        getNodeByIndex(index).element = value;
        return element;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T element = getNodeByIndex(index).element;
        unlink(getNodeByIndex(index));
        return element;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> i = head; i != null; i = i.next) {
            if (i.element == object || i.element != null && i.element.equals(object)) {
                unlink(i);
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

    private void unlink(Node<T> node) {
        Node<T> prevNode = node.prev;
        Node<T> nextNode = node.next;
        if (size == 1) {
            head = null;
            tail = null;
            size--;
            return;
        }
        if (prevNode == null) {
            head = node.next;
            nextNode.prev = null;
            size--;
            return;
        }
        if (nextNode == null) {
            tail = node.prev;
            prevNode.next = null;
            size--;
            return;
        }
        prevNode.next = nextNode;
        nextNode.prev = prevNode;
        size--;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(index + " is invalid index");
        }
    }

    private Node<T> getNodeByIndex(int index) {
        if (size / 2 > index) {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else {
            Node<T> current = tail;
            for (int i = 0; i < (size - 1) - index; i++) {
                current = current.prev;
            }
            return current;
        }
    }
}
