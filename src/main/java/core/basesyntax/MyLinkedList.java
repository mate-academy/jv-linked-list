package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String INDEX_EXCEPTION = "Index is incorrect";
    private Node<T> tail;
    private Node<T> head;
    private int size;

    @Override
    public void add(T value) {
        final Node<T> addNode = new Node<>(null, value, null);
        if (head == null) {
            head = addNode;
        }
        addNode.prev = tail;
        if (tail != null) {
            tail.next = addNode;
        }
        tail = addNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(INDEX_EXCEPTION);
        }
        Node<T> addNodeByIndex = findByIndex(index);
        if (index == size) {
            add(value);
            return;
        } else if (addNodeByIndex.prev == null) {
            head.prev = new Node<>(null, value, head);
            head = head.prev;
        } else {
            addNodeByIndex = new Node<>(addNodeByIndex.prev, value, addNodeByIndex);
            addNodeByIndex.prev.next = addNodeByIndex;
            addNodeByIndex.next.prev = addNodeByIndex;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkPositionIndex(index);
        return findByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkPositionIndex(index);
        Node<T> setNode = findByIndex(index);
        T oldValue = setNode.value;
        setNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkPositionIndex(index);
        Node<T> removeNode = findByIndex(index);
        T oldValue = removeNode.value;
        unlink(removeNode);
        return oldValue;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = new Node<>(null, object, head);
        for (int i = 0; i <= size; i++) {
            current = current.next;
            if (current != null) {
                if (current.value == object || object != null && object.equals(current.value)) {
                    remove(i);
                    return true;
                }
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

    private void checkPositionIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(INDEX_EXCEPTION);
        }
    }

    private Node<T> findByIndex(int index) {
        Node<T> node = new Node<>(tail, null, head);
        int half = size % 2 == 0 ? size >> 1 : size >> 1 + 1;
        if (index <= half) {
            for (int i = 0; i <= index; i++) {
                node = node.next;
            }
        } else {
            for (int i = 0; i <= size - index - 1; i++) {
                node = node.prev;
            }
        }
        return node;
    }

    private void unlink(Node node) {
        if (size > 1) {
            if (node.prev == null) {
                head = node.next;
            } else if (node.next == null) {
                tail = node.prev;
            } else {
                node.next.prev = node.prev;
                node.prev.next = node.next;
            }
        } else {
            head = null;
            tail = null;
        }
        size--;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }
}
