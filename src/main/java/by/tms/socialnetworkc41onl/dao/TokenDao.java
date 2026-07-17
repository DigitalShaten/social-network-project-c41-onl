package by.tms.socialnetworkc41onl.dao;

import by.tms.socialnetworkc41onl.model.Token;
import by.tms.socialnetworkc41onl.util.ConnectionManager;

import java.sql.*;
import java.util.Optional;
import java.util.UUID;

public class TokenDao {
    private static final String SAVE_SQL = "INSERT INTO TOKENS (ID, TYPE, IS_ACTIVE, USER_ID, CREATED_DATE) VALUES (?, ?, ?, ?, ?)";
    private static final String FIND_BY_ID_SQL = "SELECT ID, TYPE, IS_ACTIVE, USER_ID, CREATED_DATE FROM TOKENS WHERE ID = ?";
    private static final String UPDATE_SQL = "UPDATE TOKENS SET TYPE = ?, IS_ACTIVE = ?, USER_ID = ?, CREATED_DATE = ? WHERE ID = ?";

    public void save(Token token){
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL)){

            preparedStatement.setObject(1, token.getId());
            preparedStatement.setString(2, token.getType());
            preparedStatement.setBoolean(3, token.isActive());
            preparedStatement.setLong(4, token.getUserId());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(token.getCreatedDate()));
            preparedStatement.executeUpdate();

            if (!connection.getAutoCommit()) {
                connection.commit();
            }
        }catch(SQLException e){
            throw new RuntimeException("Ошибка при сохранении токена", e);
        }
    }

    public Optional<Token> findById(UUID id){
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)){

            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Token token = new Token();
                token.setId(resultSet.getObject("ID", UUID.class));
                token.setType(resultSet.getString("TYPE"));
                token.setActive(resultSet.getBoolean("IS_ACTIVE"));
                token.setUserId(resultSet.getLong("USER_ID"));
                token.setCreatedDate(resultSet.getTimestamp("CREATED_DATE").toLocalDateTime());

               return Optional.of(token);
            }
        }catch(SQLException e){
            throw new RuntimeException("Ошибка при поиске токена по ID", e);
        }
        return Optional.empty();
    }

    public void update(Token token){
        try(Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)){

            preparedStatement.setString(1, token.getType());
            preparedStatement.setBoolean(2, token.isActive());
            preparedStatement.setLong(3, token.getUserId());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(token.getCreatedDate()));
            preparedStatement.setObject(5, token.getId());
            preparedStatement.executeUpdate();

            if (!connection.getAutoCommit()) {
                connection.commit();
            }
        }catch(SQLException e){
            throw new RuntimeException("Ошибка при обновлении токена", e);
        }

    }
}
