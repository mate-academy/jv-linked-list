package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    private static class Node<E> {
        private E data;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.data = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        if (!isEmpty()) {
            Node<T> newNode = new Node<>(last, value, null);
            last.next = newNode;
            last = newNode;
        } else {
            first = new Node<T>(null, value, null);
            last = first;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null, value, first);
            first.prev = newNode;
            first = newNode;
        } else {
            indexValidation(index);
            Node<T> nodeByIndex = getCurrentNode(index);
            Node<T> newNode = new Node<>(nodeByIndex.prev, value, nodeByIndex);
            nodeByIndex.prev.next = newNode;
            nodeByIndex.prev = newNode;
        }
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
        indexValidation(index);
        return getCurrentNode(index).data;
    }

    @Override
    public T set(T value, int index) {
        indexValidation(index);
        Node<T> nodeForReplace = getCurrentNode(index);
        T previousData = nodeForReplace.data;
        nodeForReplace.data = value;
        return previousData;
    }

    @Override
    public T remove(int index) {
        indexValidation(index);
        Node<T> nodeForRemove = getCurrentNode(index);
        return unlink(nodeForRemove);
    }

    @Override
    public boolean remove(T object) {
        Node<T> node;
        for (node = first; node != null; node = node.next) {
            if (object != null && object.equals(node.data) || node.data == object) {
                unlink(node);
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

    private void indexValidation(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Wrong index! " + index);
        }
    }

    private Node<T> getCurrentNode(int index) {
        Node<T> current;
        if (index < size / 2) {
            current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = last;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private T unlink(Node<T> node) {
        Node<T> nextNode = node.next;
        Node<T> prevNode = node.prev;
        if (prevNode == null) {
            first = nextNode;
        } else {
            prevNode.next = nextNode;
            node.prev = null;
        }
        if (nextNode == null) {
            last = prevNode;
        } else {
            nextNode.prev = prevNode;
            node.next = null;
        }
        T unlinkedData = node.data;
        node.data = null;
        size--;
        return unlinkedData;
    }
}
