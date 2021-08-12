# A Calendar Application

## A Digital Replacement for the Student Calendar

Good time management and Task management is imperative of every student. As a rather forgetful person, I have
always relied on my student calendar to remind myself of what I need to do, and by when. Naturally, I was
excited when I had the chance to implement this essential part of my life into an application. I hope to be
able to bring the same utility a normal student calendar brings, and perhaps even more.


## What Can It Do?
The main function of the application will be to keep track of tasks in a calendar format.
Since its intended purpose is a Task tracking for students, certain design choices will be made to reflect this 
decision. 

Here are some of its features:
- It has an interface for every day
- Add or remove tasks on specific days.
- View the current day
- View dates in the past or in the future

## User Stories
- As a user, I want to be able to add a Task to a specific date and time
- As a user, I want to be able to view the list of tasks on a specific date
- As a user, I want to be able to delete a Task previously added
- As a user, I want to be able to view if today is the current day
- As a user, I want to be able to view other days, and be able to go back 
- As a user, I want every day of a month to be added and visible 
- As a user, I want to be able to save my calendar to file
- As a user, I want to be able to load my calendar from file

## Phase 4: Task 2
- I chose to create a type hierarchy revolving around the Task class.
- The Task class changed into an abstract class, and two new classes (NormalTask and UrgentClass) extends Task.

## Phase 4: Task 3
When looking at the design of my code, I believe that I did surprisingly well to keep coupling levels low. The
associations from Calendar down to Task has minimal coupling. Furthermore, there aren't any bidirectional
associations, so it isn't too difficult to limit coupling. In terms of refactoring, however, there
is a lot of work to be done in the Date class. It currently performs too many tasks, so I would
split the class into several (perhaps one each for TomorrowDate and YesterdayDate, and further
delegating the work of creating complete calendar date lists to another class).


