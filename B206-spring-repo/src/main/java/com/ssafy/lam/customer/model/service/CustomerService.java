package com.ssafy.lam.customer.model.service;


import com.ssafy.lam.dto.Customer;

import java.util.List;

public interface CustomerService {
    public List<Customer> getAllCustomers();
    public Customer getCustomerById(int id);
    public Customer createCustomer(Customer customer);

    public Customer updateCustomer(int id, Customer updatedCustomer);

    public void deleteCustomer(int id);
}
