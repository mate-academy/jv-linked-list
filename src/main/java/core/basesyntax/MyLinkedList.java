package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String INDEX_OUT_OF_ARRAY_MESSAGE = "Index less than 0 or more than size";
    private int size = 0;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        if (head == null) {
            head = new Node<>(null, value, null);
            tail = head;
            size++;
            return;
        }
        Node<T> newNode = new Node<>(tail, value, null);
        Node<T> temp = tail;
        temp.next = newNode;
        tail = newNode;
        if (head.next == null) {
            head.next = temp;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index != size) {
            rangeCheck(index);
        }
        if (head == null || head == tail) {
            add(value);
            return;
        }
        Node<T> node = getNode(index);
        if (node == head) {
            Node<T> newNode = new Node<>(null, value, node);
            node.previous = newNode;
            head = newNode;
            size++;
            return;
        }
        if (node == tail && index == size) {
            Node<T> newNode = new Node<>(node, value, null);
            node.next = newNode;
            tail = newNode;
            size++;
            return;
        }
        Node<T> newNode = new Node<>(node.previous, value, node);
        node.previous.next = newNode;
        node.previous = newNode;
        if (node.next != null) {
            node.next = node.next.previous;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public T get(int index) {
        rangeCheck(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        rangeCheck(index);
        Node<T> node = getNode(index);
        T ele = node.value;
        node.value = value;
        return ele;
    }

    @Override
    public T remove(int index) {
        rangeCheck(index);
        Node<T> node = getNode(index);
        return unlinkAndRemove(node);
    }

    @Override
    public boolean remove(T object) {
        Node<T> tempNode = head;
        for (int i = 0; i < size; i++) {
            T value = tempNode.value;
            if ((object == null && value == null) || (value != null && value.equals(object))) {
                unlinkAndRemove(tempNode);
                return true;
            }
            tempNode = tempNode.next;
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

    private static class Node<T> {
        private Node<T> previous;
        private T value;
        private Node<T> next;

        public Node(Node<T> previous, T value, Node<T> next) {
            this.previous = previous;
            this.value = value;
            this.next = next;
        }
    }

    private Node<T> getNode(int index) {
        int middle = size >> 1;
        if (index <= middle) {
            Node<T> headEl = head;
            for (int i = 0; i < index; i++) {
                headEl = headEl.next;
            }
            return headEl;
        }
        Node<T> tailEl = tail;
        for (int i = size - 1; i > index; i--) {
            tailEl = tailEl.previous;
        }
        return tailEl;
    }

    private void rangeCheck(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException(INDEX_OUT_OF_ARRAY_MESSAGE);
        }
    }

    private T unlinkAndRemove(Node<T> node) {
        if (head == tail) {
            head = null;
            tail = null;
            size--;
            return node.value;
        }
        if (node == head) {
            head = node.next;
            node.next.previous = null;
            T temp = node.value;
            node = null;
            size--;
            return temp;
        }
        if (node == tail) {
            tail = node.previous;
            node.previous.next = null;
            T temp = node.value;
            node = null;
            size--;
            return temp;
        }
        final Node<T> temp = node;
        node.previous.next = node.next;
        node.next.previous = node.previous;
        node = null;
        size--;
        return temp.value;
    }
}
