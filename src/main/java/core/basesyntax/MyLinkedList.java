package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, node(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            throw new NullPointerException("List to add cannot be null");
        }
        if (list.isEmpty()) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            linkLast(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        Node<T> node = node(index);
        if (node != null) {
            return node(index).value;
        }
        return null;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> currentlyNode = node(index);
        T oldValue = currentlyNode.value;
        currentlyNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(node(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentlyNode = first;
        for (int i = 0; i < size; i++) {
            if (currentlyNode != null) {
                if (object == null ? currentlyNode.value == null
                        : object.equals(currentlyNode.value)) {
                    T removedValue = unlink(currentlyNode);
                    return true;
                } else {
                    currentlyNode = currentlyNode.next;
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

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + " Size: " + size);
        }
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + " Size: " + size);
        }
    }

    private void linkLast(T value) {
        final Node<T> lastNode = last;
        final Node<T> newNode = new Node<>(lastNode, value, null);

        last = newNode;

        if (lastNode == null) {
            first = newNode;
        } else {
            lastNode.next = newNode;
        }
        size++;
    }

    private void linkBefore(T value, Node<T> nodePrev) {
        final Node<T> prev = nodePrev.prev;
        final Node<T> newNode = new Node<>(prev, value, nodePrev);

        nodePrev.prev = newNode;

        if (prev == null) {
            first = newNode;
        } else {
            prev.next = newNode;
        }
        size++;
    }

    private Node<T> node(int index) {
        Node<T> currentlyNode = first;
        for (int i = 0; i < index; i++) {
            currentlyNode = currentlyNode.next;
        }
        return currentlyNode;
    }

    class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private T unlink(Node<T> node) {
        final T oldValue = node.value;
        final Node<T> prev = node.prev;
        final Node<T> next = node.next;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }

        node.value = null;
        size--;

        return oldValue;
    }
}
