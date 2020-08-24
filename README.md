## Ansible Rule Wrapper Demo

This project demonstrates how the variables as exposed by the Invoke Ansible Decision can be controlled by the business users.

Check [Ansible Decisions](https://github.com/snandakumar87/AutomationEventDecision)

## Pre-requisites
Business central running on 8080 port. The code base points to localhost, adjust accordingly for remote business central instance.

## Run Locally
./mvnw quarkus:dev

## Test Data
Sensu Event
```
{
	"checkType": "check-disk-usage",
	"eventDate": 1598215285032,
	"status": "2",
	"hostName": "node2"
}
```

Plybook Events

```
[{
	"runDate" : 1598264999627,
	"checkName" : "check-disk-usage",
	"hostName" : "node2",
	"apbName" : "disk_cleanup"
}, {
	"runDate" : 1598265131946,
	"checkName" : "check-disk-usage",
	"hostName" : "node2",
	"apbName" : "disk_cleanup"
}]
```
Frequency 
```
2
```
Interval
```
1
```
