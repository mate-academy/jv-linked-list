package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    public static class Node<T> {
        private Node<T> prev;
        private T item;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            addFirstElement(value);
            return;
        }
        Node<T> newNode = new Node(last, value, null);
        Node<T> currentNode = last;
        newNode.prev = currentNode;
        currentNode.next = newNode;
        last = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        if (size == 0) {
            addFirstElement(value);
            return;
        }
        if (index == 0) {
            addElementAtZeroIndex(value);
            return;
        }
        if (index == size) {
            addElementAtLastIndex(value);
            return;
        }
        Node<T> currentNode = first;
        int counter = 0;
        while (counter < index - 1) {
            currentNode = currentNode.next;
            counter++;
        }
        Node<T> prevNode = currentNode;
        Node<T> nextNode = currentNode.next;
        Node<T> newNode = new Node<>(prevNode, value, nextNode);
        prevNode.next = newNode;
        nextNode.prev = newNode;
        size++;
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
        if (index <= size / 2 + 1) {
            return scanFromBegin(index);
        }
        return scanFromEnd(index);
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> currentNode = first;
        int counter = 0;
        while (counter < index) {
            currentNode = currentNode.next;
            counter++;
        }
        T oldValue = currentNode.item;
        currentNode.item = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        if (size == 1) {
            Node<T> oldElement = first;
            removeSingleElement();
            return oldElement.item;
        }
        if (index == 0) {
            Node<T> oldElement = first;
            removeFirstElement();
            return oldElement.item;
        }
        if (index == size - 1) {
            Node<T> oldElement = last;
            removeLastElement();
            return oldElement.item;
        }
        Node<T> oldElement = getNodeByIndex(index);
        unlinkAndRelink(oldElement);
        return oldElement.item;
    }

    @Override
    public boolean remove(T object) {
        Node<T> currentNode = first;
        int counter = 0;
        while (currentNode != null) {
            if (size == 1) {
                removeSingleElement();
                return true;
            }
            if ((currentNode.item == object || (currentNode.item != null
                    && currentNode.item.equals(object))) && counter == 0) {
                removeFirstElement();
                return true;
            }
            if ((currentNode.item == object || (currentNode.item != null
                    && currentNode.item.equals(object))) && counter == size - 1) {
                removeLastElement();
                return true;
            }
            if (currentNode.item == object || currentNode.item != null
                    && currentNode.item.equals(object)) {
                Node<T> oldElement = currentNode;
                unlinkAndRelink(oldElement);
                return true;
            }
            currentNode = currentNode.next;
            counter++;
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
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("wrong index");
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("wrong index");
        }
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> currentNode = first;
        int counter = 0;
        while (counter < index) {
            currentNode = currentNode.next;
            counter++;
        }
        return currentNode;
    }

    private void addFirstElement(T value) {
        Node<T> newNode = new Node(null, value, null);
        first = newNode;
        last = newNode;
        size++;
    }

    private void addElementAtZeroIndex(T value) {
        Node<T> newNode = new Node(null, value, first);
        first.prev = newNode;
        first = newNode;
        size++;
    }

    private void addElementAtLastIndex(T value) {
        Node<T> newNode = new Node(last, value, null);
        last.next = newNode;
        last = newNode;
        size++;
    }

    private void removeSingleElement() {
        first = null;
        last = null;
        size--;
    }

    private void removeFirstElement() {
        Node<T> oldElement = first;
        first = oldElement.next;
        first.prev = null;
        oldElement.next = null;
        size--;
    }

    private void removeLastElement() {
        Node<T> oldElement = last;
        last = oldElement.prev;
        last.next = null;
        oldElement.prev = null;
        size--;
    }

    private T scanFromBegin(int index) {
        Node<T> currentNode = first;
        int counter = 0;
        while (counter < index) {
            currentNode = currentNode.next;
            counter++;
        }
        return currentNode.item;
    }

    private T scanFromEnd(int index) {
        Node<T> currentNode = last;
        int counter = size - 1;
        while (counter > index) {
            currentNode = currentNode.prev;
            counter--;
        }
        return currentNode.item;
    }

    private void unlinkAndRelink(Node<T> oldElement) {
        Node<T> prevNode = oldElement.prev;
        Node<T> nextNode = oldElement.next;
        prevNode.next = nextNode;
        nextNode.prev = prevNode;
        oldElement.next = null;
        oldElement.prev = null;
        size--;
    }
}
