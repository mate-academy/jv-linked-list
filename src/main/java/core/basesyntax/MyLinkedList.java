package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    private void linkFirst(T value) {
        final Node<T> first = head;
        final Node<T> newNode = new Node<>(null, value, first);
        head = newNode;
        if (first == null) {
            tail = newNode;
        } else {
            first.prev = newNode;
        }
    }

    private void linkLast(T value) {
        final Node<T> last = tail;
        final Node<T> newNode = new Node<>(last, value, null);
        tail = newNode;
        if (last == null) {
            head = newNode;
        } else {
            last.next = newNode;
        }
    }

    private void linkBefore(T value, Node<T> place) {
        final Node<T> prev = place.prev;
        final Node<T> newNode = new Node<>(prev, value, place);
        place.prev = newNode;
        if (prev == null) {
            head = newNode;
        } else {
            prev.next = newNode;
        }
    }

    private T unlinkFirst(Node<T> node) {
        final T element = node.item;
        final Node<T> next = node.next;
        node.item = null;
        node.next = null;
        this.head = next;
        if (next == null) {
            tail = null;
        } else {
            next.prev = null;
        }
        size--;
        return element;
    }

    private T unlinkLast(Node<T> node) {
        final T element = node.item;
        final Node<T> prev = node.prev;
        node.item = null;
        node.prev = null;
        this.tail = prev;
        if (prev == null) {
            head = null;
        } else {
            prev.next = null;
        }
        size--;
        return element;
    }

    private T unlink(Node<T> node) {
        final T element = node.item;
        Node<T> prev = node.prev;
        Node<T> next = node.next;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            node.prev = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        size--;
        return element;
    }

    @Override
    public void add(T value) {
        if (isEmpty()) {
            linkFirst(value);
        } else {
            linkLast(value);
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index entered");
        }
        if (index == size) {
            add(value);
        } else {
            Node<T> currentNode = searchNodeByIndex(index);
            linkBefore(value, currentNode);
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
        return searchNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = searchNodeByIndex(index);
        T replacedItem = currentNode.item;
        currentNode.item = value;
        return replacedItem;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        if (index == 0) {
            return unlinkFirst(head);
        }
        if (index == size - 1) {
            return unlinkLast(tail);
        }
        Node<T> currentNode = searchNodeByIndex(index);
        return unlink(currentNode);
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if (object == currentNode.item || object != null && object.equals(currentNode.item)) {
                break;
            }
            currentNode = currentNode.next;
        }
        if (currentNode == tail.next) {
            return false;
        }
        unlink(currentNode);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index entered");
        }
    }

    private Node<T> searchNodeByIndex(int index) {
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}
