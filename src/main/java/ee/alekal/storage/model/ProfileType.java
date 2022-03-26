package ee.alekal.storage.model;

public enum ProfileType {
    BUSINESS("BUSINESS"),
    PRIVATE("PRIVATE");

    public String value;

    ProfileType(String value) {
        this.value = value;
    }
}
