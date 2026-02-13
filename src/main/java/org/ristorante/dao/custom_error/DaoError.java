package org.ristorante.dao.custom_error;

import java.sql.SQLException;

public class DaoError extends RuntimeException {
    public DaoError(String message, SQLException e) {
        super(message, e);
    }
}
