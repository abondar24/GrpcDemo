package org.abondar.experimental.grpc.client;

import io.grpc.ManagedChannelBuilder;
import org.abondar.experimental.grpc.generated.IdRequest;
import org.abondar.experimental.grpc.generated.PersonRequest;
import org.abondar.experimental.grpc.generated.PersonResponse;
import org.abondar.experimental.grpc.generated.PersonServiceGrpc;

public class Main {
    public static void main(String[] args) {
        var channel = ManagedChannelBuilder
                .forAddress("localhost", 8024)
                .usePlaintext()
                .build();


        var stub = PersonServiceGrpc.newBlockingStub(channel);

        var persRequest = PersonRequest.newBuilder()
                .setFirstName("Alex")
                .setLastName("Bondar")
                .setAge(29)
                .build();

        var resp = stub.createPerson(persRequest);


        var id = resp.getId();

        var res = readResponse(resp);
        System.out.println("Created person\n");
        System.out.println(res);

        var idRequest = IdRequest.newBuilder()
                .setId(id)
                .build();

        resp = stub.getPerson(idRequest);
        res = readResponse(resp);
        System.out.println("Found person");
        System.out.println(res);

    }

    private static String readResponse(PersonResponse resp) {
        var rspStr = new StringBuilder();
        rspStr.append(resp.getFirstName());
        rspStr.append(resp.getLastName());
        rspStr.append("\n");
        rspStr.append(resp.getId());

        return resp.toString();
    }
}
