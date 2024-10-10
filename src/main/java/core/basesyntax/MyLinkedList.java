package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            head = tail = new Node(null, value, null);
        } else {
            Node notTheTailAnyMore = tail;
            tail = new Node(notTheTailAnyMore, value, null);
            notTheTailAnyMore.next = tail;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("given index does not exist");
        } else {
            if (index == size) {
                add(value);
            } else if (index == 0) {
                Node notTheHeadAnyMore = head;
                head = new Node(null, value, notTheHeadAnyMore);
                notTheHeadAnyMore.prev = head;
                size++;
            } else {
                int currentIndex = 0;
                if (size > 1 && index > (int) (size / 2)) {
                    currentIndex = (int) (size / 2) - 1;
                }
                Node currentNode = head;
                while (currentIndex != index) {
                    currentNode = currentNode.next;
                    currentIndex++;
                }
                Node newNode = new Node(currentNode.prev, value, currentNode);
                currentNode.prev.next = newNode;
                currentNode.prev = newNode;

                size++;
            }
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element : list) {
            add(element);
        }
    }

    public Node getNode(int index) {
        if (index > size - 1 || index < 0) {
            throw new IndexOutOfBoundsException("given index does not exist");
        } else {
            if (index < (int) (size / 2)) {
                int currentIndex = 0;
                Node currentNode = head;
                while (currentIndex != index) {
                    currentNode = currentNode.next;
                    currentIndex++;
                }
                return (Node<T>) currentNode;
            } else  {
                int currentIndex = size - 1;
                Node currentNode = tail;
                while (currentIndex != index) {
                    currentNode = currentNode.prev;
                    currentIndex--;
                }
                return (Node<T>) currentNode;
            }

        }
    }

    @Override
    public T get(int index) {
        return (T) getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        if (index > size - 1 || index < 0) {
            throw new IndexOutOfBoundsException("given index does not exist");
        } else {
            T previousNode = (T) getNode(index).value;
            getNode(index).value = value;
            return previousNode;
        }
    }

    @Override
    public T remove(int index) {
        if (index > size - 1 || index < 0) {
            throw new IndexOutOfBoundsException("given index does not exist");
        } else if (size == 1) {
            Node removedNode = head;
            head = null;
            size--;
            return (T) removedNode.value;
        } else if (index == 0) {
            Node removedNode = head;
            head.next.prev = null;
            head = head.next;
            size--;
            if (removedNode == null) {
                return null;
            }
            return (T) removedNode.value;
        } else if (index == size - 1) {
            Node removedNode = tail;
            tail.prev.next = null;
            tail = tail.prev;
            size--;
            return (T) removedNode.value;
        } else {
            int currentIndex = 0;
            Node currentNode = head;
            while (currentIndex != index) {
                currentNode = currentNode.next;
                currentIndex++;
            }
            Node removedNode = currentNode;
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
            size--;
            return (T) removedNode.value;
        }
    }

    @Override
    public boolean remove(T object) {
        Node currentNode = head;
        int index = 0;
        int indexOfRemovingObject = -1;
        do {
            if ((object != null && currentNode.value != null && object.equals(currentNode.value))
                    || object == null && currentNode.value == null) {
                indexOfRemovingObject = index;
                break;
            }
            index++;
            currentNode = currentNode.next;
        } while (index != size - 1);

        if (indexOfRemovingObject != -1) {
            remove(indexOfRemovingObject);
        }
        return indexOfRemovingObject != -1;
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
