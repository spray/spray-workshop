Step 1 - Serving a simple page from spray
-----------------------------------------

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
