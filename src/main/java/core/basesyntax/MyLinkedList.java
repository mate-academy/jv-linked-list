package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> lastNode;
    private Node<T> firstNode;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(lastNode, value, null);
        if (lastNode == null) {
            firstNode = node;
            lastNode = node;
        } else {
            lastNode.next = node;
            lastNode = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> node = new Node<>(null, value, firstNode);
            firstNode.prev = node;
            firstNode = node;
            size++;
            return;
        }
        Node<T> requiredNode = getNodeByIndex(index);
        Node<T> newNode = new Node<>(requiredNode.prev, value, requiredNode);
        requiredNode.prev.next = newNode;
        requiredNode.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T listItem : list) {
            add(listItem);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> requiredNode = getNodeByIndex(index);
        T oldItem = requiredNode.item;
        requiredNode.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        Node<T> removableNode = getNodeByIndex(index);
        unlink(removableNode);
        return removableNode.item;
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> node = firstNode; node != null; node = node.next) {
                if (node.item == null) {
                    unlink(node);
                    return true;
                }
            }
        } else {
            for (Node<T> node = firstNode; node != null; node = node.next) {
                if (object.equals(node.item)) {
                    unlink(node);
                    return true;
                }
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
            throw new IndexOutOfBoundsException("error index:" + index);
        }
    }

    private void unlink(Node<T> removableNode) {
        final Node<T> next = removableNode.next;
        final Node<T> prev = removableNode.prev;
        if (prev == null) {
            firstNode = next;
        } else {
            prev.next = next;
            removableNode.prev = null;
        }
        if (next == null) {
            lastNode = prev;
        } else {
            next.prev = prev;
            removableNode.next = null;
        }
        size--;
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        if (index < (size >> 1)) {
            Node<T> requiredNode = firstNode;
            for (int i = 0; i < index; i++) {
                requiredNode = requiredNode.next;
            }
            return requiredNode;
        } else {
            Node<T> requiredNode = lastNode;
            for (int i = size - 1; i > index; i--) {
                requiredNode = requiredNode.prev;
            }
            return requiredNode;
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}
