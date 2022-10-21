package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        addLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, size + 1);
        if (index == 0) {
            addFirst(value);
            return;
        }
        if (index == size) {
            addLast(value);
            return;
        }
        addMiddle(value, index);
    }

    @Override
    public void addAll(List<T> list) {
        for (T listElement : list) {
            add(listElement);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index, size);
        return (T) findNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, size);
        Node node = findNode(index);
        T oldValue = (T) node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, size);
        Node node = findNode(index);
        T removed = (T) node.item;
        unlink(node);
        return removed;
    }

    @Override
    public boolean remove(T object) {
        int index = 0;
        Node node = first;
        while (node != null) {
            if ((object == null && node.item == null)
                    || object != null && (object.equals(node.item))) {
                unlink(node);
                return true;
            }
            index++;
            node = node.next;
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

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T element, Node<T> next) {
            this.prev = prev;
            this.item = element;
            this.next = next;
        }
    }

    private void checkIndex(int index, int size) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index is not valid. Index: "
                    + index + ". Size of list: " + size);
        }
    }

    private void addMiddle(T value, int index) {
        Node node = findNode(index);
        Node middleNode = new Node(node.prev, value, node);
        node.prev.next = middleNode;
        node.prev = middleNode;
        size++;
    }

    private void addLast(T value) {
        Node lastNode = new Node(last, value, null);
        last = lastNode;
        if (lastNode.prev != null) {
            lastNode.prev.next = lastNode;
        }
        size++;
        if (first == null) {
            first = lastNode;
        }
    }

    private void addFirst(T value) {
        Node firstNode = new Node(null, value, first);
        first = firstNode;
        if (firstNode.next != null) {
            firstNode.next.prev = firstNode;
        }
        size++;
        if (last == null) {
            last = firstNode;
        }
    }

    private Node findNode(int index) {
        if (index < size / 2) {
            Node node = first;
            for (int i = 0; i <= size / 2; i++) {
                if (i == index) {
                    return node;
                }
                node = node.next;
            }
        }
        Node node = last;
        for (int i = size - 1; i >= size / 2; i--) {
            if (i == index) {
                return node;
            }
            node = node.prev;
        }
        return null;
    }

    private void unlink(Node node) {
        if (node == first && node == last) {
            first = null;
            last = null;
            size--;
            return;
        }
        if (node == first) {
            node.next.prev = null;
            first = node.next;
            size--;
            return;
        }
        if (node == last) {
            node.prev.next = null;
            last = node.prev;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
        size--;
    }
}
