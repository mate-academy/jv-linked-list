package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public boolean add(T value) {
        if (size == 0) {
            Node<T> first = new Node<>(null, value, null);
            head = first;
            tail = first;
        } else {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            add(value);
        } else {
            Node<T> current = getNode(index);
            Node<T> left = current.prev;
            Node<T> newNode = new Node<>(left, value, current);
            if (left == null) {
                head = newNode;
            } else {
                current.prev = newNode;
                left.next = newNode;
            }
            size++;
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
        return true;
    }

    @Override
    public T get(int index) {
        indexChecker(index);
        return getNode(index).items;
    }

    @Override
    public T set(T value, int index) {
        indexChecker(index);
        Node<T> oldNode = getNode(index);
        T oldValue = oldNode.items;
        oldNode.items = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        indexChecker(index);
        Node<T> oldNode = getNode(index);
        return deleteNode(oldNode);
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if (currentNode.items == null || currentNode.items.equals(object)) {
                deleteNode(currentNode);
                return true;
            }
            currentNode = head.next;
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

    private void indexChecker(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Node<T> getNode(int index) {
        if (index < size / 2) {
            Node<T> first = head;
            for (int i = 0; i < index; i++) {
                first = first.next;
            }
            return first;
        } else {
            Node<T> last = tail;
            for (int i = size - 1; i > index; i--) {
                last = last.prev;
            }
            return last;
        }
    }

    private T deleteNode(Node<T> node) {
        Node<T> oldNode = node;
        Node<T> prevNode = oldNode.prev;
        Node<T> nextNode = oldNode.next;
        final T returnElement = oldNode.items;
        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
        }
        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prev = prevNode;
        }
        size--;
        return returnElement;
    }

    private static class Node<T> {
        private T items;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T items, Node<T> next) {
            this.prev = prev;
            this.items = items;
            this.next = next;
        }
    }
}
