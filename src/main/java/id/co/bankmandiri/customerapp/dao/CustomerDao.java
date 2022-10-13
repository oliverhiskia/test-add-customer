package id.co.bankmandiri.customerapp.dao;

import id.co.bankmandiri.customerapp.domain.Customer;
import id.co.bankmandiri.customerapp.domain.CustomerException;
import id.co.bankmandiri.customerapp.service.CustomerService;
import id.co.bankmandiri.customerapp.util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao implements CustomerService {

    private Connection connection;

    public CustomerDao() {
        connection= DbUtil.getConnection();
    }

    public CustomerDao(Connection connection){
        this.connection=connection;
    }
    private final String queryDisplayAllCustomer="select * from customer";
    private final String queryFindCustomerById="select * from customer where customerId= ?";
    private final String queryEditCustomer="Update customer set firstName= ? , lastName= ? , dateOfBirth = ? " +
            "where customerId= ?";
    private final String queryDeleteCustomer="Delete from customer " +
            "where customerId= ?";
    private final String queryInsertCustomer="Insert into  customer(firstName,lastName,dateOfBirth) values (?,?,?)";

    @Override
    public List<Customer> displayAllCustomers() {
        List<Customer> customers=new ArrayList<>();
        try(
                //Connection connection= DriverManager.getConnection();
                Statement statement=connection.createStatement();
                ResultSet resultSet=statement.executeQuery(queryDisplayAllCustomer);
        )
        {
            while (resultSet.next()){
                customers.add(new Customer(
                        resultSet.getInt("customerId"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getDate("dateOfBirth").toLocalDate()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    @Override
    public List<Customer> displayAllCustomer() {
        return null;
    }

    @Override
    public void addCustomer(Customer customer) throws CustomerException {
        try(PreparedStatement ps=connection.prepareStatement(queryInsertCustomer)){
            ps.setString(1,customer.getFirstName());
            ps.setString(2,customer.getLastName());
            ps.setDate(3,Date.valueOf(customer.getDateOfBirth()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new CustomerException("Add Customer Fail");
        }
    }

    @Override
    public void editCustomer(Customer customer) throws CustomerException {
        try(PreparedStatement ps=connection.prepareStatement(queryEditCustomer)){
            ps.setString(1,customer.getFirstName());
            ps.setString(2,customer.getLastName());
            ps.setDate(3,Date.valueOf(customer.getDateOfBirth()));
            ps.setInt(4,customer.getCustomerId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new CustomerException("Edit Customer Fail");
        }
    }

    @Override
    public Customer findCustomerById(int id) throws CustomerException {
        Customer customer=null;
        try(PreparedStatement ps=connection.prepareStatement(queryFindCustomerById)){
            ps.setInt(1,id);
            ResultSet resultSet=ps.executeQuery();

            if (resultSet.next()){
                customer=new Customer(
                        resultSet.getInt("customerId"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getDate("dateOfBirth").toLocalDate());

            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new CustomerException("Delete Customer Fail");
        }

        if(customer!=null){
            return customer;
        }else{
            throw new CustomerException("Customer Tidak Ditemukan");
        }
    }

    @Override
    public Customer findCustomer(int id) throws CustomerException {
        Customer customer=null;
        try(PreparedStatement ps=connection.prepareStatement(queryFindCustomerById)){
            ps.setInt(1,id);
            ResultSet resultSet=ps.executeQuery();

            if (resultSet.next()){
                customer=new Customer(
                        resultSet.getInt("customerId"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getDate("dateOfBirth").toLocalDate());

            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new CustomerException("Delete Customer Fail");
        }

        if(customer!=null){
            return customer;
        }else{
            throw new CustomerException("Customer Tidak Ditemukan");
        }
    }

    @Override
    public void deleteCustomer(int id) throws CustomerException {
        try(PreparedStatement ps=connection.prepareStatement(queryDeleteCustomer)){
            ps.setInt(1,id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new CustomerException("Delete Customer Fail");
        }
    }
}