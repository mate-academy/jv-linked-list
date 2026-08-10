package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    @Override
    public void add(T value) {

        Node<T> node = new Node(null, value, null);

        if (size == 0) {

            head = node;
            tail = node;

        } else {

            tail.nextNode = node;
            node.prevNode = tail;
            tail = node;

        }

        size++;
    }

    @Override
    public void add(T value, int index) {

        if (index < 0 || index > size) {

            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {

            if (size == 0) {

                add(value);

                return;

            } else {

                Node<T> node = new Node(null, value, head);

                head.prevNode = node;

                head = node;

                size++;

            }

            return;

        }

        if (index == size) {

            add(value);

            return;
        }

        Node<T> nodeTemp = head; // look at this

        for (int i = 0; i < index - 1; i++) {

            nodeTemp = nodeTemp.nextNode;

        }

        Node<T> node = new Node(nodeTemp, value, nodeTemp.nextNode);

        if (nodeTemp.nextNode != null) {

            nodeTemp.nextNode.prevNode = node;

        }

        nodeTemp.nextNode = node;

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

        if (index < 0 || index >= size) {

            throw new IndexOutOfBoundsException();

        }

        return getNode(index).item;
    }

    @Override
    public T set(T value, int index) {

        T oldValue;

        if (index < 0 || index >= size) {

            throw new IndexOutOfBoundsException();

        }

        oldValue = getNode(index).item;

        getNode(index).item = value;

        return oldValue;
    }

    @Override
    public T remove(int index) {

        if (index < 0 || index >= size) {

            throw new IndexOutOfBoundsException();

        }

        Node<T> nodeToRemove = getNode(index);

        return unlinkNode(nodeToRemove);

    }

    @Override
    public boolean remove(T object) {

        Node<T> tempNode = head;

        for (int i = 0; i < size; i++) {

            boolean isEquals = (object == null) ? (tempNode.item == null)
                    : object.equals(tempNode.item);

            if (isEquals) {
                unlinkNode(tempNode);
                return true;
            }

            tempNode = tempNode.nextNode;
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

    private static class Node<E> {

        private E item;
        private Node<E> prevNode;
        private Node<E> nextNode;

        Node(Node<E> prevNode, E element, Node<E> nextNode) {

            this.item = element;
            this.prevNode = prevNode;
            this.nextNode = nextNode;

        }

    }

    private T unlinkNode(Node<T> node) {

        T removedItem = node.item;

        if (size == 1) {

            head = null;
            tail = null;

        } else if (node == tail) {

            tail = tail.prevNode;
            tail.nextNode = null;

        } else if (node == head) {

            head = head.nextNode;

            head.prevNode = null;

        } else {

            Node<T> leftNode = node.prevNode;

            Node<T> rightNode = node.nextNode;

            leftNode.nextNode = rightNode;

            rightNode.prevNode = leftNode;

        }

        size--;

        return removedItem;

    }

    private Node<T> getNode(int index) {

        Node<T> tempNode;

        if (index < size / 2) {

            tempNode = head;

            for (int i = 0; i < index; i++) {

                tempNode = tempNode.nextNode;

            }
        } else {

            tempNode = tail;

            for (int i = size - 1; i > index; i--) {

                tempNode = tempNode.prevNode;
            }
        }

        return tempNode;
    }

}
