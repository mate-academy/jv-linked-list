package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        if (head == null) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            Node<T> newNode = new Node<>(tail, value, null);
            tail.next = newNode;
            tail = newNode;
        }
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        if (index == 0) {
            Node<T> newNode = new Node<>(null, value, head);
            head = newNode;
            if (tail == null) {
                tail = newNode;
            }
        } else {
            Node<T> prevNode = getNodeByIndex(index - 1);
            Node<T> newNode = new Node<>(prevNode, value, prevNode.next);

            prevNode.next = newNode;
            if (newNode.next != null) {
                newNode.next.prev = newNode;
            } else {
                tail = newNode;
            }
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        Node<T> current = getNodeByIndex(index);
        return current.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> toSet = node(index);
        T oldValue = toSet.value;
        toSet.value = value;
        return oldValue;
        Node<T> current = head;
        int count = 0;
        while (count < index) {
            current = current.next;
            count++;
        }
        T oldValue = current.item;
        Node<T> newNode;
        if (index == 0) {
            newNode = new Node<>(null, value, head.next);
            if (head != null) {
                head.prev = newNode;
            }
            head = newNode;

            if (tail == null) {
                tail = newNode;
            }
        } else {
            newNode = new Node<>(current.prev, value, current.next);
            current.prev.next = newNode;
            current.next.prev = newNode;
        }
        current.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> toRemove = node(index);
        unlink(toRemove);
        return toRemove.value;
        Node<T> current = head;
        int count = 0;
        while (count < index) {
            current = current.next;
            count++;
        }
        if (current.prev == null) {
            head = head.next;
            if (head != null) {
                head.prev = null;
            }
        } else {
            current.prev.next = current.next;
            if (current.next != null) {
                current.next.prev = current.prev;
            } else {
                tail = current.prev;
            }
        }
        return current.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if (object != null && object.equals(current.item) || (object == current.item)) {
                removeNodeByValue(current);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        int size = 0;
        Node<T> current = head;
        while (current != null) {
            size++;
            current = current.next;
        }
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);

        Node<T> current;
        if (index <= size() / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size() - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private void removeNodeByValue(Node<T> node) {
        if (node.prev == null) {
            head = head.next;
            if (head != null) {
                head.prev = null;
            }
        } else {
            node.prev.next = node.next;
            if (node.next != null) {
                node.next.prev = node.prev;
            } else {
                tail = node.prev;
            }
        }
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        public Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
