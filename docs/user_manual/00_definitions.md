## Definitions

The main concept around which the application revolves is the *translation project*. Each translation project has a descriptive name and at least one language (in can be set up with just one and more languages can be added at a later stage). 

A translation project must have exactly one *base language*, i.e. the source language that will be considered the reference value from which to derive the localized variants. 

A project can contain zero or more *messages*, each of which is identified by a unique *key* corresponding to the resource ID that developers reference in the source code to display a given string in the user interface. Besides the key, each message is constituted by a *segment* which contains the text in one of the project languages. 

In the localized versions, segments are displayed in pairs (source and target): the group of key, source message and target message can be identified as a *translation unit*.
