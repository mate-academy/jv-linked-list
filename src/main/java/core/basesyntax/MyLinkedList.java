package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;
    private Node<T> temporaryNode;

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        temporaryNode = new Node<>(tail, value, null);
        if (size == 0) {
            head = tail = temporaryNode;
        } else {
            tail = tail.next = temporaryNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index " + index + " for size: " + size);
        }
        if (size == 0) {
            head = tail = new Node<>(tail, value, null);
        } else if (index == 0) {
            head = head.prev = new Node<>(null, value, head);
        } else if (size == index) {
            tail = tail.next = new Node<>(tail, value,null);
        } else {
            temporaryNode = getNode(index - 1);
            temporaryNode.next.prev = temporaryNode.next = new Node<>(temporaryNode,
                    value, temporaryNode.next);
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        temporaryNode = getNode(index);
        T oldValue = temporaryNode.value;
        temporaryNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        temporaryNode = getNode(index);
        unlinkNode(temporaryNode);
        return temporaryNode.value;
    }

    @Override
    public boolean remove(T object) {
        temporaryNode = head;
        for (int i = 0; i < size; i++) {
            if (temporaryNode.value != null && temporaryNode.value.equals(object)
                    || temporaryNode.value == object) {
                unlinkNode(temporaryNode);
                return true;
            }
            temporaryNode = temporaryNode.next;
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

    private Node<T> getNode(int index) {
        if (index <= size / 2) {
            temporaryNode = head;
            for (int i = 0; i < index; i++) {
                temporaryNode = temporaryNode.next;
            }
        } else {
            temporaryNode = tail;
            for (int i = size - 1; i > index; i--) {
                temporaryNode = temporaryNode.prev;
            }
        }
        return temporaryNode;
    }

    private void unlinkNode(Node<T> node) {
        if (node.prev == null) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node.next == null) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        size--;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index " + index + " for size: " + size);
        }
    }
}
