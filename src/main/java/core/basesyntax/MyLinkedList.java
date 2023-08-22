package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkElementIndex(index);
        if (index == size) {
            add(value);
        } else if (index == 0) {
            linkFirst(value);
        } else {
            Node<T> source = node(index);
            Node<T> toAdd = new Node<>(source.previous, value, source);
            source.previous.next = toAdd;
            source.previous = toAdd;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        list.forEach(this::add);
    }

    @Override
    public T get(int index) {
        checkPositionIndex(index);
        return node(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkPositionIndex(index);
        Node<T> toSet = node(index);
        T oldValue = toSet.value;
        toSet.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkPositionIndex(index);
        Node<T> toRemove = node(index);
        unlink(toRemove);
        return toRemove.value;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> start = head; start != null; start = start.next) {
            if (object == start.value || object != null
                    && object.equals(start.value)) {
                unlink(start);
                return true;
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

    private void checkPositionIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(outOfBoundMessage(index));
        }
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(outOfBoundMessage(index));
        }
    }

    private Node<T> node(int index) {
        return index <= size() / 2 ? getNodeFromHead(index) : getNodeFromTail(index);
    }

    private Node<T> getNodeFromTail(int index) {
        Node<T> current = tail;
        for (int i = size - 1; i > index; i--) {
            current = current.previous;
        }
        return current;
    }

    private Node<T> getNodeFromHead(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private void unlink(Node<T> node) {
        if (node == head) {
            head = node.next;
        } else {
            node.previous.next = node.next;
        }
        if (node == tail) {
            tail = node.previous;
        } else {
            node.next.previous = node.previous;
        }
        size--;
    }

    private void linkFirst(T value) {
        Node<T> toAdd = new Node<>(null, value, head);
        head.previous = toAdd;
        head = toAdd;
        size++;
    }

    private String outOfBoundMessage(int index) {
        return String.format("Index %d out of bound for size %d", index, size);
    }

    class Node<T> {
        private Node<T> previous;
        private T value;
        private Node<T> next;

        public Node(Node<T> previous, T value, Node<T> next) {
            this.previous = previous;
            this.value = value;
            this.next = next;
        }
    }
}
