package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    MyLinkedList() {
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            first = new Node<>(null, value, null);
            last = first;
            size++;
        } else {
            Node<T> node = new Node<>(last, value, null);
            last.next = node;
            last = node;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        validateAddIndex(index);
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
            first = newNode;
            last = newNode;
            size++;
            return;
        }
        Node<T> node = getNodeByIndex(index);
        if (node == null) {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        } else if (index == 0) {
            node.prev = newNode;
            newNode.next = node;
            first = newNode;
        } else {
            newNode.prev = node.prev;
            node.prev.next = newNode;
            node.prev = newNode;
            newNode.next = node;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        Node<T> node = last;
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        validateIndex(index);
        Node<T> node = first;
        for (int i = 0; i < size; i++) {
            if (index == i) {
                return node.item;
            }
            node = node.next;
        }
        return null;
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index);
        final T replacedElement;
        Node<T> node = first;
        for (int i = 0; i < size; i++) {
            if (index == i) {
                replacedElement = node.item;
                node.item = value;
                return replacedElement;
            }
            node = node.next;
        }
        return null;
    }

    @Override
    public T remove(int index) {
        validateIndex(index);
        Node<T> node = getNodeByIndex(index);
        final T removedElement = node.item;
        unlink(node);
        return removedElement;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = getNodeByValue(object);
        return unlink(node);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void validateIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Incorrect index provided");
        }
    }

    private void validateAddIndex(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Incorrect index provided");
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> node = first;
        for (int i = 0; i <= size; i++) {
            if (index == i) {
                return node;
            }
            node = node.next;
        }
        return null;
    }

    private Node<T> getNodeByValue(T object) {
        Node<T> node = first;
        for (int i = 0; i < size; i++) {
            if (node.item == object || (node.item != null && node.item.equals(object))) {
                return node;
            }
            node = node.next;
        }
        return null;
    }

    private boolean unlink(Node<T> node) {
        if (node == null) {
            return false;
        }
        if (size == 1) {
            first = null;
            last = null;
        } else if (node == first) {
            node.next.prev = null;
            first = node.next;
        } else if (node == last) {
            node.prev.next = null;
            last = node.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        node.prev = null;
        node.next = null;
        size--;
        return true;
    }

    private class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
