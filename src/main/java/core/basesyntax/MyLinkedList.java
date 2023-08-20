package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(T value) {
            this.value = value;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (isIndexLessThenZero(index)) {
            throw new IndexOutOfBoundsException("Index can't be less then zero");
        } else if (isIndexGreaterThanSize(index)) {
            throw new IndexOutOfBoundsException("Index can't be greater then size");
        } else {
            Node<T> newNode = new Node<>(value);
            if (head == null) {
                head = tail = newNode;
            } else if (index == 0) {
                newNode.next = head;
                head = newNode;
            } else if (index == size) {
                tail.next = newNode;
                tail = newNode;
            } else {
                Node<T> node = getNodeByIndex(index - 1);
                newNode.next = node.next;
                node.next = newNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            Node<T> newNode = new Node<>(value);
            if (head == null) {
                head = tail = newNode;
            } else {
                tail.next = newNode;
                newNode.prev = tail;
                tail = newNode;
            }
            size++;
        }
    }

    @Override
    public T get(int index) {
        if (isIndexLessThenZero(index)) {
            throw new IndexOutOfBoundsException("Index can't be less then zero");
        } else if (isIndexGreaterThanOrEqualsToSize(index)) {
            throw new IndexOutOfBoundsException("Index can't be greater then or equal to size");
        } else {
            return getNodeByIndex(index).value;
        }
    }

    @Override
    public T set(T value, int index) {
        if (isIndexLessThenZero(index)) {
            throw new IndexOutOfBoundsException("Index can't be less then zero");
        } else if (isIndexGreaterThanOrEqualsToSize(index)) {
            throw new IndexOutOfBoundsException("Index can't be greater then or equal to size");
        } else {
            T oldElement = getNodeByIndex(index).value;
            Node<T> node = getNodeByIndex(index);
            node.value = value;
            return oldElement;
        }
    }

    @Override
    public T remove(int index) {
        T deletedEllement;
        if (isIndexLessThenZero(index)) {
            throw new IndexOutOfBoundsException("Index can't be less then zero");
        } else if (isIndexGreaterThanOrEqualsToSize(index)) {
            throw new IndexOutOfBoundsException("Index can't be greater then or equal to size");
        } else if (index == 0) {
            deletedEllement = head.value;
            head = head.next;
            if (head == null) {
                tail = null;
            }
        } else {
            Node<T> prev = getNodeByIndex(index - 1);
            deletedEllement = prev.next.value;
            prev.next = prev.next.next;
            if (index == size - 1) {
                tail = prev;
            }
        }
        size--;
        return deletedEllement;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (isEqual(current.value, object)) {
                index = i;
                break;
            }
            current = current.next;
        }
        if (index == -1) {
            return false;
        } else {
            remove(index);
            return true;
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

    private Node<T> getNodeByIndex(int index) {
        if (isIndexLessThenZero(index)) {
            throw new IndexOutOfBoundsException("Index can't be less then zero");
        } else if (isIndexGreaterThanOrEqualsToSize(index)) {
            throw new IndexOutOfBoundsException("Index can't be greater then or equal to size");
        } else {
            Node<T> temp = head;
            for (int i = 0; i < index; i++) {
                temp = temp.next;
                if (temp == null) {
                    throw new IndexOutOfBoundsException("Index is out of bounds");
                }
            }
            return temp;
        }
    }

    private boolean isIndexLessThenZero(int index) {
        return index < 0;
    }

    private boolean isIndexGreaterThanSize(int index) {
        return index > size;
    }

    private boolean isIndexGreaterThanOrEqualsToSize(int index) {
        return index >= size;
    }

    private boolean isEqual(T value1, T value2) {
        if (value1 == null) {
            return value2 == null;
        }
        return value1.equals(value2);
    }
}
