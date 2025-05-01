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
            this.prev = prev;
            this.next = next;
            this.value = value;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value,null);
        if (this.isEmpty()) {
            head = newNode;
            tail = head;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bound: " + index);
        }
        Node<T> newNode = new Node<>(null, value, null);
        if (index == 0) {
            if (this.isEmpty()) {
                tail = head = newNode;
            } else {
                head.prev = newNode;
                newNode.next = head;
                head = newNode;
            }
        } else if (index == size) {
            newNode.prev = tail;
            newNode.prev.next = newNode;
            tail = newNode;
        } else {
            Node<T> currentNode = head;
            for (int i = 0; i < index - 1; i++) {
                currentNode = currentNode.next;
            }
            newNode.next = currentNode.next;
            if (currentNode.next != null) {
                currentNode.next.prev = newNode;
            }
            currentNode.next = newNode;
            newNode.prev = currentNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T listElement : list) {
            add(listElement);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bound: " + index);
        }
        if (this.isEmpty()) {
            return null;
        }

        if (index == 0) {
            return this.head.value;
        } else if (index == size - 1) {
            return this.tail.value;
        } else {
            Node<T> currentNode = this.head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode.value;
        }
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bound: " + index);
        }
        if (this.isEmpty()) {
            return null;
        }
        Node<T> currentNode = this.head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        T oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bound: " + index);
        }
        if (this.isEmpty()) {
            return null;
        }
        Node<T> currentNode = this.head;
        if (index == 0) {
            this.head = currentNode.next;
            if (this.head != null) {
                this.head.prev = null;
            }
        } else {
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            if (currentNode.next == null) {
                currentNode.prev.next = null;
                this.tail = currentNode.prev;
            } else {
                currentNode.next.prev = currentNode.prev;
            }
            if (currentNode.prev != null) {
                currentNode.prev.next = currentNode.next;
            }
        }
        size--;
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        if (this.isEmpty()) {
            return false;
        }
        boolean objectIsFound = false;
        Node<T> currentNode = this.head;
        for (int i = 0; i < size; i++) {
            if ((currentNode.value == null && object == null)
                    || (currentNode.value != null && currentNode.value.equals(object))) {
                objectIsFound = true;
                break;
            }
            currentNode = currentNode.next;
        }
        if (objectIsFound) {
            if (currentNode.next != null) {
                currentNode.next.prev = currentNode.prev;
            } else {
                this.tail = currentNode.prev;
            }
            if (currentNode.prev != null) {
                currentNode.prev.next = currentNode.next;
            } else {
                this.head = currentNode.next;
            }
            size--;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }
}
