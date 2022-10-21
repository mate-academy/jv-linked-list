package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

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
        Node middleNode = new Node(findNode(index - 1), value, findNode(index));
        findNode(index - 1).next = middleNode;
        findNode(index).prev = middleNode;
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
            Node getNode = first;
            for (int i = 0; i <= size / 2; i++) {
                if (i == index) {
                    return getNode;
                }
                getNode = getNode.next;
            }
        }
        Node getNode = last;
        for (int i = size - 1; i >= size / 2; i--) {
            if (i == index) {
                return getNode;
            }
            getNode = getNode.prev;
        }
        return null;
    }

    private void unlink(Node node, int index) {
        if (size == 1) {
            first = null;
            last = null;
            size--;
            return;
        }
        if (index == 0) {
            node.next.prev = null;
            first = node.next;
            size--;
            return;
        }
        if (index == size - 1) {
            node.prev.next = null;
            last = node.prev;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
        size--;
    }

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
        T oldValue = (T) findNode(index).item;
        findNode(index).item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, size);
        T removed = (T) findNode(index).item;
        unlink(findNode(index), index);
        return removed;
    }

    @Override
    public boolean remove(T object) {
        int index = 0;
        Node getNode = first;
        while (getNode != null) {
            if ((object == null && getNode.item == null)
                    || object != null && (object.equals(getNode.item))) {
                remove(index);
                return true;
            }
            index++;
            getNode = getNode.next;
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
}
