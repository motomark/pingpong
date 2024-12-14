## Using Feign and Kubernetes
Need to create a service account etc see:
* https://medium.com/@iK8sJourneyMaverick/efficient-communication-in-spring-boot-applications-leveraging-feign-client-on-kubernetes-41f4c78b01c3

## Using Telepresence
Ping:
```
sudo telepresence intercept ping --port 9800 --env-file ~/git/pingpong/ping/ping.env
```

## ConfigMap 

How to run a Spring Boot deployment with a application-properties stored in a ConfigMap and not packaged in the jar. To do this we have to first store the file in the Config Map :
```
kubectl create configmap spring-app-config --from-file=src/main/resources/application.properties
```

Then reerence a Volume mapping for the Pod with the application.properties file from the ConfigMap copied to the /config mount path. On startup Spring Boot will first look for application.properties next to the jar file or under /config and merge/overwrite any properties with the external version:
```
volumeMounts:
        - name: application-config 
        mountPath: "/config"
        readOnly: true
    volumes:
    - name: application-config
    configMap:
        name: spring-app-config 
        items:
        - key: application.properties 
        path: application.properties
```
(see deployment.yaml).


