package org.abondar.experimental.grpc.server;

import io.grpc.ServerBuilder;
import org.abondar.experimental.grpc.server.service.PersonServiceImpl;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException,InterruptedException {
          var srv = ServerBuilder
                  .forPort(8024)
                  .addService(new PersonServiceImpl())
                  .build();

          srv.start();
          System.out.println("Started gRPC server on port 8024");
          srv.awaitTermination();
    }
}
