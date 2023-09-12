package com.hngTask2.identify.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.hngTask2.identify.data.dto.request.PersonRequest;
import com.hngTask2.identify.data.dto.response.ApiResponse;
import com.hngTask2.identify.data.model.Address;
import com.hngTask2.identify.data.model.Person;
import com.hngTask2.identify.data.repository.AddressRepository;
import com.hngTask2.identify.data.repository.PersonRepository;
import com.hngTask2.identify.exception.BusinessLogicException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.hngTask2.identify.utility.IdentifyUtilities.MAX_NUMBER_PER_PAGE;

@Service
@AllArgsConstructor
@Slf4j
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    @Override
    public ApiResponse createPerson(PersonRequest personRequest) {
        Person person = new Person();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        LocalDate dateOfBirth = convertStringDateToLocalDate(personRequest.getDateOfBirth());
        person.setFirstName(personRequest.getFirstName());
        person.setLastName(personRequest.getLastName());
        person.setDateOfBirth(dateOfBirth);
        person.setAddress(personRequest.getAddress());
        person.setPhoneNumber(personRequest.getPhoneNumber());
//        Person.builder()
//                .id(person.getId())
//                .firstName(personRequest.getFirstName())
//                .lastName(personRequest.getLastName())
//                .dateOfBirth(dateOfBirth)
//                .address(personRequest.getAddress())
//                .phoneNumber(personRequest.getPhoneNumber())
//                .build();
        Person savedPerson = personRepository.save(person);
        return getApiResponse(savedPerson);
    }

    private LocalDate convertStringDateToLocalDate(String dateOfBirth) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(dateOfBirth, dateTimeFormatter);
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
        ObjectMapper mapper = new ObjectMapper();
        Person foundPerson = getPersonById(personId);
        //parse person object to node
        JsonNode node = mapper.convertValue(foundPerson, JsonNode.class);
        try {
            //apply patch
            JsonNode updatedNode = updatePayload.apply(node);
            //parse node back to passenger object
            Person updatedPerson = mapper.convertValue(updatedNode, Person.class);
            updatedPerson = personRepository.save(updatedPerson);
            return updatedPerson;
        } catch (JsonPatchException e) {
            log.error(e.getMessage());
            throw new RuntimeException();
        }


    }

    @Override
    public void deletePerson(Long personId) {
        personRepository.deleteById(personId);

    }
}
