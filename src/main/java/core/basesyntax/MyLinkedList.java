package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static class Node<T> {
        private Node<T> prevNode;
        private Node<T> nextNode;
        private T value;

        public Node(Node<T> prevNode, T value, Node<T> nextNode) {
            this.prevNode = prevNode;
            this.value = value;
            this.nextNode = nextNode;
        }
    }

    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        linkTail(value);
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            linkTail(value);
            return;
        }
        if (index == 0) {
            linkHead(value);
            return;
        }
        Node<T> current = getNodeByIndex(index);
        Node<T> newNode = new Node<>(current.prevNode, value, current);
        current.prevNode.nextNode = newNode;
        current.prevNode = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            linkTail(element);
        }
    }

    @Override
    public T get(int index) {
        if (index == 0) {
            return head.value;
        }
        validateIndex(index);
        if (index == size - 1) {
            return tail.value;
        }
        return getNodeByIndex(index).value;

    }

    @Override
    public T set(T value, int index) {
        Node<T> current = getNodeByIndex(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> current = getNodeByIndex(index);
        unlink(current);
        return current.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if ((current.value == object)
                    || (current.value != null && current.value.equals(object))) {
                unlink(current);
                return true;
            }
            current = current.nextNode;
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

    private Node<T> getNodeByIndex(int index) {
        validateIndex(index);
        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.nextNode;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prevNode;
            }
        }
        return current;
    }

    private void linkHead(T value) {
        Node<T> newNode = new Node<>(null, value, head);
        head.prevNode = newNode;
        newNode.nextNode = head;
        head = newNode;
        size++;
    }

    private void linkTail(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (head == null) {
            head = newNode;
            tail = newNode;
            size++;
            return;
        }
        tail.nextNode = newNode;
        tail = newNode;
        size++;
    }

    private void unlink(Node<T> current) {
        if (current.equals(head)) {
            if (head.nextNode != null) {
                head.nextNode.prevNode = null;
                head = head.nextNode;
            } else {
                head = null;
                tail = null;
            }
        } else if (current.equals(tail)) {
            tail.prevNode.nextNode = null;
            tail = tail.prevNode;
        } else {
            current.nextNode.prevNode = current.prevNode;
            current.prevNode.nextNode = current.nextNode;
        }
        size--;
    }

    private void validateIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(
                    "LinkedList don`t contains index " + index);
        }
    }
}
