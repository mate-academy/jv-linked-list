package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String INDEX_OUT_OF_BOUNDS_FORMAT =
            "Index = [%d] was out of bounds for size = [%d]";
    private static final int LAST_ELEMENT_INDEX_SHIFT = 1;
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> node = new Node<>(value, null, null);
        if (isEmpty()) {
            head = node;
        } else {
            tail.next = node;
            node.previous = tail;
        }
        tail = node;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            head = new Node<>(value, head, null);
            size++;
            return;
        }
        Node<T> nextNode = getNodeByIndex(index);
        Node<T> previousNode = nextNode.previous;
        Node<T> node = new Node<>(value, nextNode, previousNode);
        previousNode.next = node;
        nextNode.previous = node;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        list.forEach(this::add);
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNodeByIndex(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (index == 0) {
            T oldValue = head.value;
            head = head.next;
            size--;
            return oldValue;
        }
        if (index >= 0 && index == size - LAST_ELEMENT_INDEX_SHIFT) {
            T oldValue = tail.value;
            tail = tail.previous;
            size--;
            return oldValue;
        }
        Node<T> nodeToRemove = getNodeByIndex(index);
        nodeToRemove.previous.next = nodeToRemove.next;
        nodeToRemove.next.previous = nodeToRemove.previous;
        size--;
        return nodeToRemove.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> node = head;
        for (int i = 0; i < size; i++) {
            if (object != null && node.value == null) {
                node = node.next;
                continue;
            }
            if (object == node.value || node.value.equals(object)) {
                remove(i);
                return true;
            }
            node = node.next;
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

    private Node<T> getNodeByIndex(int index) {
        validateIndex(index);
        Node<T> node;
        if (index > size / 2) {
            node = tail;
            for (int i = size - LAST_ELEMENT_INDEX_SHIFT; i > index; i--) {
                node = node.previous;
            }
        } else {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        }
        return node;
    }

    private void validateIndex(int index) {
        if (index >= size || index < 0) {
            String format = String.format(INDEX_OUT_OF_BOUNDS_FORMAT, index, size);
            throw new IndexOutOfBoundsException(format);
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> previous;

        public Node(T value, Node<T> next, Node<T> previous) {
            this.value = value;
            this.next = next;
            this.previous = previous;
        }
    }
}
