package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private void linkLast(T value) {
        Node<T> currentTail = tail;
        Node<T> newTail = new Node<>(currentTail, value, null);
        tail = newTail;
        if (currentTail == null) {
            head = newTail;
        } else {
            currentTail.next = newTail;
        }
        size++;
    }

    private void linkBefore(T value, Node<T> currentNode) {
        Node<T> prevNode = currentNode.prev;
        Node<T> newNode = new Node<>(prevNode, value, currentNode);
        currentNode.prev = newNode;
        if (prevNode == null) {
            head = newNode;
        } else {
            prevNode.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value) {
        linkLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        if (index == size) {
            linkLast(value);
        } else {
            linkBefore(value, getNodeByIndex(index));
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
        checkElementIndex(index);
        if (index < size / 2) {
            Node<T> currentNode = head;
            T result = currentNode.value;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
                result = currentNode.value;
            }
            return result;
        } else {
            Node<T> currentNode = tail;
            T result = currentNode.value;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
                result = currentNode.value;
            }
            return result;
        }
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        if (index < size / 2) {
            Node<T> currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            T prevValue = currentNode.value;
            currentNode.value = value;
            return prevValue;
        } else {
            Node<T> currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
            T prevValue = currentNode.value;
            currentNode.value = value;
            return prevValue;
        }
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        return unlink(getNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        if (object == null) {
            while (currentNode != null) {
                if (currentNode.value == null) {
                    unlink(currentNode);
                    return true;
                }
                currentNode = currentNode.next;
            }
        } else {
            while (currentNode != null) {
                if (currentNode.value.equals(object)) {
                    unlink(currentNode);
                    return true;
                }
                currentNode = currentNode.next;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        if (head == null || tail == null) {
            return true;
        }
        return false;
    }

    private void checkElementIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
    }

    private Node<T> getNodeByIndex(int index) {
        checkElementIndex(index);
        if (index < size / 2) {
            Node<T> currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode;
        } else {
            Node<T> currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
            return currentNode;
        }
    }

    private T unlink(Node<T> node) {
        final T element = node.value;
        Node<T> nextNode = node.next;
        Node<T> prevNode = node.prev;

        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
            node.prev = null;
        }

        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prev = prevNode;
            node.next = null;
        }

        node.value = null;
        size--;
        return element;
    }
}
