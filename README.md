# Android_Route_Based_Navigation
Excercising an issue with Android Navigation Component. 

Essentially `NavController.popUpTo` works well with xml with ids and kotlin dsl with routes but fails when using xml with routes.

Issue: https://issuetracker.google.com/issues/256122093

# UPDATE
This issue was resolved and it is a matter of removing ids from the route based nav graph xml file and setting the nav graph in a more manual way.
