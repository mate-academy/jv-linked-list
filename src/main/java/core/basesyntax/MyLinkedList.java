package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    private static class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> previous;

        public Node(T element) {
            this.element = element;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (first == null) {
            // Список порожній
            first = newNode;
            last = newNode;
        } else {
            last.next = newNode;
            newNode.previous = last;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bound ");
        }
        if (index == size) {
            add(value);
            return;
        }

        Node<T> newNode = new Node<>(value);
        if (index == 0) {
            newNode.next = first;
            first.previous = newNode;
            first = newNode;
        } else {
            Node<T> indexNode = first;
            for (int i = 0; i < index; i++) {
                indexNode = indexNode.next;
            }
            newNode.previous = indexNode.previous;
            newNode.next = indexNode;
            indexNode.previous.next = newNode;
            indexNode.previous = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T sample : list) {
            add(sample);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bound ");
        }
        Node<T> currentNode = first;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode.element;
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bound ");
        }
        Node<T> currentNode = first;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        T oldValue = currentNode.element;
        currentNode.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bound ");
        }
        Node<T> nodeToRemove;
        if (index == 0) {
            nodeToRemove = first;
            first = first.next;
            if (first != null) {
                first.previous = null;
            }
            if (first == null) {
                last = null;
            }
        } else if (index == size - 1) {
            nodeToRemove = last;
            last = last.previous;
            if (last != null) {
                last.next = null;
            }
            if (last == null) {
                first = null;
            }
        } else {
            nodeToRemove = first;
            for (int i = 0; i < index; i++) {
                nodeToRemove = nodeToRemove.next;
            }
            nodeToRemove.previous.next = nodeToRemove.next;
            nodeToRemove.next.previous = nodeToRemove.previous;
        }
        size--;
        return nodeToRemove.element;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        while (currentNode != null) {
            if (Objects.equals(currentNode.element, object)) {
                if (currentNode == first) {
                    first = first.next;
                    if (first != null) {
                        first.previous = null;
                    }
                } else if (currentNode == last) {
                    last = last.previous;
                    if (last != null) {
                        last.next = null;
                    }
                } else {
                    currentNode.previous.next = currentNode.next;
                    currentNode.next.previous = currentNode.previous;
                }
                size--;
                return true;
            }
            currentNode = currentNode.next;
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
}
