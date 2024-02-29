package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final String INDEX_ERR_MSG = "Index: %d, Size: %d";
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        final Node<T> currentLastItem = last;
        final Node<T> newNode = new Node<>(currentLastItem, value, null);
        last = newNode;
        if (currentLastItem == null) {
            first = newNode;
        } else {
            currentLastItem.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (index == size) {
            add(value);
        } else {
            linkBefore(value, findNodeByIndex(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndexSizeInclusive(index);
        return findNodeByIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndexSizeInclusive(index);
        Node<T> node = findNodeByIndex(index);
        T oldValue = node.item;
        node.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndexSizeInclusive(index);
        return unlink(findNodeByIndex(index));
    }

    @Override
    public boolean remove(T object) {
        for (Node<T> currentNode = first; currentNode != null; currentNode = currentNode.next) {
            if ((object == null && currentNode.item == null)
                    || (object != null && object.equals(currentNode.item))) {
                unlink(currentNode);
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

    private void checkIndexForAdd(int index) {
        if (index >= 0 && index <= size) {
            return;
        }
        throw new IndexOutOfBoundsException(String.format(INDEX_ERR_MSG, index, size));
    }

    private void checkIndexSizeInclusive(int index) {
        if (index >= 0 && index < size) {
            return;
        }
        throw new IndexOutOfBoundsException(String.format(INDEX_ERR_MSG, index, size));
    }

    private void linkBefore(T value, Node<T> nextNode) {
        final Node<T> previousNode = nextNode.previous;
        final Node<T> newNode = new Node<>(previousNode, value, nextNode);
        nextNode.previous = newNode;
        if (previousNode == null) {
            first = newNode;
        } else {
            previousNode.next = newNode;
        }
        size++;
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> item;
        if (index < size / 2) {
            item = first;
            for (int i = 0; i < index; i++) {
                item = item.next;
            }
        } else {
            item = last;
            for (int i = size - 1; i > index; i--) {
                item = item.previous;
            }
        }
        return item;
    }

    private T unlink(Node<T> nodeToRemove) {
        final T value = nodeToRemove.item;
        final Node<T> previous = nodeToRemove.previous;
        final Node<T> next = nodeToRemove.next;
        if (previous == null) {
            first = next;
        } else {
            previous.next = next;
            nodeToRemove.previous = null;
        }
        if (next == null) {
            last = previous;
        } else {
            next.previous = previous;
            nodeToRemove.next = null;
        }
        nodeToRemove.item = null;
        size--;
        return value;
    }

    private static class Node<T> {
        private T item;
        private Node<T> previous;
        private Node<T> next;

        Node(Node<T> previous, T item, Node<T> next) {
            this.item = item;
            this.previous = previous;
            this.next = next;
        }
    }
}
