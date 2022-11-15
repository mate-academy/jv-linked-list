package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    public MyLinkedList() {
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(last, value, null);
        if (size == 0) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else {
            indexCheck(index);
            Node<T> nodeByIndex = getNode(index);
            Node<T> newNode = new Node<>(nodeByIndex.prev, value, nodeByIndex);
            if (index > 0) {
                nodeByIndex.prev.next = newNode;
            } else {
                first = newNode;
            }
            nodeByIndex.prev = newNode;
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
        indexCheck(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        indexCheck(index);
        Node<T> nodeByIndex = getNode(index);
        T oldValue = nodeByIndex.value;
        nodeByIndex.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        indexCheck(index);
        Node<T> removedNode = getNode(index);
        unlink(removedNode);
        size--;
        return removedNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        int index = 0;
        for (int i = 0; i < size; i++) {
            if (object == currentNode.value || object != null && object.equals(currentNode.value)) {
                unlink(currentNode);
                size--;
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

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    private void indexCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index of value has to be more or equal 0 and less then MyLinkedList size");
        }
    }

    public Node<T> getNode(int index) {
        Node<T> currentNode = null;
        if (index <= size / 2) {
            currentNode = first;
            for (int i = 0; i <= index; i++) {
                if (i == index) {
                    break;
                }
                currentNode = currentNode.next;
            }
        }
        if (index > size / 2) {
            currentNode = last;
            for (int i = size - 1; i >= index; i--) {
                if (i == index) {
                    break;
                }
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    public void unlink(Node<T> removedNode) {
        Node<T> next = removedNode.next;
        Node<T> prev = removedNode.prev;
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            removedNode.next = null;
        }
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            removedNode.prev = null;
        }
    }
}
