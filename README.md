# Intelligent Mountain Rescue

This is a Search and Rescue (SAR) application with a 
forest-mountain national park. In the simulation, hikers in distress
by rescue helicopters and ground rescue units.  
  
The user can click on a field to place a person in distress, whose lifespan is reduced over time. 
Once they reach zero, they can no longer be helped. 
The GUI will show how many of the excursionists the agents have managed to have saved.  

A hiker in trouble is just waiting to be rescued. 
A helicopter or foot agents have a common goal of cooperating to save all the
(or as many as possible if this is not possible) victims, i.e.
locate, stabilize and return to base. When a person in distress 
is successfully found by a rescue team or runs out of time, he is taken off the
GUI. The GUI consists of fields similar to a map, with a top view.
There are two types of fields: forest and mountain. 
Mountain fields are not accessible to helicopter agents, but they can make 3 steps in a unit of time, as opposed to the troops on foot who can only move one field in that time. 
The user, in addition to dropping off hikers in distress, can set at the start of the simulation
the number of helicopter or foot rescue unit agents and the speed of the simulation.


## To contribute
### The environment
- use IntelliJ IDEA
- clone the repo
- open the cloned repo as a project (if prompted, gradle project)
- then reload the gradle project
- then the dependencies should be resolved
### Running the simulation
- you can run it via the `run` task
