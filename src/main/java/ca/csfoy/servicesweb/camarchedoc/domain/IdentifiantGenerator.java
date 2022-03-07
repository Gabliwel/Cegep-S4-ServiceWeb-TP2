package ca.csfoy.servicesweb.camarchedoc.domain;

public class IdentifiantGenerator {

    private static int nextId = 100;

    public static Integer getNextId() {
        nextId += 1;
        return nextId;
    }
    
    public static String getNextIdAsString() {
        nextId += 1;
        return nextId + "";
    }
}
