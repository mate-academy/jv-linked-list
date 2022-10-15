package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size = 0;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> thisNode = new Node<>(tail, value, null);
        if (head == null) {
            head = thisNode;
        } else {
            tail.next = thisNode;
        }
        tail = thisNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        Node<T> prevNode = getNode(index).prev;
        Node<T> thisNode = new Node<>(prevNode, value, getNode(index));
        getNode(index).prev = thisNode;
        if (prevNode == null) {
            head = thisNode;
        } else {
            prevNode.next = thisNode;
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
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        Node<T> current = getNode(index);
        T oldValue = current.item;
        current.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        return unlink(getNode(index));
    }

    @Override
    public boolean remove(T object) {
        boolean remove = false;
        if (object == null) {
            for (Node<T> i = head; i != null; i = i.next) {
                if (i.item == null) {
                    unlink(i);
                    remove = true;
                }
            }
        } else {
            for (Node<T> i = head; i != null; i = i.next) {
                if (object == i.item || object != null && object.equals(i.item)) {
                    unlink(i);
                    remove = true;
                }
            }
        }
        return remove;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void checkIndexCorrect(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("This value of index: "
                    + index
                    + ", is not correct");
        }
    }

    private Node<T> getNode(int index) {
        checkIndexCorrect(index);
        Node<T> node;
        if (index < (size / 2)) {
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

    private T unlink(Node<T> node) {
        Node<T> prev = node.prev;
        Node<T> next = node.next;
        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            node.prev = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        T element = node.item;
        node.item = null;
        size--;
        return element;
    }
}
