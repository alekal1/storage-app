package ee.alekal.storage.model;

public enum ProfileType {
    BUSINESS("BUSINESS"),
    PRIVATE("PRIVATE"),
    ADMIN("ADMIN");

    public String value;

    ProfileType(String value) {
        this.value = value;
    }
}
