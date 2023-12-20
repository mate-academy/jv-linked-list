package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> head;
    private Node<T> tail;

    private class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> prev;

        public Node(T element) {
            this.element = element;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        Objects.checkIndex(index, size + 1);
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = tail = newNode;
        } else if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else if (index == size) {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        } else {
            Node<T> currentNode = getElementByIndex(index - 1);
            newNode.next = currentNode.next;
            currentNode.next.prev = newNode;
            currentNode.next = newNode;
            newNode.prev = currentNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            tail.next = new Node<>(list.get(i));
            tail = tail.next;
            size++;
        }
    }

    @Override
    public T get(int index) {
        Objects.checkIndex(index, size);
        return getElementByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        Objects.checkIndex(index, size);
        Node<T> setElement = getElementByIndex(index);
        T returnedElement = setElement.element;
        setElement.element = value;
        return returnedElement;
    }

    @Override
    public T remove(int index) {
        Objects.checkIndex(index, size);
        T returnElement;
        if (index == 0) {
            returnElement = head.element;
            head = head.next;
            if (head == null) {
                tail = null;
            }
        } else {
            if (index == size) {
                returnElement = tail.element;
                tail = tail.prev;
            } else {
                Node<T> removeNode = getElementByIndex(index - 1);
                returnElement = removeNode.next.element;
                removeNode.next = removeNode.next.next;
            }
        }
        size--;
        return returnElement;
    }

    @Override
    public boolean remove(T object) {
        Node<T> removeElement = head;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(object, removeElement.element)) {
                if (i == 0) {
                    head = head.next;
                    size--;
                    return true;
                }
                removeElement.prev.next = removeElement.next;
                removeElement.next.prev = removeElement.prev;
                size--;
                return true;
            }
            removeElement = removeElement.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    private Node<T> getElementByIndex(int index) {
        Objects.checkIndex(index, size);
        Node<T> node;
        node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }
}
