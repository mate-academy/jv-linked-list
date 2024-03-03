
package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String INDEX_FOR_EXCEPTION = "Index: ";
    private static final String SIZE_FOR_EXCEPTION = ", Size: ";
    private static final int NUMBER_TO_SUBTRACT = 1;
    private int size;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        private T value;
        private Node<T> next;

        Node(T value) {
            this.value = value;
        }
    }

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(INDEX_FOR_EXCEPTION + index + SIZE_FOR_EXCEPTION + size);
        }
        Node<T> newNode = new Node<>(value);
        if (index == 0) {
            addFirst(newNode);
        } else if (index == size) {
            addLast(newNode);
        } else {
            Node<T> previousNode = getNode(index - NUMBER_TO_SUBTRACT);
            newNode.next = previousNode.next;
            previousNode.next = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        validateIndexForGet(index);
        Node<T> node = getNode(index);
        return node.value;
    }

    @Override
    public T set(T value, int index) {
        validateIndexForGet(index);
        Node<T> node = getNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        validateIndexForGet(index);
        Node<T> removedNode;
        if (index == 0) {
            removedNode = head;
            head = head.next;
            if (head == null) {
                tail = null;
            }
        } else {
            Node<T> previousNode = getNode(index - NUMBER_TO_SUBTRACT);
            removedNode = previousNode.next;
            previousNode.next = removedNode.next;
            if (index == size - NUMBER_TO_SUBTRACT) {
                tail = previousNode;
            }
        }
        size--;
        return removedNode.value;
    }

    @Override
    public boolean remove(T value) {
        Node<T> current = head;
        Node<T> previous = null;
        while (current != null) {
            if ((value == null && current.value == null)
                    || (value != null && value.equals(current.value))) {
                unlink(previous, current);
                size--;
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false;
    }

    private void unlink(Node<T> previous, Node<T> current) {
        if (previous == null) {
            head = current.next;
            if (head == null) {
                tail = null;
            }
        } else {
            previous.next = current.next;
            if (current.next == null) {
                tail = previous;
            }
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void addFirst(Node<T> newNode) {
        newNode.next = head;
        head = newNode;
        if (tail == null) {
            tail = newNode;
        }
        size++;
    }

    private void addLast(Node<T> newNode) {
        if (tail == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    private Node<T> getNode(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private void validateIndexForGet(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(INDEX_FOR_EXCEPTION
                    + index + SIZE_FOR_EXCEPTION + size);
        }
    }
}
