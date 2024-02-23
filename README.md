bb

# Star Wars API search application

Simple search tool that performs a name-based search on
the data set provided by Star Wars API: https://swapi.dev/

The application shall allow to search for API objects based on name. As a result, it shall 
return all objects of that name as well as all objects related to them.

The app was created as an interview exercise.

The app is Spring Boot based.

## Usage

Upon start, the application collects all API data and stores them in an index.

Aditionally, it exposes a search services at `/starwars`. This service allows to search
for API objects by name (or title in case of films).

### Example calls of the service:
* `/starwars?term=Tatooine` returns an array of URLs related to that name.
* `/starwars?term=Tatooine,R2-D2` returns an array of URLs related either to
                Tatooine (the planet) or R2-D2 the robot. 
                It is possible to combine names of different 
                object in a comma-separated list.

Example response (`/starwars?term=R2-D2`):

    `{"searchTerm":"R2-D2","searchResults":["https://swapi.dev/api/planets/8/","https://swapi.dev/api/people/3/","https://swapi.dev/api/films/4/","https://swapi.dev/api/films/3/","https://swapi.dev/api/films/1/","https://swapi.dev/api/films/6/","https://swapi.dev/api/films/2/","https://swapi.dev/api/films/5/","https://swapi.dev/api/species/2/"]}`

## Build

Maven is used to build the application. 
To run the application, checkout the code and run `mvn spring-boot:run`.

## Code
The application maintains an in-memory index of keywords to URLs of resources 
in the API. The index is populated once after the application starts. 

### Package `xalbrech.exercises.starwars.crawler`
The population is done by a "crawler" component (`ApiCrawler`),
that uses `http://swdev.api/api` as it's start point and traverses all API resources
referenced there. A 'RestTemplate' client is used to get the API resources,
mapping classes in `xalbrech.exercises.starwars.crawler.mapping` are used to map the JSON resources to POJOs.

### Package `xalbrech.exercises.starwars.index`
Maintains in-memory index as a multi-map and provides lookup (search) capability to 
of the search.

### Main app class
`xalbrecht.exercises.starwars.StarWarsSearchApp`. Defines beans used in the application,
performs the indexing on start-up and provides main method (calls Spring Boot).

## TODOs
The application is a simple prototype and a lot would still need to be done to make it production-like code.

* The code of mapping classes in the crawler (`xalbrech.exercises.starwars.crawler.mapping`) seems to be an overkill
for a search - it seems the search is too tightly coupled to the API data structures. Other variants to implement this 
(e.g. some use of JsonPath) might be more suitable for such task. 
* SearchIndex class is used accross the application, including the crawler code, though the crawler only needs a 
single piece of it's functionality (adding a record). This should be hidden behind an interface the crawler'd use instead 
of SearchIndex class.
* Access to search index map is not synchronized in any way. This is OK for now as the index is only populated at the start, 
but for a real use, some synchronization might be necesary.
* Improve test coverage
* Javadocs
