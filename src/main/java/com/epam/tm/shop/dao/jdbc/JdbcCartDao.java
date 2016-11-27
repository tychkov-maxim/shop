package com.epam.tm.shop.dao.jdbc;

import com.epam.tm.shop.dao.CartDao;
import com.epam.tm.shop.entity.Cart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JdbcCartDao extends JdbcDao<Cart> implements CartDao {

    private static final String INSERT_QUERY = "INSERT INTO users VALUES(DEFAULT,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_QUERY = "UPDATE users SET login = ?, password = ?, first_name = ?, last_name = ?, role = ?, account = ?, account_unit = ?, address = ? WHERE id = ?";
    private static final String SELECT_QUERY = "SELECT * FROM users JOIN roles ON users.role = roles.id WHERE users.id = ?";
    private static final String DELETE_QUERY = "DELETE FROM users WHERE id = ?";
    private static final String SELECT_QUERY_BY_LOGIN = "SELECT * FROM users JOIN roles ON users.role = roles.id WHERE users.login = ?";


    public JdbcCartDao(Connection connection) {
        super(connection);
    }


    @Override
    public Cart save(Cart entity) throws JdbcException {
        return super.save(entity);
    }

    @Override
    protected void setPsFields(PreparedStatement ps, Cart entity) throws JdbcException {

    }

    @Override
    protected String getInsertQuery() {
        return INSERT_QUERY;
    }

    @Override
    protected String getUpdateQuery() {
        return UPDATE_QUERY;
    }

    @Override
    protected String getSelectQueryById() {
        return SELECT_QUERY;
    }

    @Override
    protected String getDeleteQuery() {
        return DELETE_QUERY;
    }
    @Override
    protected List<Cart> createEntityFromResultSet(ResultSet rs) throws JdbcException {
        /*List<User> users = new ArrayList<>();
        try {
            while (rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setRole(new Role(rs.getString("roles.name"),rs.getInt("role")));
                user.setAccount(Money.of(CurrencyUnit.getInstance(rs.getString("account_unit")),rs.getBigDecimal("account")));
                user.setAddress(rs.getString("address"));
                users.add(user);
            }
        } catch (SQLException e) {
            log.error("creating user entity from result set was failed");
            throw new JdbcException(e);
        }

        if (users.size() == 0)
            throw new JdbcException("no one user was found");

        return users;*/
        return new ArrayList<>();
    }


    // FIXME: 22.11.2016
    public void setCon(Connection con){
        this.connection = con;
    }

}
