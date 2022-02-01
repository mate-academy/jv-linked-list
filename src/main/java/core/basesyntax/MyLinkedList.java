package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        linkWithLast(value);
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkToAdd(index);
        if (index == size) {
            linkWithLast(value);
        } else {
            linkNode(value, findByIndex(index));
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list != null) {
            for (T element : list) {
                add(element);
            }
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return (findByIndex(index)).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = findByIndex(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(findByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        if (findByItem(object) != null) {
            unlink(findByItem(object));
            return true;
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Cann`t find index " + index);
        }
    }

    private void checkToAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Cann`t find index " + index);
        }
    }

    private Node<T> findByIndex(int index) {
        checkIndex(index);
        Node<T> currentNode;
        if (size / 2 > index) {
            currentNode = first;
            for (int i = 1; i <= index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = last;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private Node<T> findByItem(T value) {
        Node<T> current = first;
        while (current != null) {
            if (current.item == value || (current.item != null && (current.item).equals(value))) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    private T unlink(Node<T> node) {
        Node<T> prev = node.prev;
        Node<T> next = node.next;

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
        T result = node.item;
        node.item = null;
        size--;
        return result;
    }

    private void linkWithLast(T value) {
        final Node<T> node = new Node<>(last, value, null);
        if (last == null) {
            first = node;
        } else {
            last.next = node;
        }
        last = node;
    }

    private void linkNode(T value, Node<T> nextNode) {
        final Node<T> node = new Node<>(null, value, nextNode);
        if (nextNode.prev == null) {
            first = node;
        } else {
            nextNode.prev.next = node;
        }
        node.prev = nextNode.prev;
        nextNode.prev = node;
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
