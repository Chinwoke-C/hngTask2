package com.hngTask2.identify.service;

import com.github.fge.jsonpatch.JsonPatch;
import com.hngTask2.identify.data.dto.request.PersonRequest;
import com.hngTask2.identify.data.dto.response.ApiResponse;
import com.hngTask2.identify.data.model.Person;
import com.hngTask2.identify.data.repository.PersonRepository;
import com.hngTask2.identify.exception.BusinessLogicException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.hngTask2.identify.utility.IdentifyUtilities.MAX_NUMBER_PER_PAGE;

@Service
@AllArgsConstructor
public class PersonInterfaceImpl implements PersonInterface{
    private final PersonRepository personRepository;
    @Override
    public ApiResponse createPerson(PersonRequest personRequest) {
        Person person = new Person();
        Person.builder()
                .id(person.getId())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .dateOfBirth(person.getDateOfBirth())
                .address(person.getAddress())
                .phoneNumber(person.getPhoneNumber())
                .build();
        Person savedPerson = personRepository.save(person);
        return getApiResponse(savedPerson);
    }

    private static ApiResponse getApiResponse(Person savedPerson) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setId(savedPerson.getId());
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Person Created Successfully");
        return apiResponse;
    }

    @Override
    public Person getPersonById(Long personId) {
        return personRepository.findById(personId).orElseThrow(
                ()-> new BusinessLogicException(
                        String.format("Person with id %d not found", personId)
                )
        );
    }

    @Override
    public Page<Person> getAllPersons(int pageNumber) {
        int page = pageNumber < 1 ? 0 : pageNumber -1;
        Pageable pageable = PageRequest.of(page, MAX_NUMBER_PER_PAGE);
        return personRepository.findAll(pageable);
    }

    @Override
    public Person updatePerson(Long personId, JsonPatch updatePayload) {
        return null;
    }

    @Override
    public void deletePerson(Long personId) {

    }
}
