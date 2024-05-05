package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        addLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index
                    + ", size: " + size);
        }
        if (index == size) {
            addLast(value);
        } else if (index == 0) {
            addFirst(value);
        } else {
            Node<T> previousNode = getNode(index - 1);
            Node<T> nextNode = previousNode.next;
            Node<T> newNode = new Node<>(value, previousNode, nextNode);

            previousNode.next = newNode;
            nextNode.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            addLast(value);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeToRemove = getNode(index);
        return unlink(nodeToRemove);
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> currentNode = head; currentNode != null; currentNode = currentNode.next) {
            if (object == null ? currentNode.value == null : object.equals(currentNode.value)) {
                unlink(currentNode);
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

    private T unlink(Node<T> node) {
        Node<T> previousNode = node.prev;
        Node<T> nextNode = node.next;
        if (previousNode == null) {
            head = nextNode;
        } else {
            previousNode.next = nextNode;
            node.prev = null;
        }
        if (nextNode == null) {
            tail = previousNode;
        } else {
            nextNode.prev = previousNode;
            node.next = null;
        }
        T element = node.value;
        node.value = null;
        size--;
        return element;
    }

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index
                    + ", Size: " + size);
        }

        if (index < (size / 2)) {
            Node<T> neededNode = head;
            for (int i = 0; i < index; i++) {
                neededNode = neededNode.next;
            }
            return neededNode;
        } else {
            Node<T> neededNode = tail;
            for (int i = size - 1; i > index; i--) {
                neededNode = neededNode.prev;
            }
            return neededNode;
        }
    }

    private void addFirst(T value) {
        Node<T> first = head;
        Node<T> newNode = new Node<>(value, null, first);
        head = newNode;

        if (first == null) {
            tail = newNode;
        } else {
            first.prev = newNode;
        }
        size++;
    }

    private void addLast(T value) {
        Node<T> last = tail;
        Node<T> newNode = new Node<>(value, last, null);
        tail = newNode;

        if (last == null) {
            head = newNode;
        } else {
            last.next = newNode;
        }
        size++;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
