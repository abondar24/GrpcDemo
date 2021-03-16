package org.abondar.experimental.grpc.server.service;


import io.grpc.stub.StreamObserver;
import org.abondar.experimental.grpc.generated.IdRequest;
import org.abondar.experimental.grpc.generated.PersonRequest;
import org.abondar.experimental.grpc.generated.PersonResponse;
import org.abondar.experimental.grpc.generated.PersonServiceGrpc;
import org.abondar.experimental.grpc.server.model.Person;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class PersonServiceImpl extends PersonServiceGrpc.PersonServiceImplBase {

    private Map<String, Person> storage = new HashMap<>();

    @Override
    public void createPerson(PersonRequest personRequest, StreamObserver<PersonResponse> responseObserver){
       var id = UUID.randomUUID().toString();

       var person = new Person(id,personRequest.getFirstName(),
               personRequest.getLastName(),personRequest.getAge());

       storage.put(id,person);

       var resp = buildResponse(person);

       responseObserver.onNext(resp);
       responseObserver.onCompleted();
    }

    @Override
    public void getPerson(IdRequest idRequest, StreamObserver<PersonResponse> responseObserver){
        var id = idRequest.getId();

        var res = storage.get(id);

        PersonResponse resp;
        if (res!=null){
            resp = buildResponse(res);
        } else {
            resp = PersonResponse.newBuilder()
                    .build();
        }

        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    private PersonResponse buildResponse(Person person){
        return PersonResponse.newBuilder()
                .setId(person.getId())
                .setFirstName(person.getFirstName())
                .setLastName(person.getLastName())
                .setAge(person.getAge())
                .build();
    }
}
