package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            Node<T> addNode = new Node<>(null, value, null);
            head = addNode;
            tail = addNode;
            size++;
        } else {
            Node<T> addNode = new Node<>(tail, value, null);
            tail.next = addNode;
            tail = addNode;
            size++;
        }
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == size) {
            add(value);
            return;
        }
        Node<T> addNode = new Node<>(null, value, null);
        Node<T> currentNode;
        if (index == 0) { //adding first element to LL
            currentNode = head;
            currentNode.prev = addNode;
            addNode.next = currentNode;
            size++;
            head = addNode;
            return;
        }
        if (index <= size / 2) { //from head to tail
            currentNode = head;
            for (int i = 1; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else { //from tail to head
            currentNode = tail;
            for (int i = size - 1; i >= index; i--) {
                currentNode = currentNode.prev;
            }
        }
        addNode.prev = currentNode;
        addNode.next = currentNode.next;
        if (addNode.next == null) {
            tail = addNode;
        } else {
            currentNode.next.prev = addNode;
        }
        currentNode.next = addNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T oneFromList:list) {
            add(oneFromList);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        T oldValue;
        Node<T> currentNode = getNode(index);
        oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T oldValue;
        Node<T> currentNode = head;
        if (index == 0 && size == 1) { //remove single element and make LL null
            oldValue = currentNode.value;
            size--;
            head = tail = null;
            return oldValue;
        }
        if (index == 0) {
            currentNode.next.prev = null;
            head = currentNode.next;
            oldValue = currentNode.value;
            size--;
            return oldValue;
        }
        currentNode = getNode(index);
        oldValue = currentNode.value;
        unlink(currentNode);
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        if (object == null) {
            while (object != currentNode.value) {
                currentNode = currentNode.next;
                if (currentNode == tail) {
                    return false;
                }
            }
        } else {
            while (!object.equals(currentNode.value)) {
                currentNode = currentNode.next;
                if (currentNode == tail) {
                    return false;
                }
            }
        }
        unlink(currentNode);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    private void checkIndex(int index) { //for methods Get, Set, Remove
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is less than 0 "
                    + "or bigger than LinkedList's size");
        }
    }

    private void checkIndexForAdd(int index) { //for method Add
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is less than 0 "
                    + "or bigger than LinkedList's size");
        }
    }

    private void unlink(Node<T> currentNode) {
        if (currentNode.prev == null) {
            head = currentNode.next;
        } else {
            currentNode.prev.next = currentNode.next;
        }
        if (currentNode.next == null) {
            tail = currentNode;
        } else {
            currentNode.next.prev = currentNode.prev;
        }
        size--;
    }

    private Node<T> getNode(int index) { //only for Get, Set, Remove methods
        Node<T> currentNode;
        if (index <= size / 2) { //from head to tail
            currentNode = head;
            for (int i = 1; i <= index; i++) {
                currentNode = currentNode.next;
            }
        } else { //from tail to head
            currentNode = tail;
            for (int i = size - 2; i >= index; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }
}
