package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    private static class Node<T> {
        private Node<T> prev;
        private T item;
        private Node<T> next;

        public Node(Node<T> prevNode, T value, Node<T> nextNode) {
            prev = prevNode;
            item = value;
            next = nextNode;
        }
    }

    @Override
    public void add(T value) {
        Node<T> current = new Node<>(null, value, null);
        if (size == 0) {
            head = current;
        } else {
            current.prev = tail;
            tail.next = current;
        }
        tail = current;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Can't add node with invalid index");
        }
        if (index == size) {
            add(value);
        } else {
            Node<T> newNode = new Node<>(null, value, null);
            if (index == 0) {
                newNode.next = head;
                head.prev = newNode;
                head = newNode;
            } else {
                Node<T> current = getNodeAtIndex(index);
                newNode.next = current;
                newNode.prev = current.prev;
                current.prev.next = newNode;
                current.prev = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            Node<T> node = new Node<>(tail, value, null);
            tail.next = node;
            tail = node;
            size++;
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        if (index == 0) {
            return head.item;
        }
        int count = 0;
        Node<T> node = head;
        while (count != index) {
            node = node.next;
            count++;
        }
        return node.item;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Can't set value with invalid index");
        }
        int count = 0;
        Node<T> node = head;
        while (count != index) {
            node = node.next;
            count++;
        }
        T oldItem = node.item;
        node.item = value;
        return oldItem;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Can't remove node with invalid index");
        }
        if (index == 0) {
            final T item = head.item;
            head.prev = null;
            head = head.next;
            if (size == 1) {
                tail = null;
            }
            size--;
            return item;
        }
        Node<T> current = getNodeAtIndex(index);
        unlink(current);
        return current.item;
    }

    @Override
    public boolean remove(T object) {
        if (size == 0) {
            return false;
        }
        if (head.item == object) {
            head = head.next;
            if (head != null) {
                head.prev = null;
                tail = null;
            }
            size--;
            return true;
        }
        Node<T> current = head;
        boolean notNullEquals = false;
        while (!notNullEquals) {
            if (current.next == null) {
                return false;
            }
            current = current.next;
            notNullEquals = current.item == null ? object == null : current.item.equals(object);
        }
        unlink(current);
        return true;
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
        if (node.prev != null) {
            node.prev.next = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        }
        size--;
    }

    private Node<T> getNodeAtIndex(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }
}
