package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            first = new Node<>(null, value, null);
            last = first;
        } else {
            last.next = new Node<>(last, value, null);
            last = last.next;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        indexAddValidatorForAddElement(index);
        if (size > index) {
            Node<T> currentNode = getNode(index);
            if (currentNode == first) {
                first = currentNode.prev = new Node<>(null, value, currentNode);
                size++;
            } else {
                Node<T> prev = currentNode.prev;
                Node<T> newNode = new Node<>(prev, value, currentNode);
                prev.next = newNode;
                currentNode.prev = newNode;
                size++;
            }
        } else {
            add(value);
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (list != null) {
            for (T element : list) {
                add(element);
            }
        }
    }

    @Override
    public T get(int index) {
        indexValidation(index);
        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {
        indexValidation(index);
        Node<T> currentNode = getNode(index);
        T temp = currentNode.item;
        currentNode.item = value;
        return temp;
    }

    @Override
    public T remove(int index) {
        indexValidation(index);
        Node<T> currentNode = getNode(index);
        Node<T> temp = currentNode;
        if (currentNode == first) {
            return deleteFirstElementInList(currentNode, temp);
        }

        if (currentNode == last) {
            return deleteElementFromEnd(currentNode);
        }
        return deleteElementFromMiddleOfList(currentNode);
    }

    @Override
    public boolean remove(T object) {
        if (object == null) {
            return deleteWhenElementIsNull(object);
        }

        if (findIndex(object) < 0) {
            return false;
        }
        return remove(findIndex(object)).equals(object);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void indexAddValidatorForAddElement(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds List");
        }
    }

    private void indexValidation(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds List");
        }
    }

    private int findIndex(T object) {
        Node<T> currentNode = first;
        for (int i = 0; i < size; i++) {
            if (object.equals(currentNode.item)) {
                return i;
            }
            currentNode = currentNode.next;
        }
        return -1;
    }

    private Node<T> getNode(int index) {
        Node<T> currentNode;
        if (index > size / 2) {
            currentNode = last;
            for (int i = size; i > index + 1; i--) {
                currentNode = currentNode.prev;
            }
        } else {
            currentNode = first;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        }
        return currentNode;
    }

    private void unlink(Node<T> node) {
        node.prev = null;
        node.next = null;
    }

    private T deleteElementFromMiddleOfList(Node<T> currentNode) {
        Node<T> nextNode = currentNode.next;
        Node<T> prevNode = currentNode.prev;
        prevNode.next = nextNode;
        nextNode.prev = prevNode;
        size--;
        unlink(currentNode);
        return currentNode.item;
    }

    private T deleteFirstElementInList(Node<T> currentNode, Node<T> temp) {
        if (currentNode.next == null) {
            first = last = null;
            size--;
            unlink(currentNode);
            return currentNode.item;
        }
        Node nextNode = currentNode.next;
        nextNode.prev = null;
        first = nextNode;
        size--;
        unlink(currentNode);
        return temp.item;
    }

    private T deleteElementFromEnd(Node<T> currentNode) {
        Node prevNode = currentNode.prev;
        prevNode.next = null;
        last = prevNode;
        size--;
        unlink(currentNode);
        return currentNode.item;
    }

    private boolean deleteWhenElementIsNull(T object) {
        Node<T> currentNode = first;
        for (int i = 0; i < size; i++) {
            if (currentNode.item == object) {
                remove(i);
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    private static class Node<E> {
        private E item;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
