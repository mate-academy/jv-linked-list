package core.basesyntax.service;

public class ObjectService {
    public static boolean equals(Object firstObject, Object secondObject) {
        return firstObject == secondObject
                || firstObject != null && firstObject.equals(secondObject);
    }
}
