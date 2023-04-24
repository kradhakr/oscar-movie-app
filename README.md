
# Spring Boot - Oscar Movie Application
Build Rest API for Oscar Movie Application

•	Open the project into IntelliJ 
•	Run the OscarApplication.java can also run using command 
•	Reach till spring boot application folder (oscar-movie-app)
•	Run this command (mvn spring-boot:run)

API Details :
http://localhost:8090/swagger-ui.html#

# Generate Token
•	Open in PostMan below URL 
http://localhost:8090/authenticate
In the body, add the below JSON
{
"username": "admin",
"password": "password"
}

We will get response as JWT Token
{
"jwtToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY2NzUyMDE
zNCwiaWF0IjoxNjY3NTAyMTM0fQ.6y11X86Pf0IXObldNEO5Xjt7ztBwky6tfRsgnR48C9XV9H0fsBs0Hir7yZm
CkIGDD_tUCW0TTT7zx6M1h7fYUg"
}

Use this JWT for the calling the APIs.

#  API Details:
1.  API for whether a movie won a “Best Picture” Oscar, given a movie's title.
    http://localhost:8090/api/oscarAwards/bestPictures/{movieTitle}

2.  API to get a list of 10 top-rated movies ordered by box office value.
     http://localhost:8090/api/movies/topRatedMovies/10

# Docker packaging
docker build --tag oscar-movie-app .
docker run -p 8080:8090 -t oscar-movie-app --name oscar-movie-app
Access the application using URL : http://192.168.99.100:8080/api/oscarAwards


