package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            Node<T> item = new Node<>(null, value, null);
            head = item;
            tail = item;
        } else {
            Node<T> item = new Node<T>(tail, value, null);
            tail.next = item;
            tail = item;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        inSize(index);
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            Node<T> second = head;
            head = new Node<>(null, value, second);
            second.prev = head;
            size++;
            return;
        }
        Node<T> current = findNodeByIndex(index);
        Node<T> newItem = new Node<>(current.prev, value, current);
        current.prev.next = newItem;
        current.prev = newItem;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> current = findNodeByIndex(index);
        T result = current.item;
        current.item = value;
        return result;
    }

    @Override
    public T remove(int index) {
        Node<T> current = findNodeByIndex(index);
        return unlink(current);
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> node = head; node != null; node = node.next) {
            if (node.item == object || object != null && object.equals(node.item)) {
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

    private Node<T> findNodeByIndex(int index) {
        inSizeWithSize(index);
        if (index <= size / 2) {
            int indexLinked = 0;
            Node<T> current = head;
            while (indexLinked != index) {
                current = current.next;
                indexLinked++;
            }
            return current;
        }
        int indexLinked = size - 1;
        Node<T> current = tail;
        while (indexLinked != index) {
            current = current.prev;
            indexLinked--;
        }
        return current;

    }

    private void inSizeWithSize(int index) {
        if (index >= size || index < 0) {
            throw new ArrayIndexOutOfBoundsException("index outside the array");
        }
    }

    private void inSize(int index) {
        if (index > size || index < 0) {
            throw new ArrayIndexOutOfBoundsException("index outside the array");
        }
    }

    private T unlink(Node<T> currentNode) {
        Node<T> nextNode = currentNode.next;
        Node<T> prevNode = currentNode.prev;
        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
            currentNode.prev = null;
        }
        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prev = prevNode;
            currentNode.next = null;
        }
        T element = currentNode.item;
        currentNode.item = null;
        size--;
        return element;
    }
}
