package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private Node<T> currentBefore;
    private Node<T> currentAfter;
    private int size = 0;

    private static class Node<T> {
        private final T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    private void unlink(Node<T> removed) {
        currentBefore = removed.prev.prev;
        currentAfter = removed;
        currentBefore.next = currentAfter;
        currentAfter.prev = currentBefore;
    }

    private void addByIndexToTheAtMiddle(Node<T> newNode, int index, int size) {
        Node<T> temp = null;
        if (index < size / 2) {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            temp = current.prev;
            newNode.prev = current.prev;
            newNode.next = current;
            current.prev = newNode;
            temp.next = newNode;
        } else if (index >= size / 2) {
            Node<T> current = tail;
            for (int i = size; i > index; i--) {
                current = current.prev;
                temp = current.next;
            }
            newNode.prev = current;
            newNode.next = temp;
            current.next = newNode;
            temp.prev = newNode;
        }
    }

    private void addByIndexToTheHead(Node<T> newNode) {
        if (size == 0) {
            newNode.next = head;
            newNode.prev = null;
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            newNode.prev = null;
            head.prev = newNode;
            head = newNode;
        }
    }

    private void addByIndexToTheTail(Node<T> newNode) {
        newNode.next = null;
        newNode.prev = tail;
        tail.next = newNode;
        tail = newNode;
    }

    private void checkIndex(int size, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index wrong");
        }
    }

    private int addSizeIfIndexInTheMidle(int index, Node<T> newNode) {
        if (index > 0 && index < size) {
            addByIndexToTheAtMiddle(newNode, index, size);
            size++;
        }
        return size;
    }

    private T getNodeByIndex(int index, Node<T> current) {
        if (index > size - 1 || (index < 0)) {
            throw new IndexOutOfBoundsException("Index wrong");
        } else {
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        }
        return current.value;
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (size == 0) {
            tail = newNode;
            head = newNode;
        } else if (size > 0) {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode = new Node<>(null, value, null);
        checkIndex(size, index);
        addSizeIfIndexInTheMidle(index, newNode);
        if (index == 0) {
            addByIndexToTheHead(newNode);
            size++;
        }
        if (index == size) {
            addByIndexToTheTail(newNode);
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
        Node<T> current = head;
        return getNodeByIndex(index, current);
    }

    @Override
    public T set(T value, int index) {
        Node<T> setNode = new Node<>(null, value, null);
        Node<T> currentBefore;
        Node<T> currentAfter;
        Node<T> removed;
        Node<T> current = head;
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index wrong");
        }
        if (index == 0) {
            currentAfter = head.next;
            setNode.next = currentAfter;
            currentAfter.prev = setNode;
            setNode.prev = null;
            head = setNode;
        }
        if (index == 1) {
            removed = head.next;
            currentAfter = removed.next;
            setNode.prev = head;
            setNode.next = currentAfter;
            head.next = setNode;
            currentAfter.prev = setNode;
        } else {
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            removed = current;
            currentAfter = current.next;
            currentBefore = current.prev;
            setNode.next = currentAfter;
            setNode.prev = currentBefore;
        }
        return removed.value;
    }

    @Override
    public T remove(int index) {
        Node<T> removed;
        Node<T> current = head;
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index wrong");
        }
        if (index == 0) {
            if (size == 1) {
                head = null;
            } else {
                head = head.next;
                head.prev = null;
            }
        }
        if (index > 0 && index < size - 1) {
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            removed = current.next;
            unlink(removed);
        }
        if (index == size - 1 && index != 0) {
            current = tail.prev;
            current.next = null;
            tail = current;
        }
        size--;
        return current.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        Node<T> removed;
        int counter = 0;
        for (int i = 0; i < size; i++) {
            if (size == 1) {
                head = null;
                size--;
                counter++;
                break;
            }
            if (Objects.equals(object, head.value)) {
                head = head.next;
                head.prev = null;
                size--;
                counter++;
                break;
            }
            if (Objects.equals(object,current.value)) {
                removed = current.next;
                unlink(removed);
                size--;
                counter++;
                break;
            }
            current = current.next;
        }
        return (counter != 0);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }
}
