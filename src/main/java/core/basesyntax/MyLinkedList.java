package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int linkedListSize;
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, null, value);
        if (tail != null) {
            tail.next = newNode;
        } else {
            head = newNode;
        }
        tail = newNode;
        linkedListSize++;
    }

    public void add(T value, int index) {
        if (index < 0 || index > linkedListSize) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }

        if (index == 0) {
            Node<T> newNode = new Node<>(null, head, value);
            if (head != null) {
                head.prev = newNode;
            } else {
                tail = newNode;
            }
            head = newNode;
        } else if (index == linkedListSize) {
            Node<T> newNode = new Node<>(tail, null, value);
            if (tail != null) {
                tail.next = newNode;
            } else {
                head = newNode;
            }
            tail = newNode;
        } else {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            Node<T> newNode = new Node<>(current.prev, current, value);
            current.prev.next = newNode;
            current.prev = newNode;
        }
        linkedListSize++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null) {
            throw new NullPointerException("List can't be null! {void addAll}");
        }
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= linkedListSize) {
            throw new IndexOutOfBoundsException("Index of bounds! {T get}");
        }
        if (index < (linkedListSize >> 1)) {
            Node<T> currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode.value;
        } else {
            Node<T> currentNode = tail;
            for (int i = linkedListSize - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
            return currentNode.value;
        }
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= linkedListSize) {
            throw new IndexOutOfBoundsException("Index of bounds! {T set}");
        }
        if (index < (linkedListSize >> 1)) {
            Node<T> currentNode = head;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
            T oldValue = currentNode.value;
            currentNode.value = value;
            return oldValue;
        } else {
            Node<T> currentNode = tail;
            for (int i = linkedListSize - 1; i > index; i--) {
                currentNode = currentNode.prev;
            }
            T oldValue = currentNode.value;
            currentNode.value = value;
            return oldValue;
        }
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= linkedListSize) {
            throw new IndexOutOfBoundsException("Index of bounds! {T remove}");
        }
        Node<T> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        if (currentNode.prev != null) {
            currentNode.prev.next = currentNode.next;
        } else {
            head = currentNode.next;
        }
        if (currentNode.next != null) {
            currentNode.next.prev = currentNode.prev;
        } else {
            tail = currentNode.prev;
        }
        linkedListSize--;
        return currentNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = head;
        while (currentNode != null) {
            if ((object == null && currentNode.value == null)
                    || (object != null && object.equals(currentNode.value))) {
                if (currentNode.prev != null) {
                    currentNode.prev.next = currentNode.next;
                } else {
                    head = currentNode.next;
                }
                if (currentNode.next != null) {
                    currentNode.next.prev = currentNode.prev;
                } else {
                    tail = currentNode.prev;
                }
                currentNode.prev = null;
                currentNode.next = null;
                currentNode.value = null;
                linkedListSize--;
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    @Override
    public int size() {
        return linkedListSize;
    }

    @Override
    public boolean isEmpty() {
        return linkedListSize == 0;
    }

    static class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T value;

        public Node(Node<T> prev, Node<T> next, T value) {
            this.prev = prev;
            this.next = next;
            this.value = value;
        }
    }
}
