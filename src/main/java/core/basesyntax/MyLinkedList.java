package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> node;
        if (isEmpty()) {
            node = new Node<>(null, value, null);
            head = node;
        } else {
            node = new Node<>(tail, value, null);
            tail.next = node;
        }
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is not valid");
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> nodeFromArrayByIndex = getNodeByIndex(index);
        if (nodeFromArrayByIndex != null) {
            Node<T> newElement = new Node<>(nodeFromArrayByIndex.prev, value,
                    nodeFromArrayByIndex);
            nodeFromArrayByIndex.prev = newElement;
            if (newElement.prev != null) {
                newElement.prev.next = newElement;
            } else {
                head = newElement;
            }
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
        Node<T> node = getNodeByIndex(index);
        return node == null ? null : node.value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNodeByIndex(index);
        if (node != null) {
            T oldValue = node.value;
            node.value = value;
            return oldValue;
        }
        return null;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNodeByIndex(index);
        if (node != null) {
            unlink(node);
            size--;
            return node.value;
        }
        return null;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if ((node.value == null && object == null)
                    || (node.value != null && node.value.equals(object))) {
                remove(i);
                return true;
            } else {
                node = node.next;
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

    private boolean checkIndex(int index) {
        if (index < size && index > -1) {
            return true;
        }
        throw new IndexOutOfBoundsException("Index is not valid");
    }

    private Node<T> getNodeByIndex(int index) {
        if (checkIndex(index)) {
            Node<T> node = head;
            if (index <= size / 2) {
                while (index > 0) {
                    node = node.next;
                    index--;
                }
            } else {
                node = tail;
                while (index < size - 1) {
                    node = node.prev;
                    index++;
                }
            }
            return node;
        }
        return null;
    }

    private void unlink(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
        node.next = null;
        node.prev = null;
    }

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
