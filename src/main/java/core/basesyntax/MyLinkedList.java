package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (size > 0) {
            addNodeToEnd(value);
        } else {
            addFirst(value);
        }
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == 0 && size > 0) {
            Node<T> newFirstNode = new Node<>(null,value,first);
            first.prev = newFirstNode;
            first = newFirstNode;
            size++;
        } else if (index == 0 && size == 0) {
            addFirst(value);
        } else if (index == size) {
            addNodeToEnd(value);
        } else {
            Node<T> prevNode = getNode(index - 1);
            Node<T> currentNode = getNode(index);
            Node<T> nextNode = getNode(index + 1);
            Node<T> node = new Node<>(prevNode, value, currentNode);
            prevNode.next = node;
            nextNode.prev = node;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T obj : list) {
            add(obj);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T oldValue = getNode(index).item;
        getNode(index).item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T removedItem;
        if (index == 0) {
            removedItem = first.item;
            first = first.next;
            if (first != null) {
                first.prev = null;
            }
            size--;
            return removedItem;
        } else if (index == (size - 1)) {
            removedItem = last.item;
            last = last.prev;
            last.next = null;
            size--;
            return removedItem;
        }
        removedItem = getNode(index).item;
        unlink(getNode(index));
        return removedItem;
    }

    @Override
    public boolean remove(T object) {
        Node<T> suppNode = first;
        for (int i = 0; i < size; i++) {
            if (suppNode.item == object || (suppNode.item != null
                    && suppNode.item.equals(object))) {
                remove(i);
                return true;
            }
            suppNode = suppNode.next;
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

    private void addFirst(T value) {
        first = new Node<>(null, value, null);
        last = first;
        size++;
    }

    private void addNodeToEnd(T value) {
        Node<T> nextNode = new Node<>(last, value, null);
        last.next = nextNode;
        last = nextNode;
        size++;
    }

    private Node<T> getNode(int index) {
        Node<T> suppNode = first;
        if (index <= size / 2) {
            for (int i = 0; i < index; i++) {
                suppNode = suppNode.next;
            }
        } else if (index > size / 2) {
            suppNode = last;
            for (int i = size - 1; i > index; i--) {
                suppNode = suppNode.prev;
            }
        }
        return suppNode;
    }

    private void unlink(Node<T> node) {
        Node<T> prevNode = node.prev;
        Node<T> nextNode = node.next;
        prevNode.next = nextNode;
        nextNode.prev = prevNode;
        size--;
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index out of length");
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index out of length");
        }
    }
}
