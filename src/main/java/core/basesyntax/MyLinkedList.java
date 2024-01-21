package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int count;

    @Override
    public void add(T value) {
        if (head == null) {
            createNod(null, value, null);
        } else {
            createNod(tail, value, null);
        }
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, 1);
        if (head == null) {
            createNod(null, value, null);
            return;
        }
        if (index == 0) {
            createNod(null, value, head);
            return;
        }
        Node<T> currentNode = head;
        for (int i = 1; i <= index; i++) {
            if (currentNode == tail) {
                createNod(currentNode, value, null);
                return;
            }
            currentNode = currentNode.next;
        }
        createNod(currentNode.prev, value, currentNode);
    }

    @Override
    public void addAll(List<T> list) {
        for (T valueInn : list) {
            if (head == null) {
                createNod(null, valueInn, null);
            } else {
                createNod(tail, valueInn, null);
            }
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index, 0);
        if (head == null) {
            return null;
        }
        return searchNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, 0);
        if (head != null) {
            Node<T> nodeToChange = searchNode(index);
            T oldValue = nodeToChange.value;
            nodeToChange.value = value;
            return oldValue;
        }
        return null;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, 0);
        if (head == null) {
            return null;
        }
        Node<T> cutNode = searchNode(index);
        unlink(cutNode);
        return cutNode.value;
    }

    @Override
    public boolean remove(T object) {
        if (head == null) {
            return false;
        }
        Node<T> currentNode = head;
        while (true) {
            if (currentNode.value == object
                    || currentNode.value != null && currentNode.value.equals(object)) {
                unlink(currentNode);
                return true;
            }
            if (currentNode == tail) {
                return false;
            }
            currentNode = currentNode.next;
        }
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    private void checkIndex(int index, int amendmentForAdd) throws IndexOutOfBoundsException {
        if (index < 0 || index >= count + amendmentForAdd) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void createNod(Node<T> prevInn, T value, Node<T> nextInn) {
        Node<T> newNode = new Node<>(prevInn, value, nextInn);
        if (prevInn == null) {
            head = newNode;
        } else {
            prevInn.next = newNode;
        }
        if (nextInn == null) {
            tail = newNode;
        } else {
            nextInn.prev = newNode;
        }
        count++;
    }

    private Node<T> searchNode(int index) throws IndexOutOfBoundsException {
        Node<T> currentNode;
        if (index < (count / 2)) {
            currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode;
        }
        currentNode = tail;
        for (int i = count - 1; i > index; i--) {
            currentNode = currentNode.prev;
        }
        return currentNode;
    }

    private void unlink(Node<T> cutNode) {
        count--;
        if (head == tail) {
            head = null;
            tail = null;
            return;
        }
        if (cutNode == head) {
            head = cutNode.next;
            head.prev = null;
            return;
        }
        if (cutNode == tail) {
            tail = cutNode.prev;
            tail.next = null;
            return;
        }
        cutNode.prev.next = cutNode.next;
        cutNode.next.prev = cutNode.prev;
    }

    static class Node<T> {
        private Node<T> next;
        private T value;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
