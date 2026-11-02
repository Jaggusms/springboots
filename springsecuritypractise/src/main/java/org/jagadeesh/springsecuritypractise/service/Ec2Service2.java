package com.example.ec2.service;

import java.util.List;

import org.springframework.stereotype.Service;

import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.*;

@Service
public class Ec2Service {

    private final Ec2Client ec2Client;

    public Ec2Service(Ec2Client ec2Client) {
        this.ec2Client = ec2Client;
    }

    // 1. CREATE EC2 INSTANCE
    public String createInstance(
            String amiId,
            String instanceType,
            String subnetId,
            List<String> securityGroupIds,
            String keyName) {

        RunInstancesRequest.Builder requestBuilder =
                RunInstancesRequest.builder()
                        .imageId(amiId)
                        .instanceType(InstanceType.fromValue(instanceType))
                        .minCount(1)
                        .maxCount(1)
                        .subnetId(subnetId)
                        .securityGroupIds(securityGroupIds)
                        .tagSpecifications(
                                TagSpecification.builder()
                                        .resourceType(ResourceType.INSTANCE)
                                        .tags(
                                                Tag.builder()
                                                        .key("Name")
                                                        .value("springboot-ec2")
                                                        .build(),
                                                Tag.builder()
                                                        .key("Environment")
                                                        .value("learning")
                                                        .build()
                                        )
                                        .build()
                        );

        // Key pair is optional if you use another
        // configured connection method, such as SSM.
        if (keyName != null && !keyName.isBlank()) {
            requestBuilder.keyName(keyName);
        }

        RunInstancesResponse response =
                ec2Client.runInstances(requestBuilder.build());

        Instance instance = response.instances().get(0);

        return instance.instanceId();
    }

    // 2. START EC2 INSTANCE
    public String startInstance(String instanceId) {

        StartInstancesResponse response =
                ec2Client.startInstances(
                        StartInstancesRequest.builder()
                                .instanceIds(instanceId)
                                .build()
                );

        return response.startingInstances().toString();
    }

    // 3. STOP EC2 INSTANCE
    public String stopInstance(String instanceId) {

        StopInstancesResponse response =
                ec2Client.stopInstances(
                        StopInstancesRequest.builder()
                                .instanceIds(instanceId)
                                .build()
                );

        return response.stoppingInstances().toString();
    }

    // 4. REBOOT EC2 INSTANCE
    public String rebootInstance(String instanceId) {

        ec2Client.rebootInstances(
                RebootInstancesRequest.builder()
                        .instanceIds(instanceId)
                        .build()
        );

        return "Reboot request submitted for " + instanceId;
    }

    // 5. GET INSTANCE DETAILS
    public List<Instance> describeInstances(String instanceId) {

        DescribeInstancesResponse response =
                ec2Client.describeInstances(
                        DescribeInstancesRequest.builder()
                                .instanceIds(instanceId)
                                .build()
                );

        return response.reservations()
                .stream()
                .flatMap(reservation -> reservation.instances().stream())
                .toList();
    }

    // 6. LIST ALL INSTANCES
    public List<Instance> listInstances() {

        DescribeInstancesResponse response =
                ec2Client.describeInstances();

        return response.reservations()
                .stream()
                .flatMap(reservation -> reservation.instances().stream())
                .toList();
    }

    // 7. TERMINATE EC2 INSTANCE
    public String terminateInstance(String instanceId) {

        TerminateInstancesResponse response =
                ec2Client.terminateInstances(
                        TerminateInstancesRequest.builder()
                                .instanceIds(instanceId)
                                .build()
                );

        return response.terminatingInstances().toString();
    }
}