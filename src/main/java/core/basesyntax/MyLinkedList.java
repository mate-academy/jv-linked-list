package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (head == null) {
            head = new Node<>(null, value, null);
            tail = head;
        } else {
            tail.next = new Node<>(tail, value, null);
            tail = tail.next;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, index > size);
        if (index == size) {
            add(value);
        } else {
            Node<T> nodeByIndex = node(index);
            Node<T> prevNode = nodeByIndex.previous;
            Node<T> toAddNode = new Node<>(prevNode, value, nodeByIndex);
            nodeByIndex.previous = toAddNode;
            if (prevNode == null) {
                head = toAddNode;
            } else {
                prevNode.next = toAddNode;
            }
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index, index >= size);
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node.value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, index >= size);
        Node<T> nodeByIndex = node(index);
        T replacedValue = nodeByIndex.value;
        nodeByIndex.value = value;
        return replacedValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, index >= size);
        Node<T> nodeByIndex = node(index);
        Node<T> prevNode = nodeByIndex.previous;
        Node<T> nextNode = nodeByIndex.next;
        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
        }
        if (nextNode != null) {
            nextNode.previous = prevNode;
        }
        size--;
        return nodeByIndex.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> first = head;
        if (first == null) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = 0; i < size; i++) {
            if (first != null && (first.value == object || first.value.equals(object))) {
                Node<T> nextNode = first.next;
                Node<T> prevNode = first.previous;
                if (prevNode == null) {
                    if (nextNode != null) {
                        nextNode.previous = null;
                    }
                    head = nextNode;
                } else {
                    prevNode.next = nextNode;
                }
                if (nextNode != null) {
                    nextNode.previous = prevNode;
                }
                size--;
                return true;
            }
            first = first.next;
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

    private void checkIndex(int index, boolean b) {
        if (b || index < 0) {
            throw new IndexOutOfBoundsException("Don't have such index " + index);
        }
    }

    private Node<T> node(int index) {
        if (index < (size / 2)) {
            Node<T> first = this.head;
            for (int i = 0; i < index; i++) {
                first = first.next;
            }
            return first;
        } else {
            Node<T> last = tail;
            for (int i = size - 1; i > index; i--) {
                last = last.previous;
            }
            return last;
        }
    }

    private static class Node<T> {
        private Node<T> next;
        private Node<T> previous;
        private T value;

        public Node(Node<T> previous, T value, Node<T> next) {
            this.next = next;
            this.previous = previous;
            this.value = value;
        }
    }
}
