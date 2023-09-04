package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        appendToTail(value);
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            appendToTail(value);
        } else {
            insertBeforeNode(value, getNodeElement(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list.isEmpty()) {
            return;
        }

        for (Object object : list) {
            T nodeElement = (T) object;
            Node<T> newNode = new Node<>(tail, nodeElement, null);
            if (tail == null) {
                head = newNode;
            } else {
                tail.next = newNode;
            }
            tail = newNode;
        }
        size += list.size();
    }

    @Override
    public T get(int index) {
        return getNodeElement(index).item;
    }

    @Override
    public T set(T item, int index) {
        Node<T> nodeValueByIndex = getNodeElement(index);
        T oldItem = nodeValueByIndex.item;
        nodeValueByIndex.item = item;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        return unlink(getNodeElement(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> nodeValue = head; nodeValue != null; nodeValue = nodeValue.next) {
            if ((object == null) && (nodeValue.item == null)
                    || (object != null) && (object.equals(nodeValue.item))) {
                unlink(nodeValue);
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

    private void appendToTail(T value) {
        final Node<T> tail = this.tail;
        final Node<T> newNode = new Node<>(tail, value, null);
        this.tail = newNode;
        if (tail == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        size++;
    }

    private void insertBeforeNode(T value, Node<T> linkNextNode) {
        final Node<T> pred = linkNextNode.prev;
        final Node<T> newNode = new Node<>(pred, value, linkNextNode);
        linkNextNode.prev = newNode;
        if (pred == null) {
            head = newNode;
        } else {
            pred.next = newNode;
        }
        size++;
    }

    private T unlink(Node<T> value) {
        final T item = value.item;
        final Node<T> next = value.next;
        final Node<T> prev = value.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            value.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            value.next = null;
        }

        value.item = null;
        size--;
        return item;
    }

    private void checkIndex(int index) {
        if ((index < 0) || (index >= size)) {
            throw new IndexOutOfBoundsException("Wrong index: " + index);
        }
    }

    private Node<T> getNodeElement(int index) {
        checkIndex(index);
        if (index < (size >> 1)) {
            Node<T> nodeElement = head;
            for (int i = 0; i < index; i++) {
                nodeElement = nodeElement.next;
            }
            return nodeElement;
        } else {
            Node<T> nodeElement = tail;
            for (int i = size - 1; i > index; i--) {
                nodeElement = nodeElement.prev;
            }
            return nodeElement;
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}
