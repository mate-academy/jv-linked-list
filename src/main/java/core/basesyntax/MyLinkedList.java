package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String ERROR_MESSAGE_TEMPLATE = "Index: %d, Size: %d";

    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkAddPossibility(index);
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        list.forEach(this::add);
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        return node.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNodeByIndex(index);
        unlink(node);
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        while (current != null) {
            if ((object == null && current.value == null)
                    || (object != null && object.equals(current.value))) {
                unlink(current);
                return true;
            }
            current = current.next;
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

    private void checkAddPossibility(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(String.format(ERROR_MESSAGE_TEMPLATE, index, size));
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format(ERROR_MESSAGE_TEMPLATE, index, size));
        }
    }

    private void linkLast(T value) {
        Node<T> temp = last;
        Node<T> newNode = new Node<>(temp, value, null);
        last = newNode;
        if (temp == null) {
            first = newNode;
        } else {
            temp.next = newNode;
        }
        size++;
    }

    private void linkBefore(T value, int index) {
        Node<T> current = getNodeByIndex(index);
        Node<T> newNode = new Node<>(current.prev, value, current);
        if (current.prev != null) {
            current.prev.next = newNode;
        } else {
            first = newNode;
        }
        current.prev = newNode;
        size++;
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        return size / 2 <= index ? getNodeByIndexFromHead(index) : getNodeByIndexFromTail(index);
    }

    private Node<T> getNodeByIndexFromHead(int index) {
        Node<T> currentNode = first;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private Node<T> getNodeByIndexFromTail(int index) {
        Node<T> currentNode = last;
        for (int i = size - 1; i > index; i--) {
            currentNode = currentNode.prev;
        }
        return currentNode;
    }

    private void unlink(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            first = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            last = node.prev;
        }
        size--;
    }

    private static class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
