package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int count;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (tail != null) {
            tail.next = newNode;
        }
        tail = newNode;
        if (head == null) {
            head = newNode;
        }
        count++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexToAdd(index);
        if (index == size()) {
            add(value);
        } else if (index == 0) {
            addToHead(value);
        } else {
            Node<T> source = getNodeByIndex(index);
            Node<T> toAdd = new Node<>(source.previous, value, source);
            source.previous.next = toAdd;
            source.previous = toAdd;
            count++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        list.forEach(this::add);
    }

    @Override
    public T get(int index) {
        checkIndexToGet(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndexToGet(index);
        Node<T> toSet = getNodeByIndex(index);
        T oldValue = toSet.value;
        toSet.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexToGet(index);
        Node<T> toRemove = getNodeByIndex(index);
        unlink(toRemove);
        return (T) toRemove.value;
    }

    @Override
    public boolean remove(T object) {
        return unlink(getNodeByValue(object));
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    private void checkIndexToGet(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    private void checkIndexToAdd(int index) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    private Node<T> getNodeByIndex(int index) {
        return index <= size() / 2 ? getNodeFromHead(index) :
                getNodeFromTail(index);
    }

    private Node<T> getNodeFromTail(int index) {
        int backCounter = size();
        Node<T> current = tail;
        while (--backCounter > index) {
            current = current.previous;
        }
        return current;
    }

    private Node<T> getNodeFromHead(int index) {
        int counter = 0;
        Node<T> current = head;
        while (counter++ < index) {
            current = current.next;
        }
        return current;
    }

    private Node<T> getNodeByValue(T value) {
        Node<T> current = head;
        while (current != null) {
            if (current.value == value || current.value != null
                    && current.value.equals(value)) {
                break;
            }
            current = current.next;
        }
        return current;
    }

    private boolean unlink(Node<T> node) {
        if (node == null) {
            return false;
        }
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
        count--;
        return true;

    }

    private void addToHead(T value) {
        Node<T> toAdd = new Node<>(null, value, head);
        head.previous = toAdd;
        head = toAdd;
        count++;
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
