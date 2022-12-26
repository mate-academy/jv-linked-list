package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> tail;
    private Node<T> head;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            tail = new Node<>(null, value, null);
            head = tail;
        } else if (size == 1) {
            tail = new Node<T>(head, value, null);
            head.next = tail;
        } else if (size >= 2) {
            Node<T> penultimateNode = new Node<>(tail.preview, tail.item, null);
            tail.preview.next = penultimateNode;
            tail = new Node<>(penultimateNode, value, null);
            penultimateNode.next = tail;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (size < index || index < 0) {
            throw new IndexOutOfBoundsException("index " + index + " out of bound");
        } else if (size == index) {
            add(value);
        } else if (index == 0) {
            Node<T> insertion = new Node<>(null, head.item, head.next);
            head = new Node<>(null, value, insertion);
            insertion.preview = head;
            size++;
        } else {
            Node<T> foundNode = findNodeByIndex(index);
            Node<T> insertion = new Node<>(foundNode.preview, value, foundNode);
            foundNode.preview.next = insertion;
            foundNode.preview = insertion;
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
        checkIndex(index);
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        T oldValue = get(index);
        findNodeByIndex(index).item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        Node<T> nodeByIndex = findNodeByIndex(index);
        if (index == 0 && size != 1) {
            head = head.next;
            nodeByIndex.preview = null;
        } else if (index == size - 1) {
            tail = tail.preview;
            nodeByIndex.next = null;
        } else {
            nodeByIndex.preview.next = nodeByIndex.next;
            nodeByIndex.next.preview = nodeByIndex.preview;
        }
        size--;
        return nodeByIndex.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> head = this.head;
        int objectIndex = -1;
        for (int i = 0; i < size; i++) {
            objectIndex++;
            if (object == null && head.item == null) {
                remove(objectIndex);
                return true;
            } else if (object != null && object.equals(head.item)) {
                remove(objectIndex);
                return true;
            }
            head = head.next;
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
        if (size <= index || index < 0) {
            throw new IndexOutOfBoundsException("index " + index + " out of bound");
        }
    }

    private Node<T> findNodeByIndex(int elementIndex) {
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
        private T item;
        private Node<T> next;

        public Node(Node<T> preview, T item, Node<T> next) {
            this.preview = preview;
            this.item = item;
            this.next = next;
        }
    }
}
