package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<T>(null, value, null);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        if (index >= 1 && index < size) {
            Node<T> currentBefore = getNodeByIndex(index - 1);
            Node<T> newNode = new Node<T>(currentBefore, value, currentBefore.next);
            currentBefore.next.prev = newNode;
            currentBefore.next = newNode;
        }
        if (index == 0 && size >= 1) {
            Node<T> newNode = new Node<T>(null, value, tail);
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
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
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = getNodeByIndex(index);
        Node<T> currentAfter = getNodeByIndex(index + 1);
        if (index == 0) {
            Node<T> newNode = new Node<T>(null, value, null);
            head = newNode;
            if (size == 1) {
                tail = newNode;
            } else {
                newNode.next = currentAfter;
                currentAfter.prev = newNode;
            }
        } else {
            Node<T> currentBefore = getNodeByIndex(index - 1);
            Node<T> newNode = new Node<T>(currentBefore, value, currentNode.next);
            currentBefore.next.prev = newNode;
            currentBefore.next = newNode;
        }
        return currentNode.value;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> currentNode = head;
        if (index == 0 && size == 1) {
            head = null;
            tail = null;
        }
        if (index == 0 && size > 1) {
            head = head.next;
            head.prev = null;
        } else if (size > 1) {
            currentNode = getNodeByIndex(index);
            Node<T> currentNodeBefore = getNodeByIndex(index - 1);
            currentNodeBefore.next.prev = currentNode.next;
            currentNodeBefore.next = currentNode.next;
        }
        size--;
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            if (get(i) == object || get(i) != null && get(i).equals(object)) {
                remove(i);
                return true;
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
        if (size == 0) {
            return true;
        }
        return false;
    }

    public Node<T> getNodeByIndex(int index) {
        if (index >= 0 || index < size()) {
            if (size / 2 >= index) {
                Node<T> currentNode;
                currentNode = head;
                int currentIndex = 0;
                while (currentNode != null && index != currentIndex && size >= 1) {
                    currentNode = currentNode.next;
                    currentIndex++;
                }
                return currentNode;
            }
            if (size / 2 < index) {
                Node<T> currentNode = tail;
                int currentIndex = size - 1;
                while (index != currentIndex) {
                    currentNode = currentNode.prev;
                    currentIndex--;
                }
                return currentNode;
            }
        }
        return null;
    }

    public void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("This index " + index + " is invalid");
        }
    }

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.next = next;
            this.value = value;
            this.prev = prev;
        }
    }
}
