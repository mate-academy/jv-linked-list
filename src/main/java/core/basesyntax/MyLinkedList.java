package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> first;
    private Node<T> last;
    private int size = 0;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        if (size == 0) {
            first = last = newNode;
        } else {
            last.next = newNode;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {

        if (index == size) {
            add(value);
            return;
        }
        Node<T> node = findNode(index);
        Node<T> newNode = new Node<>(node.prev, value, node);
        if (node.equals(first)) {
            first = newNode;
        } else {
            node.prev.next = newNode;
        }
        node.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        return findNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = findNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        return unlink(findNode(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> node = first; node != null; node = node.next) {
            if (object == node.value || object != null && object.equals(node.value)) {
                unlink(node);
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
        return first == null;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Illegal index " + index);
        }
    }

    private Node<T> findNode(int index) {
        checkIndex(index);
        if (index <= size / 2) {
            Node<T> currentNode = first;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode;
        } else {
            Node<T> currentNode = last;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
            return currentNode;
        }
    }

    T unlink(Node<T> deletedNode) {
        final T value = deletedNode.value;
        final Node<T> next = deletedNode.next;
        final Node<T> prev = deletedNode.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            deletedNode.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            deletedNode.next = null;
        }

        deletedNode.value = null;
        size--;
        return value;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.value = element;
            this.next = next;
            this.prev = prev;
        }

    }
}
