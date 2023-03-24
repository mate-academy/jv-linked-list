package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    @Override
    public void add(T value) {
        if (size >= 1) {
            Node<T> prevTale = tail;
            tail = new Node<>(prevTale, value, null);
            prevTale.next = tail;
        } else {
            Node<T> fistNode = new Node<>(null, value, null);
            head = fistNode;
            tail = fistNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        checkIndexForBoundAdd(index);
        if (index == size) {
            add(value);
            return;
        } else if (index == 0) {
            Node<T> initialHeadNode = head;
            head = new Node<>(null, value, initialHeadNode);
            initialHeadNode.prev = head;
            size++;
            return;
        }
        if (index < (size - 1) / 2) {
            int indexCounter = 0;
            Node<T> tempNode = head.next;
            while (indexCounter != index) {
                indexCounter++;
                if (indexCounter == index) {
                    linkNewNode(tempNode, value);
                }
                tempNode = tempNode.next;

            }
        } else {
            int indexCounter = size;
            Node<T> tempNode = tail;
            while (indexCounter != index) {
                indexCounter--;
                if (indexCounter == index) {
                    linkNewNode(tempNode, value);
                }
                tempNode = tempNode.prev;
            }
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
        checkIndexForBound(index);
        return getNode(index).body;
    }

    @Override
    public T set(T value, int index) {
        checkIndexForBound(index);
        if (index == 0) {
            Node<T> initialHeadNode = head;
            head = new Node<>(null, value, initialHeadNode.next);
            initialHeadNode.next.prev = head;
            return initialHeadNode.body;
        } else if (index == size - 1) {
            Node<T> initialTaleNode = tail;
            tail = new Node<>(initialTaleNode.prev, value, null);
            initialTaleNode.prev.next = tail;
            return initialTaleNode.body;
        } else {
            Node<T> toRemoveNode = getNode(index);
            Node<T> insteadNode = new Node<>(toRemoveNode.prev, value, toRemoveNode.next);
            toRemoveNode.prev.next = insteadNode;
            toRemoveNode.next.prev = insteadNode;
            return toRemoveNode.body;
        }
    }

    @Override
    public T remove(int index) {
        checkIndexForBound(index);
        Node<T> toRemoveNode = getNode(index);
        if (index == 0) {
            head = getNode(1);
        } else if (index == size - 1) {
            tail = getNode(size - 2);
        }
        unlinkNode(toRemoveNode);
        size--;
        return toRemoveNode.body;
    }

    @Override
    public boolean remove(T object) {
        for (int i = 0; i < size; i++) {
            if (get(i) == object || get(i) != null && get(i).equals(object)) {
                remove(i);
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

    private void linkNewNode(Node<T> tempNode, T value) {
        Node<T> toAdd = new Node<>(tempNode.prev, value, tempNode);
        tempNode.prev.next = toAdd;
        tempNode.prev = toAdd;
        size++;
    }

    private void checkIndexForBound(int index) {
        String exception = String.format("Wrong index! %d index is out of bounds.", index);
        if (index < 0 || index > size - 1 && size > 1) {
            throw new IndexOutOfBoundsException(exception);
        }
    }

    private void checkIndexForBoundAdd(int index) {
        String exception = String.format("Wrong index! %d index is out of bounds.", index);
        if (index < 0 || index > size && size > 1) {
            throw new IndexOutOfBoundsException(exception);
        }
    }

    private void unlinkNode(Node<T> node) {
        Node<T> nextNode = node.next;
        Node<T> prevNode = node.prev;
        if (prevNode != null) {
            prevNode.next = nextNode;
        }
        if (nextNode != null) {
            nextNode.prev = prevNode;
        }
    }

    private Node<T> getNode(int index) {
        if (index == 0) {
            return head;
        }
        if (index < (size - 1) / 2) {
            int indexCounter = 0;
            Node<T> tempNode = head.next;
            while (indexCounter != index) {
                indexCounter++;
                if (indexCounter == index) {
                    return tempNode;
                }
                tempNode = tempNode.next;

            }
        } else {
            int indexCounter = size;
            Node<T> tempNode = tail;
            while (indexCounter != index) {
                indexCounter--;
                if (indexCounter == index) {
                    return tempNode;
                }
                tempNode = tempNode.prev;
            }
        }
        return null;
    }

    private static class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T body;

        public Node(Node<T> prev, T body, Node<T> next) {
            this.prev = prev;
            this.next = next;
            this.body = body;
        }
    }
}
