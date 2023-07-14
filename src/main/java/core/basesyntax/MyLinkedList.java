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
        final T removedElement;
        Node<T> node = getNodeByIndex(index);
        if (index == 0 && size == 1) {
            removedElement = node.item;
            first = null;
        } else if (index == 0) {
            removedElement = node.item;
            node.next.prev = node.prev;
            first = node.next;
        } else if (index == size - 1) {
            removedElement = node.item;
            node.prev.next = node.next;
            last = node.prev;
        } else {
            removedElement = node.item;
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
        return removedElement;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = first;
        for (int i = 0; i < size; i++) {
            if (node.item == object || (node.item != null && node.item.equals(object))) {
                if (i == 0 && size == 1) {
                    first = null;
                } else if (i == 0) {
                    node.next.prev = node.prev;
                    first = node.next;
                } else if (i == size - 1) {
                    node.prev.next = node.next;
                    last = node.prev;
                } else {
                    node.prev.next = node.next;
                    node.next.prev = node.prev;
                }
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
