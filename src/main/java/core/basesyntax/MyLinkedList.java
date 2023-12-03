package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> inputValue = new Node<>(null, value,null);
        if (head == null) {
            head = inputValue;
            tail = inputValue;
        } else {
            tail.next = inputValue;
            inputValue.prev = tail;
        }
        tail = inputValue;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> requiredIndex = getNode(index);
        Node<T> inputValue = new Node<>(requiredIndex.prev, value, requiredIndex);
        if (requiredIndex.prev == null) {
            head = inputValue;
        } else {
            requiredIndex.prev.next = inputValue;
            requiredIndex.prev = inputValue;
        }
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
        Node<T> requiredNode = getNode(index);
        return requiredNode.value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> requiredNode = getNode(index);
        T replaceValue = requiredNode.value;
        requiredNode.value = value;
        return replaceValue;
    }

    @Override
    public T remove(int index) {
        Node<T> getIndexNode = getNode(index);
        unlink(getIndexNode);
        return getIndexNode.value;
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

    public void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    public Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> node;
        if (index < size << 1) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

    public void unlink(Node<T> node) {
        Node<T> previousNode = node.prev;
        Node<T> nextNode = node.next;
        if (previousNode == null) {
            head = nextNode;
        } else {
            previousNode.next = nextNode;
        }
        if (nextNode == null) {
            tail = previousNode;
        } else {
            nextNode.prev = previousNode;
        }
        size--;
    }
}
