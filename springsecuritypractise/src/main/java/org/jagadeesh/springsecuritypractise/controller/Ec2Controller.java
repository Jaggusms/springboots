package org.jagadeesh.springsecuritypractise.controller;

import org.jagadeesh.springsecuritypractise.service.Ec2Service;
import org.jagadeesh.springsecuritypractise.service.Ec2Service.Ec2InstanceInfo;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ec2")
public class Ec2Controller {

    private final Ec2Service ec2Service;

    public Ec2Controller(Ec2Service ec2Service) {
        this.ec2Service = ec2Service;
    }

    // Create an instance
    @PostMapping("/instances")
    public Ec2InstanceInfo createInstance() {
        return ec2Service.createInstance();
    }

    // Get instance details
    @GetMapping("/instances/{instanceId}")
    public Ec2InstanceInfo getInstance(
            @PathVariable String instanceId) {
        return ec2Service.getInstance(instanceId);
    }

    // Start an instance
    @PostMapping("/instances/{instanceId}/start")
    public String startInstance(
            @PathVariable String instanceId) {
        return ec2Service.startInstance(instanceId);
    }

    // Stop an instance
    @PostMapping("/instances/{instanceId}/stop")
    public String stopInstance(
            @PathVariable String instanceId) {
        return ec2Service.stopInstance(instanceId);
    }

    // Terminate an instance
    @DeleteMapping("/instances/{instanceId}")
    public String terminateInstance(
            @PathVariable String instanceId) {
        return ec2Service.terminateInstance(instanceId);
    }
}
