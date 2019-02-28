package auth;

/**
 * Created by qukoucai001 on 2019/2/28.
 */
public enum Auth {

    /**
     *Auth information
     */
    HOST("yourHost"),
    USERNAME("yourUsername"),
    PASSWORD("yourPassword");

    private String value;

    Auth(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
