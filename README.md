# SmartCampusAPI
##in this readme the answers will be provided first then a description of my project!

##1)In JAX-RS, resource classes are typically created per request, meaning a new instance is used for each incoming HTTP request. This helps avoid shared state issues because each request works with its own object instance.

However, in this project, the data (rooms, sensors, readings) is stored in static collections inside DAO classes. This means the data is shared across all requests, even though the resource instances themselves are not.Because of this, we must be careful when modifying shared data structures. If multiple requests happen at the same time, there is a risk of inconsistent data or race conditions. In a real system, this would be handled using thread-safe collections or synchronization mechanisms.


##2)Hypermedia (HATEOAS) allows an API to include links in responses that guide the client on what actions are available next. Instead of relying on external documentation, the client can dynamically navigate the API using these links.

This is beneficial because it makes the API more flexible and self-descriptive. If endpoints change, the client can still function by following links rather than hardcoding URLs. Compared to static documentation, hypermedia reduces coupling between client and server and improves maintainability.

##3)Returning only IDs when listing rooms reduces the amount of data sent over the network, which improves performance and reduces bandwidth usage.

However, returning full room objects provides more useful information to the client in a single request, reducing the need for additional API calls.
In this project, returning full objects is more practical because it simplifies client-side logic, even though it slightly increases response size.

##4)DELETE is considered idempotent because calling it multiple times produces the same final result. If a room is deleted, sending the same DELETE request again will not change the system further.

In this implementation:
- first delete - removes the room
- second delete - returns 404(not found)

##5)The @Consumes(MediaType.APPLICATION_JSON) annotation ensures that the API only accepts JSON input.

If a client sends data in a different format (like XML or plain text), JAX-RS will reject the request with a 415 Unsupported Media Type error.
This protects the API from invalid input formats and ensures consistent data parsing.

##6)Using @QueryParam for filtering is more flexible than embedding the filter in the path.

##7)The sub-resource locator pattern helps break down complex APIs into smaller, focused classes.Instead of putting all logic into one large controller, each resource (like sensor readings) has its own dedicated class. This improves readability, maintainability, and scalability.

##8)HTTP 422 is more appropriate when the request is valid but contains incorrect data.In this case, the JSON request is structured correctly, but the roomId does not exist. This is different from a 404, which means the requested resource itself does not exist.Using 422 provides more accurate feedback to the client about what went wrong.

##9)Returning raw Java stack traces is dangerous because it exposes internal system details such as class names, file paths, and server structure.An attacker could use this information to identify vulnerabilities and target specific parts of the system.By using a global exception mapper, the API hides these details and returns a generic error message instead, improving security.

##10)Using JAX-RS filters for logging is better than adding logging code inside every method. Filters allow logging to be handled in one central place, which keeps the code clean and avoids repetition.They also ensure that every request and response is logged consistently, making debugging and monitoring much easier.

## Description
A RESTful API for managing Rooms, Sensors, and Sensor Readings in a smart campus environment.

## Features
- Create and retrieve rooms
- Create and retrieve sensors
- Assign sensors to rooms
- Add sensor readings
- Retrieve readings per sensor
- Filter sensors by type
- Error handling (404, 409, 422)

## Endpoints

### Base URL
http://localhost:8080/SmartCampusAPI/api/v1/

### Rooms
- GET /rooms
- POST /rooms

### Sensors
- GET /sensors
- GET /sensors?type=CO2
- POST /sensors

### Sensor Readings
- GET /sensors/{sensorId}/readings
- POST /sensors/{sensorId}/readings

## How to Run
1. Open project in NetBeans
2. Run the project
3. Use Postman to test endpoints

##creating sensor
{
  "id": "S1",
  "type": "CO2",
  "status": "ACTIVE",
  "currentValue": 400,
  "roomId": "R1"
}

