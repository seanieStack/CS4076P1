# Project Overview

This project is a server-client application designed to manage and interact with a class schedule system. It enables clients to add or remove classes from their schedule, view their current schedule, and terminate the connection to the server. The server, in response, can inform the client when the timetable is full or confirm that a class has been successfully added. This simple yet efficient communication protocol streamlines the process of schedule management for both parties.

## Actions Clients Can Request

- `ac` -> **Add Class**: Adds a specified class to the schedule.
- `rc` -> **Remove Class**: Removes a specified class from the schedule.
- `ds` -> **Display Schedule**: Displays the current class schedule.
- `st` -> **Stop (Close Connection)**: Terminates the connection with the server.

## Server Responses

- `ttf` -> **Timetable Full**: Indicates that no more classes can be added because the schedule is full.
- `cs` -> **Class Added**: Confirms that the requested class has been added to the schedule.

## Message Protocol Layout

Messages adhere to a structured format to facilitate clear and efficient communication between the client and server. The general layout is:

- **(Action)** [**(Time) (Day) (Room)**] - The additional parameters are included if the action is `ac` (add class) or `rc` (remove class).

### Client to Server Message Format

1. **Add Class (`ac`)**: Requires additional parameters.
   - Format: `ac <ModuleCode> <StartTime-EndTime> <Day> <Room>`
   - Example: `ac CS4076 09:00-10:00 monday CS4005B`
2. **Remove Class (`rc`)**: Requires the module code parameter only.
   - Format: `rc <ModuleCode>`
3. **Display Schedule (`ds`)**: No additional parameters required.
4. **Terminate Connection (`st`)**: No additional parameters required.

### Server to Client Responses

- **Timetable Full (`ttf`)**: Indicates no more classes can be added.
- **Class Added (`cs`)**: Confirms a class has been added to the schedule.

### Detailed Explanation

- For the `ac` action, clients must specify the module code, time, day, and room where the class is to be added. This detailed information is essential for scheduling the class correctly.
- For the `rc` action, clients must specify the module code, time, day, and room where the class is to be added. This detailed information is essential for removing the correct class.
- Actions like `ds` (display schedule) and `st` (stop) do not require additional information beyond the initial action command.

## Example Messages from Client to Server

- To add a class:  
  `ac CS4076 09:00-10:00 monday CS4005B`
- To stop (close the connection):  
  `st`

This structured approach to message formatting ensures a straightforward and effective interaction between the client and server, facilitating easy schedule management.
