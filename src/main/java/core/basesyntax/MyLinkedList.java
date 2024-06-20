package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(null, value, null);
        if (first == null) {
            addFirstNode(newNode);
        } else {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        ensureIndexWithinBounds(index);
        Node<T> newNode = new Node<>(null, value, null);
        if (index == 0) {
            if (first == null) {
                addFirstNode(newNode);
            } else {
                newNode.next = first;
                first.prev = newNode;
                first = newNode;
            }
        } else if (index == size) {
            if (last == null) {
                addFirstNode(newNode);
            } else {
                newNode.prev = last;
                last.next = newNode;
                last = newNode;
            }
        } else {
            Node<T> nodeAtIndex = findNodeByIndex(index);
            Node<T> prevNode = nodeAtIndex.prev;
            newNode.next = nodeAtIndex;
            newNode.prev = prevNode;
            prevNode.next = newNode;
            nodeAtIndex.prev = newNode;
        }
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
        ensureIndexWithinSize(index);
        Node<T> nodeAtIndex = findNodeByIndex(index);
        return nodeAtIndex.item;
    }

    @Override
    public T set(T value, int index) {
        ensureIndexWithinSize(index);
        Node<T> current = first;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                T result = current.item;
                current.item = value;
                return result;
            }
            current = current.next;
        }
        return null;
    }

    @Override
    public T remove(int index) {
        ensureIndexWithinSize(index);
        Node<T> toRemove;
        if (index == 0) {
            toRemove = first;
            first = first.next;
            if (first != null) {
                first.prev = null;
            } else {
                last = null;
            }
        } else if (index == size - 1) {
            toRemove = last;
            last = last.prev;
            if (last != null) {
                last.next = null;
            } else {
                first = null;
            }
        } else {
            Node<T> current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            toRemove = current;
            unlink(current);
        }

        size--;
        return toRemove.item;
    }

    @Override
    public boolean remove(T object) {
        if (first == null) {
            return false;
        }
        Node<T> current = first;
        while (current != null) {
            if ((object == null && current.item == null)
                    || (object != null && object.equals(current.item))) {
                unlink(current);
                size--;
                return true;
            }
            current = current.next;
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

    private void ensureIndexWithinBounds(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void ensureIndexWithinSize(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void addFirstNode(Node<T> node) {
        first = node;
        last = node;
    }

    private void unlink(Node<T> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            first = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            last = node.prev;
        }
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    private class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;

        Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}
