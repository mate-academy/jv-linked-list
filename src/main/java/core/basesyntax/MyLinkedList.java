package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    public void linkLast(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    public void linkFirst(T value) {
        Node<T> newNode = new Node<>(null, value, head);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    public void linkByIndex(T value, int index) {
        indexCheck(index);

        if (index == 0) {
            linkFirst(value);
        } else if (index == size) {
            linkLast(value);
        } else {
            Node<T> nodeByIndex = findNodeByIndex(index);
            Node<T> newNode = new Node<>(nodeByIndex.prev, value, nodeByIndex);
            nodeByIndex.prev.next = newNode;
            nodeByIndex.prev = newNode;
            size++;
        }
    }

    public Node<T> findNodeByIndex(int index) {
        indexCheck(index);
        Node<T> currentNode;

        if (index < size >> 1) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    public void indexCheck(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    "indexCheckException: index: " + index
                    + " size: " + size);
        }
    }

    public void indexEqualsSizeCheck(int index) {
        if (index == size) {
            throw new IndexOutOfBoundsException("Can`t get element when index == size");
        }
    }

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        indexCheck(index);

        if (index == 0) {
            linkFirst(value);
            return;
        }

        if (index == size) {
            linkLast(value);
            return;
        }

        linkByIndex(value, index);
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        indexCheck(index);
        indexEqualsSizeCheck(index);

        Node<T> x = findNodeByIndex(index);
        T oldValue = x.value;
        return oldValue;
    }

    @Override
    public T set(T value, int index) {
        indexCheck(index);
        indexEqualsSizeCheck(index);
        Node<T> x = findNodeByIndex(index);
        T oldValue = x.value;
        x.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        indexCheck(index);
        indexEqualsSizeCheck(index);
        Node<T> x = findNodeByIndex(index);
        T oldValue = x.value;

        if (x.prev == null && x.next == null) {
            head = null;
            tail = null;
            size--;
            return oldValue;
        }

        if (x.prev == null) {
            head = x.next;
            head.prev = null;
            x.next = null;
            size--;
            return oldValue;
        }

        if (x.next == null) {
            tail = x.prev;
            tail.next = null;
            x.prev = null;
            size--;
            return oldValue;
        }

        x.prev.next = x.next;
        x.next.prev = x.prev;
        x.prev = null;
        x.next = null;
        size--;
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            if ((object == null && currentNode.value == null)
                    || (object != null && object.equals(currentNode.value))) {
                remove(i);
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node currentNode = head;
        sb.append("[");
        while (currentNode != null) {
            sb.append(currentNode.value)
                    .append(currentNode.next == null ? "" : ", ");
            currentNode = currentNode.next;
        }
        sb.append("]");
        return sb.toString();
    }
    static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

}
