package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public void add(T value) {
        Node<T> currentNode = new Node(value);

        if (size == 0) {
            first = currentNode;
            last = currentNode;
            currentNode.prev = null;
            currentNode.next = null;
            size++;
            return;
        }
        last.next = currentNode;
        currentNode.prev = last;
        last = currentNode;
        last.next = null;
        size++;
    }

    @Override
    public void add(T value, int index) {
        Node<T> currentNode = new Node(value);
        checkIndexForAdding(index);

        if (size == 0 || index == size()) {
            add(value);
            return;
        }
        if (index == 0) {
            currentNode.next = first;
            first.prev = currentNode;
            currentNode.prev = null;
            first = currentNode;
            size++;
            return;
        }
        Node<T> foundedNode = findNodeByIndex(index);
        foundedNode.prev.next = currentNode;
        currentNode.prev = foundedNode.prev;
        foundedNode.prev = currentNode;
        currentNode.next = foundedNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        Node<T> currentNode;
        checkIndex(index);
        currentNode = findNodeByIndex(index);
        return currentNode.value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode;
        T oldNodeValue;

        checkIndex(index);
        currentNode = findNodeByIndex(index);
        oldNodeValue = currentNode.value;
        currentNode.value = value;
        return oldNodeValue;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode;
        T oldNodeValue;
        checkIndex(index);
        currentNode = findNodeByIndex(index);

        boolean resultOfRemoving = removingObject(currentNode);

        if (resultOfRemoving) {
            oldNodeValue = currentNode.value;
            unlinkNode(currentNode);
            return oldNodeValue;
        }

        throw new IndexOutOfBoundsException("wrong index");
    }

    @Override
    public boolean remove(T object) {
        if (size == 0) {
            return false;
        }

        Node<T> currentNode = first;

        for (int i = 0; i < size(); i++) {
            if (((object == null && currentNode.value == null)
                    || (object != null && object.equals(currentNode.value)))) {
                boolean resultOfRemoving = removingObject(currentNode);
                unlinkNode(currentNode);
                return resultOfRemoving;
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
        if (size == 0) {
            return true;
        }
        return false;
    }

    private Node<T> findNodeByIndex(int index) {
        int counter;
        Node currentNode;

        if (size == 0) {
            throw new IndexOutOfBoundsException("List is empty");
        }
        if (index >= 0 && index < size / 2) {
            counter = 0;
            currentNode = first;
            while (counter != index) {
                currentNode = currentNode.next;
                counter++;
            }
            return currentNode;
        }
        if (index >= size / 2 && index < size) {
            counter = size - 1;
            currentNode = last;
            while (counter != index) {
                currentNode = currentNode.prev;
                counter--;
            }
            return currentNode;
        }
        throw new IndexOutOfBoundsException("Can't find Node by index");
    }

    private boolean checkIndexForAdding(int index) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("wrong index");
        }
        return true;
    }

    private boolean checkIndex(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("wrong index");
        }
        return true;
    }

    private boolean removingObject(Node<T> currentNode) {
        if (currentNode.prev == null) {
            first = currentNode.next;
        } else {
            currentNode.prev.next = currentNode.next;
        }

        if (currentNode.next == null) {
            last = currentNode.prev;
        } else {
            currentNode.next.prev = currentNode.prev;
        }
        return true;
    }

    private void unlinkNode(Node<T> currentNode) {
        currentNode.next = null;
        currentNode.prev = null;
        currentNode.value = null;
        size--;
    }

    private class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }

        public Node(T value) {
            this.value = value;
        }
    }
}
