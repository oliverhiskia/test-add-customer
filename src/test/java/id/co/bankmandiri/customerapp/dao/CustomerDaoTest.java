package id.co.bankmandiri.customerapp.dao;

import id.co.bankmandiri.customerapp.domain.Customer;
import id.co.bankmandiri.customerapp.domain.CustomerException;
import id.co.bankmandiri.customerapp.util.DbUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.time.LocalDate;

class CustomerDaoTest {
    private static CustomerDao dao;

    @BeforeEach
    void setUp() {
        Connection connection = DbUtil.getConnection();
        dao = new CustomerDao(connection);
    }

    @Test
    void displayAllCustomer() {
    }

    @Test
    void addCustomer() {
        Customer customer = new Customer("Adi", "Saputra",
                LocalDate.of(1997, 7,2));
        try {
            dao.addCustomer(customer);
            Customer result = dao.findCustomerById(3);
            Assertions.assertEquals("John", result.getFirstName());
        } catch (CustomerException e) {
            e.printStackTrace();
        }
    }

    @Test
    void editCustomer() {
    }

    @Test
    void findCustomerById() {
    }

    @Test
    void deleteCustomer() {
    }
}