package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        private T value;
        private Node<T> prevNode;
        private Node<T> nextNode;

        Node(Node<T> prevNode, T value, Node<T> nextNode) {
            this.prevNode = prevNode;
            this.value = value;
            this.nextNode = nextNode;
        }
    }

    @Override
    public void add(T value) {
        Node<T> last = tail;
        Node<T> newNode = new Node<>(last, value, null);
        tail = newNode;
        if (last == null) {
            head = newNode;
        } else {
            last.nextNode = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        insertBefore(value, findNode(index));
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        return findNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> replacedNode = findNode(index);
        T oldNodeValue = replacedNode.value;
        replacedNode.value = value;
        return oldNodeValue;
    }

    @Override
    public T remove(int index) {
        return unlinkNode(findNode(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> lookup = head; lookup != null; lookup = lookup.nextNode) {
            if (lookup.value == object || object != null && object.equals(lookup.value)) {
                unlinkNode(lookup);
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

    private Node<T> findNode(int index) {
        checkIndex(index);
        Node<T> reqNode;
        if (index < (size / 2)) {
            reqNode = head;
            for (int i = 0; i < index; i++) {
                reqNode = reqNode.nextNode;
            }
        } else {
            reqNode = tail;
            for (int i = size - 1; i > index; i--) {
                reqNode = reqNode.prevNode;
            }
        }
        return reqNode;
    }

    private void insertBefore(T value, Node<T> node) {
        Node<T> previous = node.prevNode;
        Node<T> newNode = new Node<>(previous, value, node);
        newNode.nextNode.prevNode = newNode;
        if (previous == null) {
            head = newNode;
        } else {
            newNode.prevNode.nextNode = newNode;
        }
        size++;
    }

    private T unlinkNode(Node<T> toRemove) {
        Node<T> next = toRemove.nextNode;
        Node<T> previous = toRemove.prevNode;

        if (previous == null) {
            head = next;
        } else {
            previous.nextNode = next;
            toRemove.prevNode = null;
        }

        if (next == null) {
            tail = previous;
        } else {
            next.prevNode = previous;
            toRemove.nextNode = null;
        }

        T newValue = toRemove.value;
        toRemove.value = null;
        size--;
        return newValue;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(
                    "Wrong index, must be within 0 and " + size);
        }
    }
}
