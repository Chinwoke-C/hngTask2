package com.hngTask2.identify.service;

import com.github.fge.jsonpatch.JsonPatch;
import com.hngTask2.identify.data.dto.request.PersonRequest;
import com.hngTask2.identify.data.dto.response.ApiResponse;
import com.hngTask2.identify.data.model.Person;
import org.springframework.data.domain.Page;

public interface PersonService {
ApiResponse createPerson(PersonRequest personRequest);
Person getPersonById(Long personId);
Page<Person>getAllPersons(int pageNumber);
Person updatePerson(Long personId, JsonPatch updatePayload);
void deletePerson(Long personId);


}
