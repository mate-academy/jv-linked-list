package core.basesyntax;

import java.util.List;

@SuppressWarnings("unchecked")
public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private static final double GROWTH_FACTOR = 1.5;
    private static final int MAX_LIST_SIZE = 10;
    private Node<T> head;
    private Node<T> tail;
    private int size;


    @Override
    public void add(T value) {
        growIfArrayFull();
        if (size == 0) {
            nodes[size] = new Node<>(null, value, null);
            head = nodes[size];
        } else {
            nodes[size] = new Node<>(nodes[size - 1], value, null);
            nodes[size - 1].nextNode = nodes[size];
        }
        tail = nodes[size];
        size++;
    }

    @Override
    public void add(T value, int index) {
        growIfArrayFull();
        if (index == size) {
            add(value);
            return;
        }
        checkIndex(index);
        size++;
        if (size > 1) {
            for (int currentIndex = size - 1; currentIndex > index; currentIndex--) {
                moveElementsAddition(currentIndex);
            }
        } else if (size == 1) {
            moveElementsAddition(index);
        }
        if (index > 0) {
            nodes[index] = new Node<>(nodes[index - 1], value, nodes[index + 1]);
            nodes[index - 1].nextNode = nodes[index];
        } else {
            nodes[index] = new Node<>(null, value, nodes[index + 1]);
            head = nodes[index];
        }
        nodes[index + 1].prevNode = nodes[index];
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return nodes[index].nodeItem;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        final T currentNodeItemHolder = nodes[index].nodeItem;
        nodes[index] = new Node<>(nodes[index].prevNode, value, nodes[index].nextNode);
        if (index == 0 && size > 1) {
            nodes[index].nextNode.prevNode = nodes[index];
        } else if (index < size - 1) {
            nodes[index].nextNode.prevNode = nodes[index];
            nodes[index].prevNode.nextNode = nodes[index];
        } else if (index == size - 1) {
            nodes[index].prevNode.nextNode = nodes[index];
        }
        updateHeadAndTail(index);
        return currentNodeItemHolder;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T deletedNodeItemHolder = nodes[index].nodeItem;
        moveElementsDeletion(index);
        updateHeadAndTail(index);
        return deletedNodeItemHolder;
    }

    @Override
    public boolean remove(T object) {
        int index = 0;
        for (Node<T> node : nodes) {
            if ((node != null) && (object == node.nodeItem
                    || ((object != null) && object.equals(node.nodeItem)))) {
                moveElementsDeletion(index);
                updateHeadAndTail(index);
                return true;
            }
            index++;
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index " + index + " is Out of bounds for this ArrayList!");
        }
    }

    private void moveElementsAddition(int currentIndex) {
        Node<T> tempCurrentNodeHolder = nodes[currentIndex];
        Node<T> tempPreviousNodeHolder = nodes[currentIndex - 1];
        nodes[currentIndex + 1] = tempCurrentNodeHolder;
        nodes[currentIndex] = tempPreviousNodeHolder;
    }

    private void moveElementsDeletion(int index) {
        int nextElementIndex = index + 1;
        if (size == 1) {
            size--;
            nodes[index] = null;
            return;
        }
        if (index == 0 && size > 1) {
            nodes[index].nextNode.prevNode = null;
        } else if (index < size - 1) {
            nodes[index].nextNode.prevNode = nodes[index].prevNode;
            nodes[index].prevNode.nextNode = nodes[index].nextNode;
        } else if (index == size - 1) {
            nodes[index].prevNode.nextNode = nodes[index].nextNode;
        }
        while (nextElementIndex < size) {
            nodes[index++] = nodes[nextElementIndex++];
        }
        size--;
        nodes[index] = null;
    }

    private void growIfArrayFull() {
        if (size == nodes.length) {
            int newLength = (int) (nodes.length * GROWTH_FACTOR);
            Node<T>[] tempValues = nodes;
            nodes = new Node[newLength];
            System.arraycopy(tempValues, 0, nodes, 0, size);
        }
    }

    private void updateHeadAndTail(int index) {
        if (index == 0 && size == 0) {
            head = null;
            tail = null;
        } else if (index == 0 && size == 1) {
            head = nodes[0];
            tail = nodes[0];
        } else if (index == 0 && size > 1) {
            head = nodes[index];
        } else if (index == size) {
            tail = nodes[index - 1];
        } else if (index != 0 && index == size - 1) {
            tail = nodes[index];
        }
    }

    private static class Node<T> {
        private T nodeItem;
        private Node<T> nextNode;
        private Node<T> prevNode;

        Node(Node<T> prev, T insertedElement, Node<T> next) {
            this.nodeItem = insertedElement;
            this.nextNode = next;
            this.prevNode = prev;
        }
    }
}
