package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> oldLast = last;
        last = new Node<>(oldLast, value, null);
        if (oldLast == null) {
            first = last;
        } else {
            oldLast.next = last;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            Node<T> target = getNode(index);
            linkBefore(value, target);
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> indexNode = getNode(index);
        T returnNode = (T)indexNode.value;
        indexNode.value = value;

        return returnNode;
    }

    @Override
    public T remove(int index) {
        Node<T> returnNode = getNode(index);
        unlink(returnNode);

        return returnNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;

        for (int i = 0; i < size; i++) {
            if (currentNode.value != null && currentNode.value.equals(object)
                    || currentNode.value == object) {
                unlink(currentNode);
                return true;
            }
            currentNode = currentNode.next;
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

    private class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T value;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private void unlink(Node<T> node) {
        if (node.next == null && node.prev == null) {
            first = null;
            last = null;
            size--;
            return;
        }

        if (node.next == null) {
            node.prev.next = null;
            last = node.prev;
            size--;
            return;
        }

        if (node.prev == null) {
            node.next.prev = null;
            first = node.next;
            size--;
            return;
        }

        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("index "
                    + index + " is equals or out of size " + size);
        }
    }

    private void linkBefore(T value, Node<T> target) {
        Node<T> insertNode = new Node<>(target.prev, value, target);
        if (target.prev == null) {
            insertNode.next = target;
            first = insertNode;
            target.prev = first;
        } else {
            target.prev.next = insertNode;
            target.prev = insertNode;
        }
        size++;
    }

    private Node<T> getNode(int index) {
        checkIndex(index);
        Node<T> currentNode = first;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

}
