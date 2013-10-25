Themen
------

 * Development setup
   * IntelliJ
   * Clone project
   * sbt
     * gen-idea ?
   * load in idea
   * sbt revolver round-trip
 * spray basics
   * bootup and binding
   * basic route structure
 * Directives
   * serving with complete
   * serving static files from resources
   * path matching
     * designing an API routing table and representing it in spray
   * form fields
     * get
     * post
   * marshalling
     * json un/marshalling
     * Content-Type negotiation (for one endpoint also xml)
   * rejections
   * authentication
 * testing
 * custom rejection
 * custom directives
   * e.g. methodOverride


Twitter nachbauen
 * message senden
 * alle messages anschauen
 * anderer Person folgen


Screens:
 * alle user
 *

Step 0:

 * install IntelliJ
 * install sbt
 * clone repo
 * make re-start work
 * see hello-world in browser
 * change port-number server runs on
 * (run server on two ports)

Step 1:

 * Change the root path to serve HTML instead of the plain text. You may start with this code:

 ```html
 <html>
    <body>
        <div>Hello World</div>
    </body>
 </html>
 ```

 * Now add a picture or css to the html and serve it from a resource.
   * Create `web` directory inside the `src/main/resources` folder
   * Put an image file into
   * add an image tag to your html code
   * Add spray-routing code to serve files from the `web` resource directory


Step 2a:

 * path-structure
   * / -> Hello world
   * /users/:user/greet -> Hello $user
   * /users/:user/saygoodbye -> Goodbye $user
   * /add/12/15 -> calculate sum

Step 2b:
 * writing tests that test the route from 2a

Step 3a:
 * write a form that takes two numbers and submits them to the route
 * write routing logic that uses `parameters` to access data and return result

Step 3b:
 * write tests to check that logic

Step 4:

 * return calculation + result as json
   * model domain
   * write jsonformat
   * use marshaller

 * also write tests

Step 5:

 * run calculation logic in an extra actor

Step 6:

 * accessing request data like `User-Agent` header
 * basic auth
