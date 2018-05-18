# simpleJSON
Usage of simpleJSON package

Use simpleJSON.Create dsl to create an object and
output as JSON string:

{"date":"2018-05-15", "note":"Please note, that the elements in the resulting string are unsorted!", "author":{"profession":"developer", "surname":"Schröter", "name":"Tom"}, "name":"simpleJSON lib for Kotlin-JS", "version":"1.0"}

The JSON string is converted into a Kotlin HashMap:

{date=2018-05-15, note=Please note, that the elements in the resulting string are unsorted!, author={profession=developer, surname=Schröter, name=Tom}, name=simpleJSON lib for Kotlin-JS, version=1.0}

You can access the elements, e.g.:

simpleJSON lib for Kotlin-JS
by Tom Schröter
Please note, that the elements in the resulting string are unsorted!

Now let's use a literal:

{
    "name"  : "JSON literal as input",
    "empty" : "",
    "evil"  : null,
    "list"  : ["is", "also", "possible"]
}

It parses into map with nested list:

{name=JSON literal as input, evil=null, list=[is, also, possible], empty=}

Access to the list elements 
is
also
possible
where the list elements are in a row!


That's about it.
If you have suggestions and/or questions, drop me a note.
