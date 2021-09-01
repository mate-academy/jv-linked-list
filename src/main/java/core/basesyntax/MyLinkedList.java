package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String NO_INDEX_MESSAGE = "Index does not exist: ";
    private int size;
    private Node<T> first;
    private Node<T> last;

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        private Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        if (size == 0) {
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
            return;
        }
        Node<T> node = getNode(index);
        Node<T> newNode = new Node<>(node.prev, value, node);
        if (index == 0) {
            first = newNode;
            size++;
            return;
        }
        node.prev.next = newNode;
        node.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        T oldValue = getNode(index).item;
        getNode(index).item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> i = first; i != null; i = i.next) {
            if ((object != null && object.equals(i.item)) || i.item == object) {
                unlink(i);
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

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(NO_INDEX_MESSAGE + index);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(NO_INDEX_MESSAGE + index);
        }
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> tempNode;
        if (index < size / 2) {
            tempNode = first;
            for (int i = 0; i < index; i++) {
                tempNode = tempNode.next;
            }
        } else {
            tempNode = last;
            for (int i = size - 1; i > index; i--) {
                tempNode = tempNode.prev;
            }
        }
        return tempNode;
    }

    private T unlink(Node<T> node) {
        if (node.prev == null) {
            first = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node.next == null) {
            last = node.prev;
        } else {
            node.next.prev = node.prev;
        }
        size--;
        return node.item;
    }
}
