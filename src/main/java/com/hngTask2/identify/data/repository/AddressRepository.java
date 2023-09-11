package com.hngTask2.identify.data.repository;

import com.hngTask2.identify.data.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Long, Address> {
}
