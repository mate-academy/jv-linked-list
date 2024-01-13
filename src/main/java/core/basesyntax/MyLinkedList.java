package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public void add(T value) {
        linkHead(value);
    }

    @Override
    public void add(T value, int index) {
        Node<T> node = new Node<>(null, value, null);

        if (index == 0) {
            linkNodeAtHead(node);
        } else if (index == size()) {
            linkHead(value);
        } else {
            Node<T> current = getNodeAtIndexAddition(index);
            linkNodeMiddle(node, current);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        Node<T> current = getNodeAtIndex(index);
        return current.value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> current = getNodeAtIndex(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> current = getNodeAtIndex(index);
        baseRemove(current);
        return current.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if (object == null) {
                if (current.value == null) {
                    baseRemove(current);
                    return true;
                }
            } else if (object.equals(current.value)) {
                baseRemove(current);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    private Node<T> getNodeAtIndex(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        if (index < size() / 2) {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else {
            Node<T> current = tail;
            for (int i = size() - 1; i > index; i--) {
                current = current.prev;
            }
            return current;
        }
    }

    private Node<T> getNodeAtIndexAddition(int index) {
        checkIndex(index);

        if (index < size() / 2) {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else {
            Node<T> current = tail;
            for (int i = size() - 1; i > index; i--) {
                current = current.prev;
            }
            return current;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    private void baseRemove(Node<T> current) {
        if (current.prev != null) {
            current.prev.next = current.next;
        } else {
            head = current.next;
        }

        if (current.next != null) {
            current.next.prev = current.prev;
        } else {
            tail = current.prev;
        }
        size--;
    }

    private void linkHead(T value) {
        Node<T> node = new Node<>(null, value, null);
        if (head == null) {
            head = node;
        } else {
            tail.next = node;
            node.prev = tail;
        }
        tail = node;
        size++;
    }

    private void linkNodeAtHead(Node<T> node) {
        node.next = head;
        if (head != null) {
            head.prev = node;
        }
        head = node;
        if (tail == null) {
            tail = node;
        }
        size++;
    }

    private void linkNodeMiddle(Node<T> node, Node<T> current) {
        node.next = current;
        node.prev = current.prev;
        if (current.prev != null) {
            current.prev.next = node;
        }
        current.prev = node;
        size++;
    }

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
}
