package core.basesyntax;

import java.util.Collection;
import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        final Node<T> l = tail;
        final Node<T> newNode = new Node<>(l, value, null);
        tail = newNode;

        if (l == null) {
            head = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkAddingIndex(index);

        if (index == size) {
            add(value);
        } else {
            linkBefore(value, getNode(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        addAll(size, list);
    }

    private void addAll(int index, Collection<? extends T> collection) {
        checkAddingIndex(index);
        T[] objects = (T[]) collection.toArray();

        if (index == size) {
            for (T elem : objects) {
                add(elem);
            }
        } else {
            for (T element : objects) {
                linkBefore(element, getNode(index));
                index++;
            }
        }
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> setNode = getNode(index);
        final T oldValue = setNode.item;
        setNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> current = head; current != null; current = current.next) {
            if ((current.item != null && current.item.equals(object)) || current.item == object) {
                unlink(current);
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

    private void linkBefore(T value, Node<T> current) {
        if (current == null) {
            throw new IndexOutOfBoundsException("Can't link element "
                    + value + " before null Node " + current);
        }

        final Node<T> precedent = current.prev;
        final Node<T> newNode = new Node<>(precedent, value, current);
        current.prev = newNode;

        if (precedent == null) {
            head = newNode;
        } else {
            precedent.next = newNode;
        }
        size++;
    }

    private Node<T> getNode(int index) {
        checkElementIndex(index);

        if (index < (size / 2)) {
            Node<T> result = head;
            for (int i = 0; i < index; i++) {
                result = result.next;
            }
            return result;
        } else {
            Node<T> result = tail;
            for (int i = size - 1; i > index; i--) {
                result = result.prev;
            }
            return result;
        }
    }

    private T unlink(Node<T> removeNode) {
        if (removeNode == null) {
            throw new NullPointerException("Can't remove the non-exist element " + removeNode);
        }

        final T removedElement = removeNode.item;
        final Node<T> previous = removeNode.prev;
        final Node<T> next = removeNode.next;

        if (previous == null) {
            head = next;
        } else {
            previous.next = next;
            removeNode.prev = null;
        }

        if (next == null) {
            tail = previous;
        } else {
            next.prev = previous;
            removeNode.next = null;
        }

        removeNode.item = null;
        size--;
        return removedElement;
    }

    private void checkAddingIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " can't be greater then List size " + size);
        }
    }

    private void checkElementIndex(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " out of bounce of size " + size);
        }
    }
}
