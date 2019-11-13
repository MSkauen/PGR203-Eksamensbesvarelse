package no.kristiania.taskManager.jdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao<ENTITY> {

    protected DataSource dataSource;

    public AbstractDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public long insert(ENTITY o, String sql) throws SQLException {
        try(Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                mapToStatement(o, statement);
                statement.executeUpdate();

                ResultSet generatedKeys = statement.getGeneratedKeys();
                generatedKeys.next();
                return generatedKeys.getLong("id");
            }

        }
    }

    public List<ENTITY> listAll(String sql) throws SQLException {
        try(Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)){
                try (ResultSet rs = statement.executeQuery()) {
                    List<ENTITY> result = new ArrayList<>();
                    while(rs.next()){
                        result.add(mapFromResultSet(rs));
                    }
                    return result;
                }
            }
        }
    }


    protected abstract void mapToStatement(ENTITY o, PreparedStatement stmt) throws SQLException;

    protected abstract ENTITY mapFromResultSet(ResultSet rs) throws SQLException;

    protected abstract ENTITY retrieve(long id) throws SQLException;

    public ENTITY retrieve(long id, String sql) throws SQLException {
        try(Connection connection = dataSource.getConnection()){
            try(PreparedStatement statement = connection.prepareStatement(sql)){
                statement.setLong(1, id);
                try(ResultSet rs = statement.executeQuery()){
                    if(rs.next()){
                        return mapFromResultSet(rs);
                    } else {
                        return null;
                    }
                }
            }
        }
    }
}
