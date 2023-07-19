package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String INVALID_INDEX_EXCEPTION = "Invalid index: ";
    private int size;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        if (size == 0) {
            Node<T> node = createFirstNode(value);
            head = node;
            tail = node;
            size++;
        } else {
            addLast(value);
        }
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index);
        if (index == 0) {
            addFirst(value);
        } else if (index == size) {
            addLast(value);
        } else if (size / 2 >= index) {
            addFromHead(value, index);
        } else {
            addFromTail(value, index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndexGet(index);
        return size / 2 >= index ? getFromHead(index) : getFromTail(index);
    }

    @Override
    public T set(T value, int index) {
        checkIndexGet(index);
        return size / 2 >= index ? setFromHead(value, index) : setFromTail(value, index);
    }

    @Override
    public T remove(int index) {
        Node<T> current = findNodeByIndex(index);
        removeNode(current);
        return current.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = findNodeByValue(object);
        if (Objects.nonNull(current)) {
            removeNode(current);
            return true;
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

    private void removeNode(Node<T> current) {
        if (current == head) {
            head = current.next;
        } else {
            current.prev.next = current.next;
        }
        if (current == tail) {
            tail = current.prev;
        } else {
            current.next.prev = current.prev;
        }
        size--;
    }

    private Node<T> findNodeByValue(T value) {
        Node<T> current = head;
        while (current != null) {
            if (Objects.equals(current.item, value)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    private Node<T> findNodeByIndex(int index) {
        checkIndexGet(index);
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private T setFromHead(T value, int index) {
        Node<T> current = headIteration(index);
        T oldValue = current.item;
        current.item = value;
        return oldValue;
    }

    private T setFromTail(T value, int index) {
        Node<T> current = tailIteration(index);
        T oldValue = current.item;
        current.item = value;
        return oldValue;
    }

    private T getFromHead(int index) {
        Node<T> current = headIteration(index);
        return current.item;
    }

    private T getFromTail(int index) {
        Node<T> current = tailIteration(index);
        return current.item;
    }

    private Node<T> headIteration(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private Node<T> tailIteration(int index) {
        Node<T> current = tail;
        for (int i = size - 1; i > index; i--) {
            current = current.prev;
        }
        return current;
    }

    private void addFromHead(T value, int index) {
        Node<T> newNode = createFirstNode(value);
        Node<T> current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }
        newNode.next = current.next;
        newNode.prev = current;
        current.next.prev = newNode;
        current.next = newNode;
        size++;
    }

    private void addFromTail(T value, int index) {
        Node<T> newNode = createFirstNode(value);
        Node<T> current = tail;
        for (int i = size - 1; i > index; i--) {
            current = current.prev;
        }
        newNode.next = current;
        newNode.prev = current.prev;
        current.prev.next = newNode;
        current.prev = newNode;
        size++;
    }

    private void addLast(T value) {
        Node<T> node = new Node<>(tail, value, null);
        tail.next = node;
        tail = node;
        size++;
    }

    private void checkIndexGet(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(INVALID_INDEX_EXCEPTION + index);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(INVALID_INDEX_EXCEPTION + index);
        }
    }

    private void addFirst(T value) {
        if (size == 0) {
            Node<T> node = createFirstNode(value);
            head = node;
            tail = node;
            size++;
        } else {
            Node<T> node = new Node<>(null, value, head);
            head.prev = node;
            head = node;
            size++;
        }
    }

    private Node<T> createFirstNode(T value) {
        return new Node<>(null, value, null);
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        public Node(Node<E> prev, E element, Node<E> next) {
            this.prev = prev;
            this.item = element;
            this.next = next;
        }
    }
}
