package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first = null;
    private Node<T> last = null;
    private int size;

    private class Node<T> {
       T item;
       Node<T> prev;
       Node<T> next;

       Node(Node<T> prev, T item, Node<T> next) {
           this.item = item;
           this.prev = prev;
           this.next = next;
       }
    }

    private void linkToLast(T value) {
        Node<T> lastNode = last;
        final Node<T> newNode = new Node<>(lastNode, value, null);
        last = newNode;
        if (lastNode == null) {
            first = newNode;
        } else {
            lastNode.next = newNode;
        }
        size++;
    }

    private Node<T> findInTheMiddle(T value, int index) {
        if (index > (size - 1) / 2) {
           return findFromLast(value, index);
        } else {
           return findFromFirst(value, index);
        }
    }

    private Node<T> findFromFirst(T value, int index) {
        Node<T> nextNode = first;
        final Node<T> newNode = new Node<>(null, value, nextNode);
        first = newNode;
        if (nextNode != null) {
            nextNode.prev = newNode;
            for (int i = 0; i < index; i++) {
                newNode.item = nextNode.item;
                nextNode.item = value;
                nextNode = nextNode.next;
            }
        } else {
            last = newNode;
        }
        size++;
        return newNode;
    }

    private Node<T> findFromLast(T value, int index) {
        Node<T> previousNode = last;
        Node<T> newNode = new Node<>(previousNode, value, null);
        last = newNode;
        if (previousNode != null) {
            previousNode.next = newNode;
            for (int i = size; i > index; i--) {
                newNode.item = previousNode.item;
                previousNode.item = value;
                previousNode = previousNode.prev;
            }
        } else {
            first = newNode;
        }
        size++;
        return newNode;
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Can't find element with index " + index);
        }
    }

    @Override public void add(T value) {
        linkToLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Can't add element to index " + index);
        }
        findInTheMiddle(value, index);
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            Node<T> lastNode = last;
            final Node<T> newNode = new Node<>(lastNode, list.get(i), null);
            last = newNode;
            if (lastNode == null) {
                first = newNode;
            } else {
                lastNode.next = newNode;
            }
            size++;
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> previousNode = first;
        Node<T> indexNode = first;
        for (int i = 0; i < index; i++) {
            if (previousNode == null) {
                break;
            }
            indexNode.prev = previousNode;
            indexNode= previousNode.next;
            previousNode = previousNode.next;
        }
        return indexNode.item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> previousNode = last;
        T deleteValue;
        Node<T> newNode = new Node<>(previousNode, value, null);
        for (int i = size; i > index; i--) {
            newNode.prev = previousNode.prev;
            newNode.next = previousNode.next;
            previousNode = previousNode.prev;
        }
        if (previousNode != null) {
            deleteValue = previousNode.next.item;
            previousNode.next.item = value;
        } else {
            deleteValue = first.item;
            first = newNode;
        }
        return deleteValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        if (index > (size - 1) / 2) {
            Node<T> deleteNode = last;
            T deleteItem;
            for (int i = size - 1; i > index; i--) {
                deleteNode = deleteNode.prev;
            }
                deleteItem = deleteNode.item;
                    deleteNode.prev.next = deleteNode;
                    deleteNode = deleteNode.prev;
            size--;
            return deleteItem;
        } else {
            Node<T> deleteNode = first;
            T deleteItem;
            for (int i = 0; i < index; i++) {
                deleteNode = deleteNode.next;
            }
            deleteItem = deleteNode.item;
            if (index == 0) {
                 first = deleteNode.next;
            }
            if (size != 1) {
                deleteNode.next.prev = deleteNode.prev;

            }
            size--;
            return deleteItem;
        }
    }

    @Override
    public boolean remove(T object) {
        Node<T> deleteNode = first;
        for (int i = 0; i < size; i++) {
            if (deleteNode.item == object || (deleteNode.item != null && deleteNode.item.equals(object))) {
                if (i == 0) {
                    first = deleteNode.next;
                    deleteNode.prev = deleteNode;
                }
                if (size == 1) {
                    last = deleteNode.next;
                    size--;
                    return true;
                }
                deleteNode.prev.next = deleteNode.next;
                deleteNode.next.prev = deleteNode.prev;
                size--;
                return true;
            }
            deleteNode = deleteNode.next;
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
}
