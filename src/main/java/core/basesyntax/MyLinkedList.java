package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private MyLinkedList.Node<T> first;
    private MyLinkedList.Node<T> last;

    public MyLinkedList() {
        this.size = 0;
        first = null;
        last = null;
    }

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

    @Override
    public void add(T value) {
        if (size == 0) {
            addFirst(value);
        } else {
            addLast(value);
        }
    }

    @Override
    public void add(T value, int index) {
        if (!(index >= 0 && index <= size)) {
            throw new IndexOutOfBoundsException("Wrong index value");
        }
        if (size == 0) { // item is actually the very first in the list
            addFirst(value);
        } else if (index == size) { // item is adding at the last position, next by this.last
            addLast(value);
        } else {
            MyLinkedList.Node positionNode = getPositionByIndex(index);
            addBefore(value, positionNode);
        }
    }

    private Node getPositionByIndex(int index) {
        if (index <= size / 2) {
            return lookFromFirst(index);
        } else {
            return lookFromLast(index);
        }
    }

    private Node lookFromFirst(int index) {
        MyLinkedList.Node currentNode = this.first;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                break;
            }
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    private Node lookFromLast(int index) {
        MyLinkedList.Node currentNode = this.last;
        for (int i = size - 1; i >= 0; i--) {
            if (i == index) {
                break;
            }
            currentNode = currentNode.prev;
        }
        return currentNode;
    }

    @Override
    public void addAll(List<T> list) {
        for (T item: list) {
            add(item);
        }
    }

    private void addFirst(T value) {
        MyLinkedList.Node<T> newNode = new MyLinkedList.Node<>(null, value, null);
        this.first = this.last = newNode;
        ++size;
    }

    private void addLast(T value) {
        MyLinkedList.Node<T> newNode = new MyLinkedList.Node<>(this.last, value, null);
        this.last.next = newNode;
        this.last = newNode;
        ++size;
    }

    private void addBefore(T value, Node node) {
        if (node == this.first) {
            MyLinkedList.Node<T> newNode = new MyLinkedList.Node<>(null, value, node);
            this.first = newNode;
        } else {
            MyLinkedList.Node<T> newNode = new MyLinkedList.Node<>(node.prev, value, node);
            newNode.prev.next = newNode;
            node.prev = newNode;
        }
        ++size;
    }

    @Override
    public T get(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException("Wrong index value");
        }
        return (T) getPositionByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException("Wrong index value");
        }
        MyLinkedList.Node node = getPositionByIndex(index);
        T oldValue = (T) node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException("Wrong index value");
        }
        MyLinkedList.Node removedNode = getPositionByIndex(index);
        return (T) unlink(removedNode);
    }

    @Override
    public boolean remove(T object) {
        if (size == 0) {
            return false;
        }
        boolean found = false;
        MyLinkedList.Node<T> searchNode = new MyLinkedList.Node<>(null,
                object == null ? null : object, null);
        MyLinkedList.Node<T> currentNode = this.first;
        for (int i = 0; i < size; i++) {
            if ((currentNode.value == searchNode.value && searchNode.value == null)
                    || currentNode.value.equals(searchNode.value)) {
                found = true;
                unlink(currentNode);
                break;
            }
            currentNode = currentNode.next;
        }
        return found;
    }

    public T unlink(Node<T> node) {
        if (node == this.first && node == this.last) { // node is the one and only in the list
            this.first = this.last = null;
        } else if (node == this.last) { // node is last in the list
            this.last = node.prev;
            node.prev.next = null;
            node.prev = null;
        } else if (node == this.first) { // node is first in the list
            this.first = node.next;
            node.next.prev = null;
            node.next = null;
        } else { // node is in between
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.next = node.next = null;
        }
        --size;
        return node.value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
