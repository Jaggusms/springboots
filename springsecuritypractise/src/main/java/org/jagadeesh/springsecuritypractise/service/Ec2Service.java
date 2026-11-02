package org.jagadeesh.springsecuritypractise.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.*;

@Service
public class Ec2Service {

    private final Ec2Client ec2Client;
    private final String amiId;
    private final String instanceType;

    public Ec2Service(
            Ec2Client ec2Client,
            @Value("${aws.ec2.ami-id}") String amiId,
            @Value("${aws.ec2.instance-type}") String instanceType) {

        this.ec2Client = ec2Client;
        this.amiId = amiId;
        this.instanceType = instanceType;
    }

    // 1. Create an EC2 instance
    public Ec2InstanceInfo createInstance() {

        RunInstancesRequest request = RunInstancesRequest.builder()
                .imageId(amiId)
                .instanceType(InstanceType.fromValue(instanceType))
                .minCount(1)
                .maxCount(1)
                .build();

        RunInstancesResponse response =
                ec2Client.runInstances(request);

        Instance instance = response.instances().get(0);

        return toInstanceInfo(instance);
    }

    // 2. Get details of an EC2 instance
    public Ec2InstanceInfo getInstance(String instanceId) {

        DescribeInstancesResponse response =
                ec2Client.describeInstances(
                        DescribeInstancesRequest.builder()
                                .instanceIds(instanceId)
                                .build()
                );

        Instance instance = response.reservations()
                .stream()
                .flatMap(reservation -> reservation.instances().stream())
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Instance not found: " + instanceId));

        return toInstanceInfo(instance);
    }

    // 3. Start a stopped instance
    public String startInstance(String instanceId) {

        ec2Client.startInstances(
                StartInstancesRequest.builder()
                        .instanceIds(instanceId)
                        .build()
        );

        return "Start request submitted for " + instanceId;
    }

    // 4. Stop a running instance
    public String stopInstance(String instanceId) {

        ec2Client.stopInstances(
                StopInstancesRequest.builder()
                        .instanceIds(instanceId)
                        .build()
        );

        return "Stop request submitted for " + instanceId;
    }

    // 5. Terminate an instance permanently
    public String terminateInstance(String instanceId) {

        ec2Client.terminateInstances(
                TerminateInstancesRequest.builder()
                        .instanceIds(instanceId)
                        .build()
        );

        return "Termination request submitted for " + instanceId;
    }

    private Ec2InstanceInfo toInstanceInfo(Instance instance) {

        String state = instance.state() == null
                ? "UNKNOWN"
                : instance.state().nameAsString();

        return new Ec2InstanceInfo(
                instance.instanceId(),
                state,
                instance.privateIpAddress(),
                instance.publicIpAddress()
        );
    }

    public record Ec2InstanceInfo(
            String instanceId,
            String state,
            String privateIp,
            String publicIp) {
    }
}