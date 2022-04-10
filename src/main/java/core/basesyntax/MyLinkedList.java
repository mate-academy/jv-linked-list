package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.item = element;
            this.next = next;
        }
    }

    private Node<T> getIndexedNode(int index) {
        Node<T> indexedNode;
        if (index < size / 2) {
            indexedNode = head;
            for (int i = 0; i < index; i++) {
                indexedNode = indexedNode.next;
            }
        } else {
            indexedNode = tail;
            for (int i = size - 1; i > index; i--) {
                indexedNode = indexedNode.prev;
            }
        }
        return indexedNode;
    }

    @Override
    public void add(T value) {
        Node<T> checkTail = tail;
        Node<T> newNode = new Node<>(checkTail, value, null);
        tail = newNode;
        if (checkTail == null) {
            head = newNode;
        } else {
            checkTail.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index >= 0 && index <= size) {
            if (index == size) {
                add(value);
            } else {
                Node<T> next = getIndexedNode(index);
                Node<T> prev = next.prev;
                Node<T> newNode = new Node<>(prev, value, next);
                next.prev = newNode;
                if (prev == null) {
                    head = newNode;
                } else {
                    prev.next = newNode;
                }
                size++;
            }
        } else {
            throw new IndexOutOfBoundsException("Can't add value. Pass correct input index");
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        if (index >= 0 && index < size) {
            return getIndexedNode(index).item;
        }
        throw new IndexOutOfBoundsException("Can't get item. Pass correct input index");
    }

    @Override
    public T set(T value, int index) {
        if (index >= 0 && index < size) {
            Node<T> targetNode = getIndexedNode(index);
            T oldValue = targetNode.item;
            targetNode.item = value;
            return oldValue;
        }
        throw new IndexOutOfBoundsException("Can't set value. Pass correct input index");
    }

    @Override
    public T remove(int index) {
        if (index >= 0 && index < size) {
            return unlink(getIndexedNode(index));
        }
        throw new IndexOutOfBoundsException("Can't remove item. Pass correct input index");
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> node = head; node != null; node = node.next) {
                if (node.item == null) {
                    unlink(node);
                    return true;
                }
            }
        } else {
            for (Node<T> node = head; node != null; node = node.next) {
                if (object.equals(node.item)) {
                    unlink(node);
                    return true;
                }
            }
        }
        return false;
    }

    private T unlink(Node<T> node) {
        T item = node.item;
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

        node.item = null;
        size--;
        return item;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
