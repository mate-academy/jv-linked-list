package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> firstNode;
    private Node<T> lastNode;

    public MyLinkedList() {
        size = 0;
    }

    @Override
    public boolean add(T value) {
        Node<T> addedNode = new Node<>(value, null, null);
        if (size == 0) {
            firstNode = addedNode;
        } else {
            lastNode.nextNode = addedNode;
            addedNode.previousNode = lastNode;
        }
        lastNode = addedNode;
        size++;
        return true;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        Node<T> addedNode = new Node(value, null, null);
        if (index == 0) {
            addedNode.nextNode = firstNode;
            firstNode.previousNode = addedNode;
            firstNode = addedNode;
        } else {
            Node<T> prevNode = getNodeByIndex(index - 1);
            Node<T> nextNode = getNodeByIndex(index);
            addedNode.previousNode = prevNode;
            addedNode.nextNode = nextNode;
            prevNode.nextNode = addedNode;
            nextNode.previousNode = addedNode;
        }
        size++;
    }

    @Override
    public boolean addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> setNode = new Node<>(value, null, null);
        T result = getNodeByIndex(index).item;
        if (index == 0) {
            setNode.nextNode = firstNode.nextNode;
            firstNode.nextNode.previousNode = setNode;
            firstNode = setNode;
        } else if (index == size - 1) {
            setNode.previousNode = lastNode.previousNode;
            lastNode.previousNode.nextNode = setNode;
            lastNode = setNode;
        } else {
            Node<T> prevNode = getNodeByIndex(index - 1);
            Node<T> nextNode = getNodeByIndex(index + 1);
            setNode.nextNode = nextNode;
            setNode.previousNode = prevNode;
            prevNode.nextNode = setNode;
            nextNode.previousNode = setNode;
        }
        return result;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T result = getNodeByIndex(index).item;
        if (index == 0) {
            firstNode = firstNode.nextNode;
            if (firstNode != null) {
                firstNode.previousNode = null;
            } else {
                lastNode = null;
            }
        } else if (index == size - 1) {
            lastNode = lastNode.previousNode;
            lastNode.nextNode = null;
        } else {
            Node<T> prevNode = getNodeByIndex(index - 1);
            Node<T> nextNode = getNodeByIndex(index + 1);
            nextNode.previousNode = prevNode;
            prevNode.nextNode = nextNode;
        }
        size--;
        return result;
    }

    @Override
    public boolean remove(T t) {
        for (int i = 0; i < size; i++) {
            Node<T> node = getNodeByIndex(i);
            if ((node.item != null && node.item.equals(t)) || node.item == t) {
                remove(i);
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
        Node<T> result = firstNode;
        if (size / 2 > index) {
            for (int i = 0; i < index; i++) {
                result = result.nextNode;
            }
        } else {
            result = lastNode;
            for (int i = size - 1; i > index; i--) {
                result = result.previousNode;
            }
        }
        return result;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    private static class Node<T> {
        private T item;
        private Node<T> previousNode;
        private Node<T> nextNode;

        public Node(T item, Node<T> previousNode, Node<T> nextNode) {
            this.item = item;
            this.previousNode = previousNode;
            this.nextNode = nextNode;
        }
    }
}
