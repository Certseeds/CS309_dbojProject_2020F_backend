package sustech.dbojbackend.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SqlLanguage {
    MYSQL(0L),
    SQLITE(1L),
    POSTGRESQL(2L);
    private final Long code;

    SqlLanguage(Long code) {
        this.code = code;
    }

    @JsonValue
    public Long getValue() {
        return code;
    }
}
