package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    private static class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (first == null) {
            first = last = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("U cant get element by this " + index);
        }
        Node<T> newNode = new Node<>(null, value, null);
        if (index == size) {
            add(value);
        } else if (index == 0) {
            newNode.next = first;
            first.prev = newNode;
            first = newNode;
            size++;
        } else {
            Node<T> nodeByIndex = findNodeByIndex(index);
            newNode.prev = nodeByIndex.prev;
            newNode.next = nodeByIndex;
            nodeByIndex.prev.next = newNode;
            nodeByIndex.prev = newNode;
            size++;
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
        checkIndex(index);
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> nodeByIndex = findNodeByIndex(index);
        T valueBefore = nodeByIndex.value;
        nodeByIndex.value = value;
        return valueBefore;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> remove = findNodeByIndex(index);
        unlink(remove);
        size--;
        return remove.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> find = first;
        for (int i = 0; i < size; i++) {
            if (find.value == object || object != null && object.equals(find.value)) {
                unlink(find);
                size--;
                return true;
            }
            find = find.next;
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("U cant get element by this " + index);
        }
    }

    private void unlink(Node<T> node) {
        Node<T> nextNode = node.next;
        Node<T> prevNode = node.prev;
        if (size == 1) {
            first = last = null;
        } else if (nextNode == null) {
            last.prev.next = null;
            last = last.prev;
        } else if (prevNode == null) {
            first.next.prev = null;
            first = first.next;
        } else {
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
        }
    }

    private Node<T> findNodeByIndex(int index) {
        if (index < (size >> 1)) {
            Node<T> head = first;
            for (int i = 0; i < index; i++) {
                head = head.next;
            }
            return head;
        } else {
            Node<T> tail = last;
            for (int i = size - 1; i > index; i--) {
                tail = tail.prev;
            }
            return tail;
        }
    }
}
