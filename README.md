TwitterMiner
============

##Design principles###

- Microservices architecture pattern
- Domain driven design
- Message driven architecture

##Design aims##
- Scalable
- System resilience
- distributed system
- loosely coupled services

##Technology stack##

###Back-end###

- Java 8
- Lombok
- RabbitMQ
- Websocket (SockJS)
- Spring Boot
- Spring Cloud Netflix
- Spring Cloud
- Spring Cloud Bus
- Spring AMQP
- Netflix OSS: Eureka, Zuul
- Twitter4J

###Front-end###
- AngularJS
- Twitter Bootstrap CSS
- FlatUI
- Yeoman
- Bower

###Environment###
- Amazon EC2
- Supervisord


----------

##APIs##

####Spring Cloud####
Provides tools for developers to quickly build some of the common patterns in distributed systems (e.g. configuration management, service discovery, circuit breakers, intelligent routing, micro-proxy, control bus, one-time tokens, global locks, leadership election, distributed sessions, cluster state).

####Spring Cloud Netflix####
Provides Netflix OSS integrations for Spring Boot apps through auto configuration and binding to the Spring Environment and other Spring programming model idioms.

####Spring Cloud Bus####
Links nodes of a distributed system with a lightweight message broker. This can then be used to broadcast state changes (e.g. configuration changes) other management instructions.

####Zuul####
Zuul is an edge service that provides dynamic routing, monitoring, resiliency, security

####Eureka####
AWS Service registry for resilient mid-tier load balancing and failover

##Architecture##

##Microservices###
- Dashboard: User interface
- Analytics: Operates on the incoming tweets
- Stream: Handles twitter streams

##Supporting services###
- Eureka: service discovery
- Zuul (part of the Dashboard service): API gateway

![Alt text](images/services.jpg?raw=true "Flow")

##The application##

You can try the application here: http://52.5.10.150:8090/#/

![Alt text](images/main.jpg?raw=true "Flow")

