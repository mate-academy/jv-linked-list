package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (first == null) {
            first = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> newNode = new Node<>(null, value, null);
        Node nextNode = getNode(index);
        if (nextNode == first) {
            newNode.next = nextNode;
            first = newNode;
        } else {
            newNode.prev = nextNode.prev;
            newNode.next = nextNode.next;
            nextNode.prev.next = newNode;
        }
        nextNode.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T changedValue = node.value;
        node.value = value;
        return changedValue;
    }

    @Override
    public T remove(int index) {
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        do {
            T searchObject = currentNode.value;
            if (searchObject == object || searchObject != null && searchObject.equals(object)) {
                unlink(currentNode);
                return true;
            }
            currentNode = currentNode.next;
        } while (currentNode != last);
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

    private Node<T> getNode(int index) {
        checkIndex(index);
        if (index < size / 2) {
            Node<T> node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        } else {
            Node<T> node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
            return node;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + " is out of bounds!");
        }
    }

    private T unlink(Node<T> node) {
        T unlinkedValue = node.value;
        if (node == first) {
            first = node.next;
            node.prev = null;
        } else if (node == last) {
            last = node.prev;
            node.next = null;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        size--;
        return unlinkedValue;
    }

    public static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
