package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(tail, value, null);
        if (size == 0) {
            head = tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> node = new Node<>(null, value, null);
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        if (index == 0) {
            node = new Node<>(null, value, head);
            node.next = head;
            head = node;
            size++;
        } else {
            Node<T> currNode = getNodeByIndex(index - 1);
            node.next = currNode.next;
            node.prev = currNode;
            currNode.next.prev = node;
            currNode.next = node;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNodeByIndex(index);
        T parameter = node.value;
        node.value = value;
        return parameter;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNodeByIndex(index);
        unlink(node);
        return node.value;
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

    private void unlink(Node<T> node) {
        if (node == head) {
            head = node.next;
        } else if (node == tail) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
        size--;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid Index: " + index + ", size: " + size);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        int divList = size / 2;
        if (index <= divList) {
            Node<T> countNode = head;
            int count = 0;
            while (count <= divList) {
                if (count == index) {
                    return countNode;
                }
                count++;
                countNode = countNode.next;
            }
        } else {
            Node<T> countNode = tail;
            int count = size - 1;
            while (count > divList) {
                if (count == index) {
                    return countNode;
                }
                count--;
                countNode = countNode.prev;
            }
        }
        return null;
    }

    static class Node<T> {
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
