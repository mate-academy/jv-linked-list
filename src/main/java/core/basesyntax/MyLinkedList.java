package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    class Node<T> {

        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node node = new Node(null, value, null);
        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexExist(index);
        Node<T> currentNode = head;
        for (int i = 0; i <= index; i++) {
            if (i == index) {
                if (currentNode == null) {
                    add(value);
                } else {
                    if (currentNode.prev == null) {
                        Node<T> newNode = new Node<>(null, value, head);
                        head.prev = newNode;
                        head = newNode;
                    } else {
                        Node<T> newNode = new Node<>(currentNode.prev, value, currentNode);
                        currentNode.prev.next = newNode;
                        currentNode.prev = newNode;
                    }
                    size++;
                }
                break;
            }
            currentNode = currentNode.next;
        }
    }

    @Override
    public void addAll(List<T> list) {
        Node<T> currentNode = head;
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
            currentNode = currentNode.next;
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> currentNode = head;
        for (int i = 0; i <= index; i++) {
            if (i == index) {
                break;
            }
            currentNode = currentNode.next;
        }
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        T returnValue = null;
        checkIndexExist(index);
        Node<T> currentNode = head;
        for (int i = 0; i <= index; i++) {
            if (i == index) {
                returnValue = get(index);
                currentNode.value = value;
            }
            currentNode = currentNode.next;
        }
        return returnValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> currentNode = head;
        for (int i = 0; i <= index; i++) {
            if (i == index) {
                unlink(currentNode);
                break;
            }
            currentNode = currentNode.next;
        }
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        for (int i = 0; i <= size - 1; i++) {
            if (currentNode.value == object
                    || (currentNode.value != null && currentNode.value.equals(object))) {
                remove(i);
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Incorrect index: " + index);
        }
    }

    private void checkIndexExist(int index) {
        if (!(index >= 0 && index <= size)) {
            throw new IndexOutOfBoundsException("You can't use this index: " + index);
        }
    }

    private void unlink(Node node) {
        if (node.prev == null) {
            head = node.next;
        } else if (node.next == null) {
            node.prev.next = null;
            node.prev = null;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
        size--;
    }
}
