package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size; // Corrected: removed static keyword
    private Node<T> head;
    private Node<T> tail;

    class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(null, value, null);
        if (head == null) {
            head = node;
            tail = node; // Corrected: tail should be set when the first node is added
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == size) {
            add(value);
            return;
        }
        Node<T> nodeAtIndex = findNodeByIndex(index);
        Node<T> newNode = new Node<>(nodeAtIndex.prev, value, nodeAtIndex);
        if (nodeAtIndex.prev != null) {
            nodeAtIndex.prev.next = newNode;
        } else {
            head = newNode;
        }
        nodeAtIndex.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T lists : list) {
            add(lists);
        }
    }

    @Override
    public T get(int index) {
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> nodeAtIndex = findNodeByIndex(index);
        T setNode = nodeAtIndex.item;
        nodeAtIndex.item = value;
        return setNode;
    }

    @Override
    public T remove(int index) {
        Node<T> removeNode = findNodeByIndex(index);
        unlink(removeNode);
        return removeNode.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> nodeRemove = head;
        while (nodeRemove != null) {
            if ((nodeRemove.item == object)
                    || (nodeRemove.item != null && nodeRemove.item.equals(object))) {
                unlink(nodeRemove);
                return true;
            }
            nodeRemove = nodeRemove.next;
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

    private Node<T> findNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index "
                    + index + " is out of bounds");
        }

        Node<T> indexNode;
        if (index < size / 2) {
            indexNode = head;
            for (int i = 0; i < index; i++) {
                indexNode = indexNode.next;
            }
        } else {
            indexNode = tail;
            for (int i = size - 1; i > index; i--) {
                indexNode = indexNode.prev;
            }
        }
        return indexNode;
    }

    private void unlink(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
        size--;
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The index "
                    + index + " is out of bounds. Size of the collection: " + size);
        }
    }
}
