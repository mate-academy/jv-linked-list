package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Wrong index " + index + " for size " + size);
        }
        if (index == size) {
            Node<T> newNode = new Node<>(last, value, null);
            if (first == null) {
                first = newNode;
            } else {
                last.next = newNode;
            }
            last = newNode;
        } else if (index > 0) {
            Node<T> newNode = findNodeByIndex(index);
            Node<T> currentNode = new Node<>(newNode.prev, value, newNode);
            currentNode.prev.next = currentNode;
            currentNode.next.prev = currentNode;
        } else {
            Node<T> newNode = first;
            Node<T> currentNode = new Node<>(null, value, newNode);
            newNode.prev = currentNode;
            first = currentNode;
        }
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
        checkIndex(index);
        Node<T> newNode = findNodeByIndex(index);
        return newNode.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> newNode = findNodeByIndex(index);
        T oldValue = newNode.value;
        newNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(findNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        while (currentNode != null) {
            if (currentNode.value == null && object == null || currentNode.value.equals(object)) {
                unlink(currentNode);
                return true;
            }
            currentNode = currentNode.next;
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
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Wrong index " + index + " for size " + size);
        }
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> currentNode;
        if (index <= size / 2) {
            currentNode = first;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = last;
            for (int i = size; i > index + 1; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private T unlink(Node<T> node) {
        T removedNodeValue = node.value;
        if (first == last) {
            first = null;
            last = null;
        } else if (first == node) {
            node.next.prev = null;
            first = node.next;
        } else if (last == node) {
            node.prev.next = null;
            last = node.prev;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
        size--;
        return removedNodeValue;
    }
}
