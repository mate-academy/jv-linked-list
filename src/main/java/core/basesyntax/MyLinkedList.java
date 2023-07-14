package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (isEmpty()) {
            addElementInEmptyList(value);
        } else {
            addElementOnLastPosition(value);
        }
    }

    @Override
    public void add(T value, int index) {
        if (index != size) {
            checkIndex(index);
        }
        if (isEmpty()) {
            addElementInEmptyList(value);
        } else if (index == size) {
            addElementOnLastPosition(value);
        } else if (index == 0) {
            addElementOnFirstPosition(value);
        } else {
            addElementInTheMiddle(value, index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            addElementOnLastPosition(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> node = getNodeByIndex(index);
        T replacedElementValue = node.element;
        node.element = value;
        return replacedElementValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> nodeToRemove = getNodeByIndex(index);
        unlink(nodeToRemove);
        return nodeToRemove.element;
    }

    @Override
    public boolean remove(T value) {
        Node<T> nodeToRemove = getNodeByValue(value);
        if (nodeToRemove != null) {
            unlink(nodeToRemove);
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
        return head == null;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Can't add by index" + index);
        }
    }

    private void addElementInEmptyList(T value) {
        Node newNode = new Node<>(null, value, null);
        head = newNode;
        tail = newNode;
        size++;
    }

    private void addElementOnFirstPosition(T value) {
        Node<T> node = new Node<>(null, value, head);
        head.previous = node;
        head = node;
        size++;
    }

    private void addElementInTheMiddle(T value, int index) {
        Node<T> prev = getNodeByIndex(index - 1);
        Node node = new Node<>(prev, value, prev.next);
        prev.next.previous = node;
        prev.next = node;
        size++;
    }

    private void addElementOnLastPosition(T value) {
        Node node = new Node<>(tail, value, null);
        tail.next = node;
        tail = node;
        size++;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.previous;
            }
        }
        return current;
    }

    private Node<T> getNodeByValue(T value) {
        Node<T> current = head;
        while (current != null) {
            if (isEqual(current.element, value)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    private boolean isEqual(T first, T second) {
        return first != null && first.equals(second) || first == second;
    }

    private boolean isEqual(Node<T> first, Node<T> second) {
        return first != null && first.equals(second) || first == second;
    }

    private void unlink(Node<T> node) {
        if (isEqual(node, head)) {
            head = head.next;
            if (head != null) {
                head.previous = null;
            }
        } else if (isEqual(node, tail)) {
            tail = tail.previous;
            tail.next = null;
        } else {
            node.previous.next = node.next;
            node.next.previous = node.previous;
        }
        size--;
    }

    private class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> previous;

        public Node(Node<T> previous, T element, Node<T> next) {
            this.element = element;
            this.next = next;
            this.previous = previous;
        }
    }
}
