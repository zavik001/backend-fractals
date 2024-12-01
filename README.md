# fractals
Fractal generation with flexible settings and performance analysis.
[Generate stunning fractals, download them, and explore performance analysis.](https://zavik001.github.io/frontend-fractals/index.html)


[<img src="https://raw.githubusercontent.com/zavik001/backend-fractals/main/docs/web.gif" alt="demonstration" width="600">](https://raw.githubusercontent.com/zavik001/backend-fractals/main/docs/web.gif)


## How it works?
[<img src="https://raw.githubusercontent.com/zavik001/backend-fractals/main/docs/diagram.png" alt="Diagram" width="400">](https://raw.githubusercontent.com/zavik001/backend-fractals/main/docs/diagram.png)

## User Interface  
The user interacts with the system via a **web interface** where they specify parameters for fractal generation, such as size, generator type, transformation settings, and image processing options.  
[Frontend implementation is available here.](https://github.com/zavik001/frontend-fractals)   

## Server Requests  

### Fractal Generation  
- The user sends a `POST` request with parameters to the server.  
- **Limitation:** Only one request per second is allowed per IP (implemented via `OncePerRequestFilter`).  

### Performance Analysis  
- A `POST` request with a placeholder is sent (as `ngrok` does not handle `GET` requests properly).  

## Server Processing  

### Fractal Generation  
- The server processes the request via the `GenerateFractalService`.  
- The fractal is generated and saved as an image (in the specified format).  
- Execution time is stored in **Redis** for further analysis.  

### Performance Analysis  
- **Redis** stores graph points (iterations and execution time).  
- The `FractalRedisService` retrieves data for graph visualization.  

## Data Storage  
- **PostgreSQL** — Stores data about requests and parameters.  
- **Redis** — Enables fast storage and retrieval of performance data.  

## References  
The fractal generation logic is based on the source:  
**The Fractal Flame Algorithm**  
Authors: Scott Draves (Spotworks, NYC, USA) and Erik Reckase (Berthoud, CO, USA)  
Published: September 2003, last revised November 2008.
