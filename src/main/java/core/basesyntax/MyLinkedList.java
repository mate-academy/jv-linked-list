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

        public Node(Node<T> prev, T value, Node next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (isEmpty()) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            Node<T> addedNode = new Node<>(tail, value, null);
            tail.next = addedNode;
            tail = addedNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        } else if (index == 0) {
            Node<T> addedNode = new Node<>(null, value, head);
            head.prev = addedNode;
            head = addedNode;
        } else {
            checkIndex(index);
            Node<T> targetNode = getTargetNode(index);
            Node<T> newNode = new Node<>(targetNode.prev, value, targetNode);
            targetNode.prev.next = newNode;
            targetNode.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getTargetNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> targetNode = getTargetNode(index);
        T valueToReturn = targetNode.value;
        targetNode.value = value;
        return valueToReturn;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> targetNode = getTargetNode(index);
        return unlink(targetNode);
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
        return size == 0;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index!");
        }
    }

    private Node<T> getTargetNode(int index) {
        Node<T> currentNode;
        if (index < size / 2) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int j = size - 1; j > index; j--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    private T unlink(Node<T> element) {
        if (element.prev == null) {
            head = element.next;
        } else if (element.next == null) {
            tail = element.prev;
            element.prev.next = null;
        } else {
            element.prev.next = element.next;
            element.next.prev = element.prev;
        }
        size--;
        return element.value;
    }
}
