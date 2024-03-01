package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> tail;
    private Node<T> head;
    private int size;

    @Override
    public void add(T value) {
        if (head == null) {
            head = tail = new Node<>(null, value, null);
        } else {
            tail = tail.next = new Node<>(tail, value, null);
        }
        size++;
    }

    @Override
    public  void add(T value, int index) {
        checkIndexInBound(index);
        if (head == null) {
            head = tail = new Node<>(null, value, null);
        } else if (index == 0) {
            head = new Node<>(null, value, head);
        } else if (index == size) {
            tail = tail.next = new Node<>(tail, value, null);
        } else {
            Node<T> currentNode = getNode(index - 1);
            Node<T> node = new Node<>(currentNode, value, currentNode.next);
            currentNode.next.prev = node;
            currentNode.next = node;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkSetIndexInBound(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkSetIndexInBound(index);
        Node<T> nodeToSet;
        if (index == 0) {
            nodeToSet = head;
        } else {
            nodeToSet = getNode(index - 1).next;
        }
        T returnValue = nodeToSet.value;
        nodeToSet.value = value;
        return returnValue;
    }

    @Override
    public T remove(int index) {
        checkSetIndexInBound(index);
        T removedElement;
        if (index == 0) {
            removedElement = head.value;
            head = head.next;
            if (head == null) {
                tail = null;
            } else {
                head.prev = null;
            }
        } else {
            Node<T> previousNode = getNode(index);
            removedElement = previousNode.value;
            if (previousNode != head && previousNode != tail) {
                previousNode.prev.next = previousNode.next;
                previousNode.next.prev = previousNode.prev;
            } else if (previousNode == tail) {
                previousNode.prev.next = null;
                tail = previousNode.prev;
            }
            if (index == size - 1) {
                tail = previousNode;
            }
        }
        size--;
        return removedElement;
    }

    @Override
    public boolean remove(T object) {
        return removeByValue(object);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    private Node<T> getNode(int index) {
        Node<T> current;
        if (index < size / 2 + 1) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i >= index + 1; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private boolean removeByValue(T value) {
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (areValuesEqual(value, current)) {
                remove(i);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    private boolean areValuesEqual(T value, Node<T> current) {
        return (current.value != null && current.value.equals(value))
                || (current.value == null && value == null);
    }

    private void checkIndexInBound(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(
                    "No such index exist:" + index + " in size:" + size);
        }
    }

    private void checkSetIndexInBound(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(
                    "No such index exist:" + index + " in size:" + size);
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
