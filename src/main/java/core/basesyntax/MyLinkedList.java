package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (head == null) {
            newNode.preview = null;
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (size == index) {
            add(value);
        } else {
            Node<T> fond = findNodeByIndex(index);
            Node<T> insertion = new Node<>(fond.preview, value, fond);
            fond.preview = insertion;
            if (insertion.preview == null) {
                head = insertion;
            } else {
                insertion.preview.next = insertion;
            }
            size++;
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
        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node foundNode = findNodeByIndex(index);
        T oldValue = (T) foundNode.value;
        foundNode.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeByIndex = findNodeByIndex(index);
        unlink(nodeByIndex);
        return nodeByIndex.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> foundNode = this.head;
        for (int i = 0; i < size; i++) {
            if (object == null && foundNode.value == null
                    || object != null && object.equals(foundNode.value)) {
                unlink(foundNode);
                return true;
            }
            foundNode = foundNode.next;
        }
        return false;
    }

    private void unlink(Node node) {
        if (node.preview == null) {
            head = head.next;
            node.preview = null;
        } else if (node.next == null) {
            tail = tail.preview;
            node.next = null;
        } else {
            node.preview.next = node.next;
            node.next.preview = node.preview;
        }
        size--;
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
        if (size <= index || index < 0) {
            throw new IndexOutOfBoundsException("index " + index + " out of bound");
        }
    }

    private Node<T> findNodeByIndex(int elementIndex) {
        checkIndex(elementIndex);
        Node<T> search = null;
        if (size / 2 >= elementIndex) {
            int index = 0;
            search = head;
            for (int i = 0; i <= size / 2; i++) {
                if (index == elementIndex) {
                    break;
                }
                search = search.next;
                index++;
            }
        } else {
            int index = size - 1;
            search = tail;
            for (int i = size - 1; i > size / 2; i--) {
                if (index == elementIndex) {
                    break;
                }
                search = search.preview;
                index--;
            }
        }
        return search;
    }

    private static class Node<T> {
        private Node<T> preview;
        private T value;
        private Node<T> next;

        public Node(Node<T> preview, T value, Node<T> next) {
            this.preview = preview;
            this.value = value;
            this.next = next;
        }
    }
}
