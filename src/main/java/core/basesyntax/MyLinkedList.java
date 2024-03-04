
package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private static final String INFORMATION_FOR_EXCEPTION = "that index is out of bound";
    private int size;

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
       indexValidation(index);
        if (index == 0) {
            linkFirst(value);
        } else if (index == size) {
            addLast(value);
        } else {
            linkAtMiddle(value, index);
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
        indexValidation(index);
        return findByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = findByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeToRemove = findByIndex(index);
        unlink(nodeToRemove);
        size--;
        return nodeToRemove.value;
    }

    @Override
    public boolean remove(T value) {
        Node<T> current = head;
        while (current != null) {
            if (value == null ? current.value == null : value.equals(current.value)) {
                unlink(current);
                size--;
                return true;
            }
            current = current.next;
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
    private void indexValidation(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(INFORMATION_FOR_EXCEPTION + index);
        }
    }
    private void addLast(T value) {
        Node<T> newNode = newNode(value,null,tail);
        if (tail != null) {
            tail.next = newNode;
        }
        tail = newNode;
        if (head == null) {
            head = newNode;
        }
        size++;
    }

    private void linkFirst(T value) {
        Node<T> newNode = newNode(value,head,null);
        if (head != null) {
            head.previous = newNode;
        }
        head = newNode;
        if (tail == null) {
            tail = newNode;
        }
        size++;
    }

    private void linkAtMiddle(T value, int index) {
        Node<T> existingNode = findByIndex(index);
        Node<T> newNode = newNode(value,existingNode,existingNode.previous);
        existingNode.previous.next = newNode;
        existingNode.previous = newNode;
        size++;
    }

    private Node<T> findByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(INFORMATION_FOR_EXCEPTION + index);
        }
        if (index <= size / 2) {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else {
            Node<T> current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.previous;
            }
            return current;
        }
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
        node.next = null;
        node.previous = null;
    }

    private Node<T> newNode(T data, Node<T> next, Node<T> previous) {
        return new Node<>(data, next, previous);
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> previous;

        private Node(T data, Node<T> next, Node<T> previous) {
            this.value = data;
            this.next = next;
            this.previous = previous;
        }
    }
}
