package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String INDEX_ERROR_MESSAGE = "Index is out of list`s interval";
    private int size;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
        first = new Node<>(null, null, null);
        last = new Node<>(null, null, null);
        size = 0;
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (isEmpty()) {
            createFirstNode(newNode);
        }

        newNode.prev = last;
        last.next = newNode;
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(INDEX_ERROR_MESSAGE);
        }
        Node<T> newNode = new Node<>(last, value, null);
        if (index == 0 && size == 0) {
            createFirstNode(newNode);
            size++;
        } else if (index == 0 && size > 0) {
            newNode.next = first;
            first.prev = newNode;
            first = newNode;
            size++;
        } else if (index == size) {
            createLastNode(newNode);
        } else {
            Node<T> current = findNodeByIndex(index);
            newNode.next = current;
            newNode.prev = current.prev;
            current.prev = newNode;
            newNode.prev.next = newNode;
            size++;
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
        checkIndex(index);
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T previousValue = findNodeByIndex(index).item;
        findNodeByIndex(index).item = value;
        return previousValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(findNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        while (current != null) {
            if (current.item == object || (current.item != null
                    && current.item.equals(object))) {
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

    private void createFirstNode(Node<T> newNode) {
        first = newNode;
        last = newNode;
    }

    private void createLastNode(Node<T> newNode) {
        last.next = newNode;
        newNode.prev = last;
        last = newNode;
        size++;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(INDEX_ERROR_MESSAGE);
        }
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> current = first;
        if (index < size / 2) {
            int position = 0;
            while (position < index) {
                current = current.next;
                position++;
            }
        } else {
            current = last;
            int position = size - 1;
            while (position > index) {
                current = current.prev;
                position--;
            }
        }
        return current;
    }

    private T unlink(Node<T> node) {
        T element = node.item;
        if (node == first && node == last) {
            first = null;
            last = null;
            size--;
            return element;
        } else if (node == first) {
            first = node.next;
            first.prev = null;
            size--;
            return element;
        } else if (node == last) {
            last = node.prev;
            last.next = null;
            size--;
            return element;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
            return element;
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.item = value;
            this.next = next;
        }
    }
}
