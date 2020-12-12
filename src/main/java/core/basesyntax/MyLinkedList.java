package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private static class Node<T> {
        private T element;
        private Node<T> first;
        private Node<T> last;

        public Node(Node<T> first, T element, Node<T> last) {
            this.element = element;
            this.first = first;
            this.last = last;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int sizeList = 0;

    @Override
    public boolean add(T value) {
        Node<T> lastNode = tail;
        Node<T> newNode = new Node<>(lastNode, value, null);
        tail = newNode;
        if (lastNode == null) {
            head = newNode;
            sizeList++;
        } else {
            lastNode.last = newNode;
            sizeList++;
        }
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index > sizeList || index < 0) {
            throw new IndexOutOfBoundsException("Incorrect index");
        }
        if (index == sizeList) {
            add(value);
        } else {
            linkBefore(value, nodeIndex(index));
        }
    }

    @Override
    public boolean addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            T value = list.get(i);
            add(value);
        }
        return true;
    }

    @Override
    public T get(int index) {
        isIndexExist(index);
        return nodeIndex(index).element;
    }

    @Override
    public T set(T value, int index) {
        isIndexExist(index);
        Node<T> currentIndex = nodeIndex(index);
        T oldVal = currentIndex.element;
        currentIndex.element = value;
        return oldVal;
    }

    @Override
    public T remove(int index) {
        isIndexExist(index);
        return position(nodeIndex(index));
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            for (Node<T> newNode = head; newNode != null; newNode = newNode.last) {
                if (newNode.element == null) {
                    position(newNode);
                    return true;
                }
            }
        } else {
            for (Node<T> nodeNew = head; nodeNew != null; nodeNew = nodeNew.last) {
                if (object.equals(nodeNew.element)) {
                    position(nodeNew);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int size() {
        return sizeList;
    }

    @Override
    public boolean isEmpty() {
        return sizeList == 0;
    }

    private void isIndexExist(int index) {
        if (index < 0 || index >= sizeList) {
            throw new IndexOutOfBoundsException("Element can't be found!");
        }
    }

    Node<T> nodeIndex(int index) {
        Node<T> newNodeHead = head;
        Node<T> newNodeTail = tail;
        if (index < (sizeList >> 1)) {
            for (int i = 0; i < index; i++) {
                newNodeHead = newNodeHead.last;
            }
        } else {
            for (int i = sizeList - 1; i > index; i--) {
                newNodeTail = newNodeTail.first;
            }
        }
        return index < (sizeList >> 1) ? newNodeHead : newNodeTail;
    }

    void linkBefore(T item, Node<T> current) {
        Node<T> prev = current.first;
        Node<T> newNode = new Node<>(prev, item, current);
        current.first = newNode;
        if (prev == null) {
            head = newNode;
            sizeList++;
        } else {
            prev.last = newNode;
            sizeList++;
        }
    }

    T position(Node<T> currentIndex) {
        T element = currentIndex.element;
        Node<T> next = currentIndex.last;
        Node<T> prev = currentIndex.first;
        if (prev == null) {
            head = next;
        } else {
            prev.last = next;
            currentIndex.first = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.first = prev;
            currentIndex.last = null;
        }
        currentIndex.element = null;
        sizeList--;
        return element;
    }
}
