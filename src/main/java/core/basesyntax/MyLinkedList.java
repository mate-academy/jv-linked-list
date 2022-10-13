package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int VALUE_NOT_FOUND = -1;
    private int size;
    private Node<T> head;
    private Node<T> tail;

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    public MyLinkedList() {
        size = 0;
        head = null;
        tail = null;
    }

    private void addFirst(T value) {
        Node<T> first = head;
        Node<T> newNode = new Node<>(null, value, first);
        head = newNode;
        if (size == 0) {
            tail = newNode;
        } else {
            first.prev = newNode;
        }
        size++;
    }

    private void addLast(T value) {
        Node<T> last = tail;
        Node<T> newNode = new Node<>(last, value, null);
        tail = newNode;
        if (size == 0) {
            head = newNode;
        } else {
            last.next = newNode;
        }
        size++;
    }

    private void insert(T value, Node<T> afterNewNode) {
        Node<T> beforeNewNode = afterNewNode.prev;
        Node<T> newNode = new Node<>(beforeNewNode, value, afterNewNode);
        beforeNewNode.next = newNode;
        afterNewNode.prev = newNode;
        size++;
    }

    private Node<T> getObjByIndex(int index) {
        Node<T> current = head;
        if (index < size / 2) {
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private int getIndex(T value) {
        Node<T> current = head;
        for (int i = 0; current != null; i++) {
            if (current.value == value || value != null && value.equals(current.value)) {
                return i;
            }
            current = current.next;
        }
        return VALUE_NOT_FOUND;
    }

    @Override
    public void add(T value) {
        addLast(value);
    }

    @Override
    public void add(T value, int index) {
        checkPositionIndex(index);
        if (index == 0) {
            addFirst(value);
        } else if (index == size) {
            addLast(value);
        } else {
            Node<T> currentAtIndex = getObjByIndex(index);
            insert(value, currentAtIndex);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            addLast(item);
        }
    }

    @Override
    public T get(int index) {
        checkElementIndex(index);
        return getObjByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkElementIndex(index);
        Node<T> current = getObjByIndex(index);
        T oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    private T unlink(Node<T> node) {
        Node<T> beforeNode = node.prev;
        Node<T> afterNode = node.next;
        if (beforeNode == null) {
            head = afterNode;
        } else {
            beforeNode.next = afterNode;
            node.prev = null;
        }

        if (afterNode == null) {
            tail = beforeNode;
        } else {
            afterNode.prev = beforeNode;
            node.next = null;
        }
        return node.value;
    }

    @Override
    public T remove(int index) {
        checkElementIndex(index);
        Node<T> current = getObjByIndex(index);
        size--;
        return unlink(current);
    }

    @Override
    public boolean remove(T object) {
        int index = getIndex(object);
        if (isElementIndex(index)) {
            remove(index);
            return true;
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

    private boolean isElementIndex(int index) {
        return (index >= 0 && index < size);
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }
}
