package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node top;
    private Node bottom;

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index doesn't exist:" + index);
        }
    }

    private Node<T> getNode(int index) {
        Node node;
        if (index > size / 2) {
            node = bottom;
            for (int i = size - 1; i > 0; i--) {
                if (i == index) {
                    return node;
                }
                node = node.prev;
            }
        } else {
            node = top;
            for (int i = 0; i < size; i++) {
                if (i == index) {
                    return node;
                }
                node = node.next;
            }
        }
        return null;
    }

    private void unlinkNode(Node node) {
        if (node.next != null && node.prev != null) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        } else if (node.next == null && node.prev == null) {
            bottom = null;
            top = null;
        } else if (node.next == null) {
            bottom = node.prev;
            node.prev.next = null;
        } else if (node.prev == null) {
            top = node.next;
            node.next.prev = null;
        }
        size--;
    }

    @Override
    public void add(T value) {
        Node last = bottom;
        Node newNode = new Node<>(last, value, null);
        bottom = newNode;
        if (last == null) {
            top = newNode;
        } else {
            last.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Uncorrect index:" + index);
        }
        Node node = getNode(index);
        if (node == null) {
            add(value);
            return;
        }
        Node newNode = new Node<>(node.prev, value, node);
        if (node.prev != null) {
            node.prev.next = newNode;
            node.prev = newNode;
        } else {
            newNode.prev = null;
            node.prev = newNode;
            top = newNode;
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
        checkElementIndex(index);
        return (T) getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        T beforeChange = getNode(index).value;
        getNode(index).value = value;
        return (T) beforeChange;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        final Node removedNode = getNode(index);
        unlinkNode(removedNode);
        return (T) removedNode.value;
    }

    @Override
    public boolean remove(T object) {
        for (Node node = top; node != null; node = node.next) {
            if (node.value != null && node.value.equals(object)
                    || node.value == object) {
                unlinkNode(node);
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

    private static class Node<E> {
        private E value;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.value = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
