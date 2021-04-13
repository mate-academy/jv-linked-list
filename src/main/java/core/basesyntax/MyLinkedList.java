package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public boolean add(T value) {
        if (head == null) {
            setHead(value);
        } else {
            setTail(value);
        }
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        chekIndex(index);
        if (index == 0) {
            setHead(value);
        } else {
            Node<T> currentNode = getNode(index);
            Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
            currentNode.prev.next = newNode;
            currentNode.prev = newNode;
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
        return true;
    }

    @Override
    public T get(int index) {
        chekIndex(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        chekIndex(index);
        Node<T> node = getNode(index);
        T oldNodeValue = node.value;
        node.value = value;
        return oldNodeValue;
    }

    @Override
    public T remove(int index) {
        chekIndex(index);
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (node.value != null && node.value.equals(object) || node.value == object) {
                unlink(node);
                return true;
            }
            node = node.next;
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

    private void chekIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    private void setHead(T element) {
        if (head == null) {
            Node<T> newNode = new Node<>(null, element, null);
            head = newNode;
            tail = newNode;
        } else {
            head = new Node<>(null, element, head);
        }
    }

    private void setTail(T element) {
        Node<T> newNode = new Node<>(tail, element, null);
        tail.next = newNode;
        tail = newNode;
    }

    private Node<T> getNode(int index) {
        Node<T> neededNode;
        if (index <= size / 2) {
            neededNode = head;
            for (int i = 0; i < index; i++) {
                neededNode = neededNode.next;
            }
        } else {
            neededNode = tail;
            for (int i = size - 1; i > index; i--) {
                neededNode = neededNode.prev;
            }
        }
        return neededNode;
    }

    private T unlink(Node<T> unlinkNode) {
        if (head.equals(unlinkNode)) {
            if (size == 1) {
                head = null;
                tail = null;
            }
            head = unlinkNode.next;
        } else if (unlinkNode.next != null) {
            unlinkNode.next.prev = unlinkNode.prev;
            unlinkNode.prev.next = unlinkNode.next;
        }
        size--;
        return unlinkNode.value;
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;

        }
    }
}
