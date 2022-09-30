package core.basesyntax;

public interface GetNodeBy<T> {

    default Node<T> getNodeByIndex(int targetIndex, Node<T> head, int size) {
        if (targetIndex < 0 || targetIndex >= size) {
            throw new IndexOutOfBoundsException(
                    String.format("Index incorrect! Index: %s, Size: %s", targetIndex, size));
        }
        Node<T> currentNode = head;
        for (int i = size - 1; i != targetIndex; i--) {
            currentNode = currentNode.getPrev();
        }
        return currentNode;
    }

    default Node<T> getNodeByValue(T value, Node<T> head) {
        Node<T> node = head;
        while (node != null) {
            if (node.getValue() == value || node.getValue().equals(value)) {
                return node;
            }
            node = node.getNext();
        }
        return null;
    }
}
