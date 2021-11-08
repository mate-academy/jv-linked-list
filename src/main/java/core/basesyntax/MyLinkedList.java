package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private Node<T> head;
    private Node<T> tail;
    private int size;

    class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        Node addNode1 = new Node<>(tail, value, null);
        if (size == ZERO) {
            head = addNode1;
        } else {
            tail.next = addNode1;
        }
        tail = addNode1;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < ZERO || index > size) {
            throw new IndexOutOfBoundsException("Index doesn't correct" + index);
        }
        if (index == size) {
            add(value);
            return;
        }
        if (index == ZERO) {
            Node addNode2 = new Node<>(null, value, head);
            head.prev = addNode2;
            head = addNode2;
        } else {
            Node<T> addNode3 = getNodeFromIndex(index);
            Node<T> addNode4 = new Node<>(addNode3.prev, value, addNode3);
            addNode3.prev.next = addNode4;
            addNode3.prev = addNode4;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T addAll : list) {
            add(addAll);
        }
    }

    @Override
    public T get(int index) {
        checkedIndex(index);
        return getNodeFromIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkedIndex(index);
        Node<T> setNode = getNodeFromIndex(index);
        T setNode2 = setNode.item;
        setNode.item = value;
        return setNode2;
    }

    @Override
    public T remove(int index) {
        checkedIndex(index);
        return unlink(getNodeFromIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> removeNode = head;
        for (int i = ZERO; i < size; i++) {
            if (removeNode.item == object || object != null && removeNode.item.equals(object)) {
                unlink(removeNode);
                return true;
            }
            removeNode = removeNode.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == ZERO;
    }

    private Node<T> getNodeFromIndex(int index) {
        checkedIndex(index);
        Node<T> getNode;
        if (index < size << ONE) {
            getNode = head;
            for (int i = ZERO; i < index; i++) {
                getNode = getNode.next;
            }
        } else {
            getNode = tail;
            for (int i = size - ONE; i > index; i--) {
                getNode = getNode.prev;
            }
        }
        return getNode;
    }

    private T unlink(Node<T> node) {
        Node<T> nextNode = node.next;
        Node<T> prevNode = node.prev;
        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
            node.prev = null;
        }
        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prev = prevNode;
            node.next = null;
        }
        size--;
        return node.item;
    }

    private void checkedIndex(int index) {
        if (index < ZERO || index >= size) {
            throw new IndexOutOfBoundsException("Index doesn't correct" + index);
        }
    }
}
