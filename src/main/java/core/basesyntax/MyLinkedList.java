package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(tail, value, null);
        if (head == null) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else if (index == 0) {
            addToHead(value);
        } else {
            Node<T> previous = findNodeByIndex(index);
            if (previous != null) {
                insert(value, previous);
            }
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
        validateIndex(index, false);
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index, false);
        Node<T> foundNode = findNodeByIndex(index);
        T oldValue = foundNode.value;
        foundNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> deletedElement = findNodeByIndex(index);
        if (deletedElement == null) {
            throw new IndexOutOfBoundsException("wrong index: " + index + ", for empty list");
        }
        unlink(deletedElement);
        return deletedElement.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> deletedElement = findNodeByElement(object);
        boolean isDeleted = unlink(deletedElement);
        return isDeleted;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void insert(T value, Node<T> previous) {
        Node<T> newNode = new Node(previous.prev, value, previous);
        previous.prev.next = newNode;
        previous.prev = newNode;
        size++;
    }

    private void addToHead(T value) {
        Node<T> newNode = new Node<>(null, value, head);
        head.prev = newNode;
        head = newNode;
        size++;
    }

    private void addToTail(T value) {
        Node<T> newNode = new Node<>(tail, value,null);
        tail.next = newNode;
        tail = newNode;
        size++;
    }

    private void validateIndex(int index, boolean isForAdd) {
        if (isForAdd) {
            if (index > size || index < 0) {
                throw new IndexOutOfBoundsException("invalid index: " + index);
            }
        } else {
            if (index >= size || index < 0) {
                throw new IndexOutOfBoundsException("invalid index: " + index);
            }
        }
    }

    private Node<T> findNodeByIndex(int index) {
        validateIndex(index, true);
        if (index == 0) {
            return head;
        } else if (index == size - 1) {
            return tail;
        }
        if (index < size / 2) {
            return getNodeFromHead(index);
        } else {
            return getNodeFromTail(index);
        }
    }

    private Node<T> getNodeFromHead(int index) {
        Node<T> curNode = head;
        int counter = 0;
        for (int i = 0; curNode.next != null; i++) {
            if (i == index) {
                return curNode;
            }
            curNode = curNode.next;
        }
        return null;
    }

    private Node<T> getNodeFromTail(int index) {
        Node<T> curNode = tail;
        int counter = size - 1;
        while (curNode.prev != null) {
            if (counter == index) {
                return curNode;
            }
            curNode = curNode.prev;
            counter--;
        }
        return null;
    }

    private Node<T> findNodeByElement(T element) {
        Node<T> curNode = head;
        while (curNode != null) {
            if (areEqual(curNode.value, element)) {
                return curNode;
            }
            curNode = curNode.next;
        }
        return null;
    }

    private boolean areEqual(T a, T b) {
        return a == b || (a != null && a.equals(b));
    }

    private boolean unlink(Node<T> node) {
        if (node == head) {
            head = head.next;
            size--;
            return true;
        } else if (node == tail) {
            tail = tail.prev;
            size--;
            return true;
        } else if (node != null) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
            return true;
        }
        return false;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        private Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
