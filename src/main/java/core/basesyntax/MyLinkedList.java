package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    private T unlink(Node<T> removeNode) {
        Node<T> nextNode = removeNode.next;
        Node<T> prevNode = removeNode.prev;
        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
            removeNode.prev = null;
        }

        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prev = prevNode;
            removeNode.next = null;
        }
        T valueNode = removeNode.value;
        removeNode.value = null;
        size--;
        return valueNode;
    }

    private boolean isAvailableIndex(int index) {
        return index >= 0 && index < size;
    }

    private void linkLast(T value) {
        Node<T> lastNode = tail;
        Node<T> newNode = new Node(lastNode, value, null);
        tail = newNode;
        if (lastNode == null) {
            head = newNode;
        } else {
            lastNode.next = newNode;
        }
        size++;
    }

    private Node<T> node(int index) {
        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
            return current;
        }
    }

    private void linkBefore(T value, Node<T> findIndexNode) {
        Node<T> previous = findIndexNode.prev;
        Node<T> newNode = new Node(previous, value, findIndexNode);
        findIndexNode.prev = newNode;
        if (previous == null) {
            head = newNode;
        } else {
            previous.next = newNode;
        }
        size++;
    }

    @Override
    public boolean add(T value) {
        linkLast(value);
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (!(index >= 0 && index <= size)) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bound");
        }

        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, node(index));
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            T value = list.get(i);
            add(value);
        }
        return true;
    }

    @Override
    public T get(int index) {
        if (!(isAvailableIndex(index))) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bound");
        }
        return node(index).value;
    }

    @Override
    public T set(T value, int index) {
        if (!(isAvailableIndex(index))) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bound");
        }
        Node<T> findNode = node(index);
        T oldVal = findNode.value;
        findNode.value = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        if (!(isAvailableIndex(index))) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bound");
        }
        return unlink(node(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> temp = head;
        while (temp != null) {
            if (object == temp.value || Objects.equals(object, temp.value)) {
                unlink(temp);
                return true;
            }
            temp = temp.next;
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
