# Builder Pattern Note:
* Instead of making the desired object directly, the client calls a constructor with all of the required parameters and gets a builder object.   
* Then the client calls setter-like methods on the builder object to set each optional parameter of interest.  
* Finally, the client calls a parameterless build method to generate the object, which is immutable. The builder is a static member class of the class it builds.
