package core.basesyntax;

import java.util.Collection;
import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
    }

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
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " out of bounce of size " + size);
        }

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
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " out of bounce of size " + size);
        }

        if (collection.isEmpty()) {
            throw new RuntimeException("Can't add elements from empty collection " + collection);
        }
        Object[] objects = collection.toArray();

        if (index == size) {
            for (Object o : objects) {
                add((T) o);
            }
        } else {
            for (Object o : objects) {
                linkBefore((T) o, getNode(index));
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
        if (object == null) {
            for (Node<T> current = head; current != null; current = current.next) {
                if (current.item == null) {
                    unlink(current);
                    return true;
                }
            }
        } else {
            for (Node<T> current = head; current != null; current = current.next) {
                if (object.equals(current.item)) {
                    unlink(current);
                    return true;
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

    private void linkLast(T value) {
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

    private void linkBefore(T value, Node<T> succeeding) {
        if (succeeding == null) {
            throw new IndexOutOfBoundsException("Can't link element "
                    + value + " before null Node " + succeeding);
        }

        final Node<T> precedent = succeeding.prev;
        final Node<T> newNode = new Node<>(precedent, value, succeeding);
        succeeding.prev = newNode;

        if (precedent == null) {
            head = newNode;
        } else {
            precedent.next = newNode;
        }
        size++;
    }

    private Node<T> getNode(int index) {
        Node<T> result = head;
        checkElementIndex(index);

        for (int i = 0; i < index; i++) {
            result = result.next;
        }
        return result;
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

    private void checkElementIndex(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index
                    + " out of bounce of size " + size);
        }
    }
}
