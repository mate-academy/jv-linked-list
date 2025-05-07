package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        if (size == 0) {
            addFirst(value);
        } else {
            addLast(value);
        }
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Your index was: " + index);
        }
        if (index == size) {
            add(value);
        } else if (index == 0) {
            Node newNode = new Node<>(null, value, head);
            head.prev = newNode;
            head = newNode;
            size++;
        } else if (index < size / 2) {
            Node addedNode = getByIndexForwards(index);
            link(addedNode, value);
            size++;
        } else if (index >= size / 2) {
            Node addedNode = getByIndexBackwards(index);
            link(addedNode, value);
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            addLast(value);
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (index < size / 2) {
            return (T) getByIndexForwards(index).value;
        } else if (index >= size / 2) {
            return (T) getByIndexBackwards(index).value;
        }
        return null;
    }

    @Override
    public T set(T value, int index) {
        Node currentNode = head;
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (index < size / 2) {
            currentNode = getByIndexForwards(index);
        } else if (index >= size / 2) {
            currentNode = getByIndexBackwards(index);
        }
        T previousValue = (T) currentNode.value;
        currentNode.value = value;
        return previousValue;
    }

    @Override
    public T remove(int index) {
        Node currentNode = head;
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (index < size / 2) {
            currentNode = getByIndexForwards(index);
        } else if (index >= size / 2) {
            currentNode = getByIndexBackwards(index);
        }
        T previousValue = (T) currentNode.value;
        unlink(currentNode);
        size--;
        return previousValue;
    }

    @Override
    public boolean remove(T object) {
        Node currentNode = head;
        int counter = 0;
        while (counter >= 0 && counter < size) {
            if (object != null && object.equals(currentNode.value)
                    || object == currentNode.value) {
                unlink(currentNode);
                size--;
                return true;
            }
            counter++;
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

    private void addFirst(T value) {
        Node currentNode = new Node<>(null, value, null);
        tail = currentNode;
        head = currentNode;
        size++;
    }

    private void addLast(T value) {
        Node previousNode = tail;
        Node currentNode = new Node(previousNode, value, null);
        previousNode.next = currentNode;
        tail = currentNode;
        size++;
    }

    private Node<T> getByIndexForwards(int index) {
        Node currentNode = head;
        int counter = 0;
        while (counter < size / 2) {
            if (counter == index) {
                return currentNode;
            }
            currentNode = currentNode.next;
            counter++;
        }
        return null;
    }

    private Node<T> getByIndexBackwards(int index) {
        Node currentNode = tail;
        int nodesLength = size - 1;
        int counter = nodesLength;
        while (counter >= size / 2) {
            if (counter == index) {
                return currentNode;
            }
            currentNode = currentNode.prev;
            counter--;
        }
        return null;
    }

    private void link(Node<T> node, T value) {
        Node previousNode = node.prev;
        Node addedNode = new Node(previousNode, value, node);
        if (previousNode != null) {
            previousNode.next = addedNode;
        }
        node.prev = addedNode;
    }

    private void unlink(Node<T> node) {
        Node previousNode = node.prev;
        Node nextNode = node.next;
        if (previousNode == null && nextNode != null) {
            nextNode.prev = null;
            head = nextNode;
        } else if (previousNode != null && nextNode == null) {
            previousNode.next = null;
            tail = previousNode;
        } else if (previousNode != null && nextNode != null) {
            previousNode.next = nextNode;
            nextNode.prev = previousNode;
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        Node(Node<T> prev, T value, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
