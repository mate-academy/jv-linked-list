package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> lastNode = last;
        Node<T> newNode = new Node<>(lastNode, value, null);
        last = newNode;
        if (lastNode == null) {
            first = newNode;
        } else {
            lastNode.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index is wrong");
        }
        if (index == size) {
            add(value);
        } else {
            addNode(value, findByIndex(index));
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
        checkIndex(index);
        Node<T> node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return (T) node.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> findNode = findByIndex(index);
        T oldNode = findNode.item;
        findNode.item = value;
        return oldNode;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T node = unlink(findByIndex(index));
        return node;
    }

    @Override
    public boolean remove(T object) {
        if (isEmpty()) {
            throw new IllegalArgumentException("list is empty");
        }
        Node<T> node = first;
        if (object == null) {
            for (int i = 0; i < size; i++) {
                if (object == node.item) {
                    unlink(node);
                    return true;
                }
                node = node.next;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (object.equals(node.item)) {
                    unlink(node);
                    return true;
                }
                node = node.next;
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

    private class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(T value) {
            this.item = value;
        }

        public Node(Node<T> prev, T value, Node<T> next) {
            this.item = value;
            this.prev = prev;
            this.next = next;
        }
    }

    private void addNode(T value, Node<T> node) {
        Node<T> prevNode = node.prev;
        Node<T> newNode = new Node<>(prevNode, value, node);
        node.prev = newNode;
        if (prevNode == null) {
            first = newNode;
        } else {
            prevNode.next = newNode;
        }
        size++;
    }

    private T unlink(Node<T> node) {
        Node<T> next = node.next;
        Node<T> prev = node.prev;
        if (node.prev == null) {
            first = next;
        } else {
            prev.next = next;
            node.prev = null;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        size--;
        return node.item;
    }

    private Node<T> findByIndex(int index) {
        Node<T> node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index is wrong");
        }
    }

}
