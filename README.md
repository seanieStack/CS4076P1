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

## Example Messages from Client to Server

- To add a class:  
  `ac CS4076 09:00-10:00 monday CS4005B`
- To stop (close the connection):  
  `st`

This format ensures a straightforward and effective interaction between the client and the server, facilitating easy schedule management.
