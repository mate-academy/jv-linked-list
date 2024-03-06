package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        size++;
        final Node<T> newNode = new Node<>(tail, value, null);
        tail = newNode;
        if (tail.prev == null) {
            head = newNode;
        } else {
            tail.prev.next = newNode;
        }
    }

    @Override
    public void add(T value, int index) {
        size++;
        isValidIndex(index);

        Node<T> newNode = new Node<>(null, value, null);

        if (index == 0) {
            newNode.next = head;
            head = newNode;
        } else if (index == size - 1) {
            getNodeByIndex(size - 2).next = newNode;
            newNode.prev = getNodeByIndex(size - 2);
            tail = newNode;
        } else {
            Node<T> prevNode = getNodeByIndex(index - 1);
            newNode.next = prevNode.next;
            newNode.prev = prevNode;
            prevNode.next.prev = newNode;
            prevNode.next = newNode;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        isValidIndex(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        isValidIndex(index);
        Node<T> currentNode;
        T oldVal;
        if (index == size) {
            currentNode = tail;
            currentNode.value = value;
            oldVal = currentNode.value;
            return oldVal;
        }
        currentNode = getNodeByIndex(index);
        oldVal = currentNode.value;
        currentNode.value = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        isValidIndex(index);
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> currentNode = head; currentNode != null; currentNode = currentNode.next) {
            if ((object == null && currentNode.value == null)
                    || (object != null && object.equals(currentNode.value))) {
                unlink(currentNode);
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

    private Node<T> getNodeByIndex(int index) {
        if (index <= size / 2) {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else {
            Node<T> current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
            return current;
        }
    }

    private void isValidIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private T unlink(Node<T> x) {
        final T element = x.value;
        final Node<T> next = x.next;
        final Node<T> prev = x.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.value = null;
        size--;
        return element;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
