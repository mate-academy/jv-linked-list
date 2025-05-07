package core.basesyntax;

import java.util.Iterator;
import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(value, null, null);
        if (first == null) {
            first = last = node;
        } else {
            last.next = node;
            node.prev = last;
            last = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        Node<T> newNode = new Node<>(value,null, null);
        Node<T> indexNode = findByIndex(index);
        newNode.next = indexNode;
        if (indexNode.prev != null) {
            newNode.prev = indexNode.prev;
            indexNode.prev.next = newNode;
            indexNode.prev = newNode;
        } else {
            first.prev = newNode;
            newNode.next = first;
            first = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            add((T)iterator.next());
        }
    }

    @Override
    public T get(int index) {
        Node<T> indexNode = findByIndex(index);
        return indexNode != null ? indexNode.item : null;
    }

    @Override
    public T set(T value, int index) {
        T returnValue = findByIndex(index).item;
        findByIndex(index).item = value;
        return returnValue;
    }

    @Override
    public T remove(int index) {
        Node<T> removedNode = findByIndex(index);
        unlink(removedNode);
        size--;
        return removedNode.item;
    }

    @Override
    public boolean remove(T object) {
        if (first == null) {
            return false;
        }
        Node<T> nodeToRemove = first;
        while (!((nodeToRemove.item == object)
                || (nodeToRemove.item != null && nodeToRemove.item.equals(object)))) {
            nodeToRemove = nodeToRemove.next;
            if (nodeToRemove == null) {
                return false;
            }
        }
        unlink(nodeToRemove);
        size--;
        return nodeToRemove.prev == null && nodeToRemove.next == null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> findByIndex(int index) {
        checkIndex(index);
        if (index == 0) {
            return first;
        } else if (index == size - 1) {
            return last;
        }

        Node<T> returnedNode = first;
        if (index <= (size - 1) / 2) {
            for (int i = 0; i < index; i++) {
                returnedNode = returnedNode.next;
            }
        } else {
            returnedNode = last;
            for (int i = size - 1; i > index; i--) {
                returnedNode = returnedNode.prev;
            }
        }
        return returnedNode;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    private void unlink(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            first = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            last = node.prev;
        }

        node.next = null;
        node.prev = null;
    }

    static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(T item, Node<T> prev, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}
