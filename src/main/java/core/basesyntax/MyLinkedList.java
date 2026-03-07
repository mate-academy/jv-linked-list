package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(last, null, value);
        if (first == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, first, value);

            if (first == null) {
                last = newNode;
            } else {
                first.previous = newNode;
            }

            first = newNode;

            size++;
        } else {
            Node<T> oldNode = getNode(index);
            Node<T> oldNodePrev = oldNode.previous;
            Node<T> newNode = new Node<>(oldNodePrev, oldNode, value);

            oldNode.previous = newNode;
            oldNodePrev.next = newNode;

            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            throw new NullPointerException("List must not be null");
        }
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    private Node<T> getNode(int index) {
        checkIndexForGet(index);
        Node<T> currentNode;
        if (index < (size >> 1)) {
            currentNode = first;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = last;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.previous;
            }
        }
        return currentNode;
    }

    private void checkIndexForGet(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    String.format("Index should be between %d and %d, but was: %d", 0, size - 1, index)
            );
        }
    }
    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    String.format("Index should be between %d and %d, but was: %d", 0, size, index)
            );
        }
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = getNode(index);
        T oldValue = currentNode.value;
        currentNode.value = value;

        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNode(index);
        T removedValue = node.value;
        unlink(node);
        return removedValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        while (currentNode != null) {
            if ((currentNode.value == null && object == null) || (object != null && object.equals(currentNode.value))) {
                unlink(currentNode);
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    private void unlink(Node<T> currentNode) {
        Node<T> prev = currentNode.previous;
        Node<T> next = currentNode.next;
        if (prev != null) {
            prev.next = next;
        } else {
            first = next;
        }
        if (next != null) {
            next.previous = prev;
        } else {
            last = prev;
        }

        currentNode.next = null;
        currentNode.previous = null;
        currentNode.value = null;

        size--;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private static class Node<T> {
        private Node<T> previous;
        private Node<T> next;
        private T value;

        private Node(Node<T> previous, Node<T> next, T value) {
            this.previous = previous;
            this.next = next;
            this.value = value;
        }
    }
}
