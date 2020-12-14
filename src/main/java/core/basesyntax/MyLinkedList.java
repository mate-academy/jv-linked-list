package core.basesyntax;

import java.util.List;
import java.util.NoSuchElementException;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public boolean add(T value) {
        Node<T> node = new Node<T>(tail, value, null);
        if (isEmpty()) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (!((index >= 0 && index < size) || index == size)) {
            throw new IndexOutOfBoundsException("Failed add to LinkedList. Please check index");
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> currentNode = findByIndex(index);
        Node<T> newNode = new Node<T>(currentNode.previous, value, currentNode);
        if (index == 0) {
            head = newNode;
        } else {
            currentNode.previous.next = newNode;
        }
        currentNode.previous = newNode;
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return findByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = findByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> node = findByIndex(index);
        remove(node);
        return node.value;
    }

    @Override
    public boolean remove(T object) {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("Cannot delete from empty list");
        }
        try {
            Node<T> node = findByValue(object);
            remove(node);
        } catch (NoSuchElementException e) {
            return false;
        }
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

    private class Node<T> {
        private Node<T> previous;
        private T value;
        private Node<T> next;

        public Node(Node<T> previous, T value, Node<T> next) {
            this.previous = previous;
            this.value = value;
            this.next = next;
        }
    }

    private Node<T> findByIndex(int index) {
        Node<T> node;
        if(index <= size / 2) {
            node = head;
            int listIndex = 0;
            while (listIndex != index) {
                node = node.next;
                listIndex++;
            }
        } else {
            node = tail;
            int listIndex = size - 1;
            while (listIndex != index) {
                node = node.previous;
                listIndex--;
            }
        }
        return node;
    }

    private boolean checkIndex(int index) {
        if (index >= 0 && index < size) {
            return true;
        }
        throw new IndexOutOfBoundsException("Please check index");
    }

    private Node<T> findByValue(T value) {
        Node<T> node = head;
        while (node != null) {
            if (node.value == value || (node.value != null && node.value.equals(value))) {
                return node;
            }
            node = node.next;
        }
        throw new NoSuchElementException("No such element in list");
    }

    private void remove(Node<T> node) {
        if (node.previous == null) {
            head = node.next;
        } else {
            node.previous.next = node.next;
        }
        if (node.next == null) {
            tail = node.previous;
        } else {
            node.next.previous = node.previous;
        }
        size--;
    }
}
