
## Technologies used

This section will briefly describe the main technologies involved in the project and the reasons why each of them was selected. This is a personal side project so, naturally, some of the decisions were totally arbitrary and relied more on personal taste or personal history than anything else. I'll briefly document my experience with any alternatives that I've tried and abandoned for each of the sections.

### Language and build tools

 The original idea behind this project was to create an open source application that could run on all major desktop operating systems (Windows, Linux and MacOS), sharing as much code as possible. At the same time, I had roughly ten years of experience in mobile development, and I've always been an enthusiast of the Kotlin programming language since it is a very powerful and expressive yet concise language featuring really interesting concepts such as structured concurrency with coroutines, null safety with optionals, and a multi-paradigm approach combining the best of object-orientation and functional programming.
 
 I have been using Kotlin for my Android projects for the last 3 years. This is why I decided to implement this application as a **Kotlin multiplatform** project, where virtually all the code can safely reside in the `jvmMain` source set. Another implication of this choice was that I could leverage my experience with Gradle as a build tool.

### User Interface

Being an Android developer, I was already accustomed to working with **Jetpack Compose** on a daily basis, but until 2022 I'd had few chances to experiment its multiplaftorm releases, especially the desktop flavor on the JVM. This project was the perfect occasion to put it under test and see how far it was possible to go with it. 

Compose makes it easy to create and maintain isolated and reusable components with a very convenient declarative style. The desktop version apparently has less components than other older and more widespread toolkits but it has all the basic building blocks that are needed and it is very easy to combine them together and style them to fit the project needs. Despite being a mobile-first library, as far as I've seen it has a quite good support for desktop specificities such as mouse pointer inputs (right click, double click, …), context menus, etc.

I didn't evaluate many alternatives, I never regretted not having used Swing or SWT honestly.

### Architecture and navigation

One of the main aspects that I missed from Android development on desktop was a navigation library (since the Jetpack libraries ported as of mid-2023 does not include Navigation). In this project I decided to give a chance to the **Decompose** library (more info [here](https://arkivanov.github.io/Decompose/)).

Apart from providing constructs for both stack- and slot-based navigation, I found out that its hierarchical structure could fit into a Model-View-ViewModel architectural pattern (with the component being the viewmodel and the content being the view). Moreover, since each node of the hierarchy only knows about its direct children, it creates a highly modular architecture where it is quite easy to refactor and "transplant" entire subgraphs from one point to another. 

*[Side note: The validation UI was originally a modal dialog, but transforming it into just another component to display in the bottom panel was almost painless.]*

I evaluated other alternatives such as Precompose and Voyager, but I liked more Decompose for being unobtrusive and integrating seamlessly with Koin for dependency injection (Voyager as far as I know still is missing support for Koin integration on desktop).

### Dependency Injection

I was primarily accustomed to Dagger and Hilt from the Android world, and I had much less experience with other frameworks for Kotlin (such as Kodein or Koin). I tried both and the final choice was set on **Koin** because I find it concise and yet very powerful. Sometimes it can be tricky to figure out the cause of some error because messages are not always crystal clear (even if they mostly are, especially compared to Dagger 😅) and missing bindings only pop up at runtime due to its nature, but I found that if you know what you are doing – at least most of the time – it's no great deal.

I evaluated other alternatives such as Kodein, honestly it did its job well but I remember having had some problems in injecting the same instance of a singleton across different Gradle modules (and I didn't want to fallback to plain language `object`s without being able to pass dependencies dynamically).

### Primary storage and ORM

I wanted all the application data to be saved on a local database. My experience with embedded databases was limited to sqlite3 on mobile, but I wanted to interact with it using a JDBC driver. I tried using the `org.xerial:sqlite-jdbc` library and it worked while running debug builds, but I found out that I couldn't run release builds because it failed notarizing on MacOS. So, looking for alternatives, I decided to give a try to the **H2** embedded DB and its JDBC driver and I never regretted it.

Apart from the storage engine, I also wanted to use an ORM, and I discovered the **JetBrains Exposed** library, which is powerful, intuitive and very well documented. I had complex queries (e.g. translation memory and glossary lookups) with multiple joins (even recursively) and I was amazed at seeing how well it performs and integrates with Kotlin's coroutines.

I evaluated other popular alternatives in the multiplatform environment, such as SQLDelight from CashApp, but I found it more focused on mobile with lacking documentation on desktop (especially a multi-module desktop application), e.g. it never mentioned in the documentation that in order for schema generation to take place the the `sq` files had to be put under `src/jvmMain` so I was left with undecipherable errors and no solution. Additionally, integration with H2 was still experimental, so I didn't insist much trying.

### Secondary storage

Luckily, the **AndroidX DataStore** library from Jetpack was ported to multiplatform and is in alpha stage, so I decided giving it a try. Since I only had to save a couple of primitive values in the secondary storage (for anything more complicated I had the database), I chose the preference based data store. It worked well, despite some minor concurrency issues. No alternative evaluated here, maybe `java.util.prefs.Preferences` would have been a better replacement, I'm open to suggestions.

### XML (de)serialization

In this project, parsing and writing XML was a core feature (for Android resources), and my choice was on the **Redundent** library (more info [here](https://github.com/redundent/kotlin-xml-builder)) for being really lightweight and easy as a piece of cake to use. I really enjoyed its declarative approach to serialization which makes it really easy to write and to maintain the code. Parsing with Redundent is somehow a "minor" feature (and indeed not well documented), but it does its job well and it's very concise.

I had used more standard approaches such as SAXParser and XMLPullParser and both of them were quite a nightmare. After discovering Redundent I am looking forward to eradicating them from my other projects too! 😂

### Logging

I wanted a logger to be able to write log to a file (rather than just in the console) for bug reporting purposes, in order to ask users to send me the logs if they encountered issues with the program. I didn't find a suitable (and well documented) solution for Kotlin multiplatform, so I had to fallback to plain Java solutions, such as sl4j and log4j. Being a modern developer (this is opinionated, I know!) I was not very fond of having to write XML configuration files in order to get this to work (and it was not easy to configure the log file destination via environment variables) but I didn't find any viable alternative so I had to settle down with it.

### Networking

This was an offline application focused on storing data only on local files and databases. The need to perform network requests emerged when adding the integration with machine translation services. For this, the **Ktor** library was selected, due to the popularity it has gained in the multiplatform environment as well as its being very well documented. Plus, its content negotiation part offers out of the box support for Json with kotlinx serialization, so it was very easy to integrate and to work with in general.
