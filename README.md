TwitterMiner
============

## The task ##
The task is to create a **web application** that, using **Twitter's API**, taps the **real-time stream of tweets** and filters for those which contains user provided words. **Each user has its own set of search criteria**.
The app should display both **the stream of tweets** and the **statistics of hashTags** extracted from the tweets containing the provided filter words in a moving window fashion.

Search for "poker tournament" should show every tweet which has either **poker** or **tournament** in the content text:
```
bla bla bla poker blab bla #Casino #Poker
bla blu blabal tournament blabala #Poker
```
Those 2 tweet should be displayed and they should be used to extract hasTag infos.
```
#Poker, 2
#Casino, 1
```

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
- Twitter Bootstrap
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

### Messaging ###

####JMS####
- message queue: analytics service sends start/stop stream commands to the stream service with the keywords
- tweet queue: the stream service sends tweets messages the the analytics service
 
####Websocket####
- between the dashboard service and the analytics service

####RESTFul web service####
- UI sends healthcheck requests to the Eureka via the Zuul message gateway

![Alt text](images/services.jpg?raw=true "Flow")

##The application##

You can try the application here: http://52.5.10.150:8090/#/

![Alt text](images/main.jpg?raw=true "Flow")

