package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(last,value,null);
        if (first == null) {
            first = last = newNode;
        } else {
            last.next = newNode;
            last.next.prev = last;
            last = last.next;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        Objects.checkIndex(index,size + 1);
        if (first == null) {
            Node<T> newNode = new Node<>(first,value,last);
            first = last = newNode;
        } else if (index == 0) {
            Node<T> newNode = new Node<>(null,value,first);
            newNode.next = first;
            first = newNode;
        } else if (index == size) {
            Node<T> newNode = new Node<>(last,value,null);
            newNode.prev = last;
            last.next = newNode;
            last = newNode;
        } else {
            Node<T> nodeAtIndex = getNodeByIndex(index);
            Node<T> newNode = new Node<>(nodeAtIndex.prev,value,nodeAtIndex);
            newNode.next = nodeAtIndex;
            newNode.prev = nodeAtIndex.prev;
            if (newNode.prev != null) {
                newNode.prev.next = newNode;
            }
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i <= list.size() - 1; i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        Objects.checkIndex(index,size);
        return getNodeByIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        Objects.checkIndex(index,size);
        Node<T> newNode = getNodeByIndex(index);
        T oldNode = newNode.element;
        newNode.element = value;
        return oldNode;
    }

    @Override
    public T remove(int index) {
        Objects.checkIndex(index,size);
        Node<T> current = getNodeByIndex(index);
        unlink(current);
        return current.element;
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> node = first; node != null; node = node.next) {
            if (node.element == object || object != null && object.equals(node.element)) {
                unlink(node);
                return true;
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

    private Node<T> getNodeByIndex(int index) {
        Node<T> current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private void unlink(Node<T> node) {
        Node<T> nextNode = node.next;
        Node<T> prevNode = node.prev;
        if (nextNode != null) {
            nextNode.prev = prevNode;
        } else {
            last = size == 1 ? first : prevNode;
        }
        if (prevNode != null) {
            prevNode.next = nextNode;
        } else {
            first = nextNode;
        }
        size--;
        if (isEmpty()) {
            first = null;
            last = null;
        }
    }

    static class Node<T> {
        private T element;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev,T element,Node<T> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }
    }
}
