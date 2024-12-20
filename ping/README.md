## Build Notes

```bash
mvn clean install
docker build -t motomark/ping .
docker push motomark/ping
kubectl apply -f deployment.yaml  
```

## Using Feign

Basic idea:

https://medium.com/@iK8sJourneyMaverick/efficient-communication-in-spring-boot-applications-leveraging-feign-client-on-kubernetes-41f4c78b01c3

See PongClient.java

## Using Telepresence
Ping:

```bash
sudo telepresence intercept ping --port 9800 --env-file ~/git/pingpong/ping/ping.env
```

## Using a ConfigMap 

How to run a Spring Boot deployment with a application-properties stored in a ConfigMap merged with the version packaged in the jar. To do this we have to first store the file in the Config Map by using the configmap.yaml:

```bash
apiVersion: v1
kind: ConfigMap
metadata:
  name: ping-app-config
  namespace: default
data:
  application.properties: |
    spring.application.name=ping
    server.port=9800
    spring.config.import=optional:configserver
    spring.cloud.config.enabled=false
    ping.message=Hello K3s!

```

Apply the configMap:

```bash
kubectl apply -f configmap.yaml 
```

Then reference a Volume mapping for the Pod with the application.properties file from the ConfigMap copied to the /config mount path. 
On startup Spring Boot will first look for application.properties next to the jar file or under /config and merge this with the internal jar properties combined.

```bash

volumeMounts:
        - name: application-config 
        mountPath: "/config"
        readOnly: true
    volumes:
    - name: application-config
    configMap:
        name: ping-app-config
        items:
        - key: application.properties 
        path: application.properties

```
(see deployment.yaml).

We can now view the application.properties under /config on the pod like this:

```bash
kubectl get pods

NAME                               READY   STATUS    RESTARTS        AGE
book-deployment-847d67d5fc-t467k   2/2     Running   10 (7d2h ago)   26d
ping-647f7fbf8b-zdww9              1/1     Running   0               11m
pong-68cdcbd9c9-zt69j              2/2     Running   2 (7d2h ago)    10d


kubectl exec -it ping-647f7fbf8b-zdww9 -- bash

cat /config/application.properties

spring.application.name=ping
server.port=9800
spring.config.import=optional:configserver
spring.cloud.config.enabled=false
ping.message=Hello K3s!

```

We can see the ping.message is now different from that of resources/application.properties.

## Kubernetes Discovery

ref: https://medium.com/globant/kubernetes-microservices-discovery-2dd754712606

By adding the following dependencies we can now 'discover' the pong-service by its name and don't have to worry about hostname and port in the Feign client call. See PongClient.java

```bash
        <!-- Kubernetes Discovery for finding the Pong service by name. -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-kubernetes-fabric8-all</artifactId>
         </dependency>
         
        <!-- Fabric8 needs bouncy castle -->
        <dependency>
            <groupId>org.bouncycastle</groupId>
            <artifactId>bcpkix-jdk15on</artifactId>
            <version>1.70</version>
        </dependency>
```

